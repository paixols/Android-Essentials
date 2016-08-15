package com.paix.jpam.anayajuan_ce05.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.paix.jpam.anayajuan_ce05.R;
import com.paix.jpam.anayajuan_ce05.dataModel.Song;
import com.paix.jpam.anayajuan_ce05.media.MediaActivity;
import com.paix.jpam.anayajuan_ce05.receivers.NotificationReceiver;

import java.util.Random;

public class AudioService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    //TAG
    private static final String TAG = "AudioService";
    /*States for Media Player Lifecycle: State Diagram: https://goo.gl/ii8xIP */
//    @Retention(RetentionPolicy.class)
//    @IntDef({STATE_IDLE,STATE_INITIALIZED,STATE_PREPARING,STATE_PREPARED,
//            STATE_STARTED,STATE_PAUSED,STATE_STOPPED,STATE_PLAYBACK_COMPLETED,
//            STATE_END})
//    private interface PlayerState{}
    private static final int STATE_IDLE = 0;
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_PREPARING = 2;
    private static final int STATE_PREPARED = 3;
    private static final int STATE_STARTED = 4;
    private static final int STATE_PAUSED = 5;
    private static final int STATE_STOPPED = 6;
    private static final int STATE_PLAYBACK_COMPLETED = 7;
    private static final int STATE_END = 8;
    //Media Player State tracker
    private int mState;
    //Media Player Progress
    public int mSongDuration;
    private Handler mHandler = new Handler();

    //Song Position update for Seek Bar -> Local Broadcast for Media Player Fragment
    public static final String SONG_CURRENT_POSITION_UPDATE = "com.paix.jpam.anayajuan_ce05_mProgress";
    //Song Finished & Should Play Next one notification
    public static final String SONG_HAS_FINISHED_PLAY_NEXT = "com.paix.jpam.anayajuan_ce05_mNext";
    //Song Information to populate User Interface
    public static final String SONG_INFORMATION = "com.paix.jpam.anayajuan_ce05_mSongInfo";

    //Foreground Notification
    private static final int FOREGROUND_NOTIFICATION = 0x10101;

    //Notification Next and Previous Intents
    public static final String SONG_NEXT = "com.paix.jpam.anayajuan_ce05_mSongNext";
    public static final String SONG_PREVIOUS = "com.paix.jpam.anayajuan_ce05_mSongPrevious";
//    public static final String UPDATE_UI = "com.paix.jpam.anayajuan_ce05_updateUi";
    //Notification Finish Service


    //Media Player
    public MediaPlayer mPlayer;

    //Song ArrayList
    Song[] songs;
    int songIndex;
    public static Song currentSong;

    //Shuffle Flag
    boolean isShuffling;
    //Service Active Flag
    public boolean mRunning;

    /*To implement a Binder, the First thing we need to do is create a public inner class in our
    * service that extends Binder, then we can add whatever methods we want to that binder to
    * access the service. The easiest way to do this is to create a single method in your binder
    * class that returns an instance of the containing service. Source: https://goo.gl/AHDoVM */
    public class AudioServiceBinder extends Binder {
        public AudioService getService() {
            return AudioService.this;
        }
    }

    /*Life Cycle*/
    @Override
    public void onCreate() {
        super.onCreate();

        //Active Service Flag
        mRunning = false;

        //Register Local Broadcast Receivers -> Next Song, Previous Song & Song has finished playing
        IntentFilter filterNextPreviousFinished = new IntentFilter();
        filterNextPreviousFinished.addAction(AudioService.SONG_HAS_FINISHED_PLAY_NEXT);
        filterNextPreviousFinished.addAction(NotificationReceiver.CHANGE_NEXT);
        filterNextPreviousFinished.addAction(NotificationReceiver.CHANGE_PREVIOUS);
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mNextSongReceiver,
                        filterNextPreviousFinished);

        //Populate Array with songs
        songs = new Song[]{new Song("Gessaffelstain", "Aleph", "Aleph", R.drawable.gessaffelstein_aleph, R.raw.aleph),
                new Song("Kimbra", "Settle Down", "Settle Down", R.drawable.kimbra_settle_down, R.raw.settle_down),
                new Song("Soja", "Amid The Noise And Haste", "Your Song", R.drawable.soja, R.raw.your_song),
                new Song("Broke Fro Free", "Something EP", "Something Elated", R.drawable.something_elated, R.raw.something_elated)};
        songIndex = 0;

        Log.i(TAG, "onCreate: " + songs.length);

        mPlayer = new MediaPlayer();
        mState = STATE_IDLE;
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnCompletionListener(this);
        //Set Looping
        //mPlayer.setLooping(false);
        //Duration
        mSongDuration = 0;
        //Shuffle Flag
        isShuffling = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //If Media Player exists, release.
        if (mPlayer != null) {
            mPlayer.release();
            mState = STATE_END;
            mPlayer = null;
        }
        //Unregister Local Broadcast Receivers -> Next Song
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mNextSongReceiver);
        stopSelf();
        mRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Service Active
        if (!mRunning) {
            //Start Sticky Service
            //if (intent != null) {
                //if (intent.getAction().equals("hola")) {
                    Log.i(TAG, "onStartCommand: " + "SERVICE STARTED");
                    mRunning = true;
                    //Todo Start Foreground Notification Here , not on the Binding
                    return Service.START_STICKY;
                //}
            //}
        }//Else
        return super.onStartCommand(intent, flags, startId);
    }

    /*PLAY*/
    public void play() {
        //If it already started skip all
        if (mState != STATE_STARTED) {

            //If it's paused resume
            if (mState == STATE_PAUSED) {
                mPlayer.start();
                mState = STATE_STARTED;
            } else {
                //Reset Happened - Top of the Process
                if (mState == STATE_IDLE) {
                    try {
                        //setDataSource
//                        mPlayer.setDataSource(this, Uri.parse("android.resource://" +
//                                getPackageName() + "/" + R.raw.something_elated));
                        mPlayer.setDataSource(this, Uri.parse("android.resource://" +
                                getPackageName() + "/" + songs[songIndex].getSong()));
                        mState = STATE_INITIALIZED;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //To State Preparing
                if (mState == STATE_INITIALIZED || mState == STATE_STOPPED || mState == STATE_PLAYBACK_COMPLETED) {
                    mPlayer.prepareAsync();
                    mState = STATE_PREPARING;

                    //When Song is preparing send Local Broadcast with Song Information for UI
                    Intent intent = new Intent(SONG_INFORMATION);
                    //Current Song
                    currentSong = songs[songIndex];
                    intent.putExtra("songInfo_key", currentSong);
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

                }
            }
            //Start Notification
            foregroundNotification(currentSong.getArtwork(), currentSong.getArtist(), currentSong.getSongName());
        }
    }

    /*PAUSE*/
    public void pause() {
        if (mState == STATE_STARTED) {
            mPlayer.pause();
            mState = STATE_PAUSED;
        }
        //Remove notification if playback is paused
        stopForeground(true);
        //mRunning = false;
    }//Finished

    /*STOP*/
    public void stop() {
        if (mState == STATE_STARTED || mState == STATE_PAUSED ||
                mState == STATE_PREPARED || mState == STATE_PLAYBACK_COMPLETED) {
            mPlayer.stop();
            mState = STATE_STOPPED;
            //Stop Song Current Position Runnable
            mHandler.removeCallbacks(songCurrentPositionUpdate);
        }
        //Remove notification if playback is stopped
        stopForeground(true);
        //mRunning = false;
    }

    /*Loop*/
    public void loop() {
        if (!mPlayer.isLooping()) {
            mPlayer.setLooping(true);
            //Dev
            Log.i(TAG, "loop: " + mPlayer.isLooping());
            return;
        }
        mPlayer.setLooping(false);
        //Dev
        Log.i(TAG, "loop: " + mPlayer.isLooping());
    }

    /*Next*/
    public void next() {
        //Stop Current Song Playback
        mPlayer.stop();
        mState = STATE_STOPPED;
        if (!mPlayer.isLooping()) {
            //The Media Player needs to read the next song -> change song index
            if (!isShuffling) {
                if (songIndex == songs.length - 1) { // Go back to first song
                    songIndex = 0;
                } else {
                    songIndex++; // Next Available Song
                }
            } else {
                //If it's set to shuffle, get a new Random Song Index
                randomSongIndex();
            }
            //Reset Media Player
            mPlayer.reset();
            mState = STATE_IDLE;
            return;
        }
        mPlayer.prepareAsync();//Note: Start from beginning to avoid errors
        mState = STATE_PREPARING;

    }

    /*Previous*/
    public void previous() {
        //Stop Current Song Playback
        mPlayer.stop();
        mState = STATE_STOPPED;

        if (!mPlayer.isLooping()) {
            //The Media Player needs to read the previous song -> change song index
            if (!isShuffling) {
                if (songIndex == 0) {
                    songIndex = (songs.length - 1);
                } else {
                    songIndex--;
                }
            } else {
                randomSongIndex();
            }
            //Reset Media player
            mPlayer.reset();
            mState = STATE_IDLE;
            return;
        }

        mPlayer.prepareAsync();//Note: Start from beginning to avoid errors
        mState = STATE_PREPARING;
    }

    /*Shuffle*/
    public void shuffle() {
        if (!isShuffling) {
            isShuffling = true;
            return;
        }
        isShuffling = false;
    }

    /*Media Player On Prepared Listener - called with prepareAsync()*/
    @Override
    public void onPrepared(MediaPlayer mp) {
        /*It's also ok to call .prepare() instead of prepareAsync() with files */
        mState = STATE_PREPARED; //prepareAsync() finished
        //Duration
        mSongDuration = mPlayer.getDuration();//  When track is prepared
        //Start Service
        mPlayer.start();
        mState = STATE_STARTED;
        songCurrentPositionUpdate.run();
    }

    /*Get Values to Update SeekBar*/
    /*A Handler allows you to send and process Message and Runnable objects associated with a
    thread's MessageQueue. Each Handler instance is associated with a single thread and that
    thread's message queue. When you create a new Handler, it is bound to the thread / message
    queue of the thread that is creating it -- from that point on, it will deliver messages and
    runnables to that message queue and execute them as they come out of the message queue.*/
    private Runnable songCurrentPositionUpdate = new Runnable() {
        @Override
        public void run() {
            if (mState == STATE_STARTED) {
                int mProgress = mPlayer.getCurrentPosition() / 1000; //Seconds
                //Broadcast Song Progress Update every second
                Intent intent = new Intent(SONG_CURRENT_POSITION_UPDATE);
                intent.putExtra("mProgress_key", mProgress);
                intent.putExtra("mSongDuration_key", mSongDuration);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            }
            mHandler.postDelayed(this, 250);//Send Broadcast every 250 ms
        }
    };

    /* On Media Player Completion Listener*/
    @Override
    public void onCompletion(MediaPlayer mp) {
        mState = STATE_PLAYBACK_COMPLETED;
        mPlayer.stop();
        mState = STATE_STOPPED;
        //IF The Loop Song button is Pressed
        if (mPlayer.isLooping()) {
            mPlayer.prepareAsync();//Note: Start from beginning to avoid errors
            mState = STATE_PREPARING;
            return;
        }
        //If Song has stopped and is not looping
        if (mState == STATE_STOPPED) {
            if (!isShuffling) {
                //If the Media Player needs to read the next song change song index
                if (songIndex == songs.length - 1) { // Go back to first song
                    songIndex = 0;
                } else {
                    songIndex++; // Next Available Song
                }
            } else {
                //If it's set to shuffle, get a new Random Song Index
                randomSongIndex();
            }
            //Reset Media Player
            mPlayer.reset();
            mState = STATE_IDLE;
            //Broadcast that the song has Finished to Activity
            Intent intent = new Intent(SONG_HAS_FINISHED_PLAY_NEXT);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        }
    }


    /*Return an Instance of the Binder in onBind. This allows all binding activities to directly
       * access the service using a service object. IMPORTANT: You should never bind to a service
       * if the onBind method returns null.*/
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new AudioServiceBinder();
    }

    /*Random Song Index*/
    private void randomSongIndex() {
        Random randomNumber = new Random();
        songIndex = randomNumber.nextInt(songs.length);
        //TODO random number generation should have logic if the Audio is streamed
    }

    //Foreground Notification
    public void foregroundNotification(int icon, String artist, String songTitle) {

        //Notification Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.ic_media_play);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), icon);
        builder.setLargeIcon(largeIcon);
        builder.setContentTitle(artist);
        builder.setContentText(songTitle);
        builder.setAutoCancel(false);
        builder.setOngoing(true);
        builder.setStyle(new NotificationCompat.MediaStyle());
        //Pending Intent for Previous Button
        Intent previousSongIntent = new Intent(SONG_PREVIOUS);
        PendingIntent previousSongPendingIntent = PendingIntent.getBroadcast(this, 1, previousSongIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(new android.support.v4.app.NotificationCompat.Action(R.drawable.previous, "Previous", previousSongPendingIntent));
        //Pending Intent for Next Button
        Intent nextSongIntent = new Intent(SONG_NEXT);
        PendingIntent nextSongPendingIntent = PendingIntent.getBroadcast(this, 0, nextSongIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(new android.support.v4.app.NotificationCompat.Action(R.drawable.next, "Next", nextSongPendingIntent));
        //Pending Intent to open Activity
        Intent openActivity = new Intent(this, MediaActivity.class);
        PendingIntent openActivityPendingIntent = PendingIntent.getActivity(this, 2, openActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(openActivityPendingIntent);

        //Start Notification
        startForeground(FOREGROUND_NOTIFICATION, builder.build());
    }

    /*Broadcasts*/
    //Broadcast Receiver on Song Completion & Not Looping -> Should Play Next Song
    /*Broadcast Receivers*/
    private BroadcastReceiver mNextSongReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //Notification Broadcasts
            if (intent.getAction().equals(NotificationReceiver.CHANGE_NEXT)) {
                //Dev
                Log.i(TAG, "onReceive: " + "CHANGE NEXT SONG ON MEDIA ACTIVITY");
                next();
                play();
            } else if (intent.getAction().equals(NotificationReceiver.CHANGE_PREVIOUS)) {
                //Dev
                Log.i(TAG, "onReceive: " + "CHANGE PREVIOUS SONG ON MEDIA ACTIVITY");
                previous();
                play();
            } else if (intent.getAction().equals(AudioService.SONG_HAS_FINISHED_PLAY_NEXT)) {
                //Dev
                Log.i(TAG, "onReceive: " + "SONG HAS FINISHED, PLAY NEXT");
                //Play Next Song
                play();
            }
        }
    };
}
