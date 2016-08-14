// Juan Pablo Anaya
// MDF3 - 201608
// MediaActivity

package com.paix.jpam.anayajuan_ce05;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.paix.jpam.anayajuan_ce05.services.AudioService;

/*Implement the ServiceConnection Interface in the activity that is doing the binding, in this case
Main Activity is doing the binding with the AudioService. The ServiceConnection interface contains
 two callbacks that are used to relay information about the connection with the bound service.
 onServiceConnected() & onServiceDisconnected() */
public class MediaActivity extends AppCompatActivity implements ServiceConnection, OnMediaButtonClicked {

    //TAG
    private static final String TAG = "MediaActivity";
    //Bound Flag
    boolean mBound;
    //Media Player Service
    AudioService mService;
    //SeekBar Flags (Fragment -> Activity Interface Flags)
    boolean seekBarWillChange;
    boolean seekBarStoppedChanges;
    int newSeekBarValue;

    /*LifeCycle*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        Intent intent = new Intent(this, AudioService.class);
        bindService(intent, this, BIND_AUTO_CREATE);

        //Set Media Player Fragment
        MediaFragment mPlayerFragment = new MediaFragment();
        setMediaPlayerFragment(mPlayerFragment);

        //Initialize SeekBar Flags & Value
        seekBarWillChange = false;
        seekBarStoppedChanges = false;
        newSeekBarValue = 0;

        //Register Local Broadcast Receivers -> Next Song
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mNextSongReceiver,
                        new IntentFilter(AudioService.SONG_HAS_FINISHED_PLAY_NEXT));

    }

    @Override
    protected void onResume() {
        super.onResume();
         /*Set the Bound Flag to False when Activity Resumes, this stops the playback when we leave
        * the activity*/
        //mBound = false;
        /*Bind to the service using the bindService() method, and pass in the same intent that we
        * would use to start a service if it doesn't exist and don't let id die until we unbind.
        * Binding to a service happens asynchronously so don't treat your service as bound
         * immediately after calling bindService(). You will need to wait until the
         * onServiceConnected() callback and then cast the iBinder Object to your Binder type and
         * call any methods you wish to call on the Binder or the returned service. */
        //Intent intent = new Intent(this, AudioService.class);
        //bindService(intent, this, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*It's important to note that we should not attempt to unbind from a service if we're not
        * already bound, as attempting to do so will throw an IllegalArgumentException. Also since
        * bound services can't stop while bound , do not try calling stopService() while the service
        * is bound, as nothing will happen and it will be hard to debug.*/
        //mBound = false;
        //unbindService(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Unregister Local Broadcast Receivers -> Next Song
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mNextSongReceiver);
        //Unbind Service
        mBound = false;
        unbindService(this);
    }

    /*Custom Methods*/
    //Set Media Player Fragment Method
    private void setMediaPlayerFragment(MediaFragment mPlayerfragment) {
        getSupportFragmentManager().beginTransaction().replace
                (R.id.FrameLayout_MediaActivity, mPlayerfragment).commit();
    }

    /*SERVICE*/
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        AudioService.AudioServiceBinder binder =
                (AudioService.AudioServiceBinder) iBinder;
        /*Once the Service is Connected , get the Service from the  casted iBinder Object*/
        mService = binder.getService();
        /*Service bound, it's time to update the Boolean Flag to let the Main Activity it can
        * use the Service methods. */
        mBound = true;
        //Dev
        Log.i(TAG, "onServiceConnected: " + "SERVICE CONNECTED");
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        //Dev
        Log.i(TAG, "onServiceDisconnected: " + "SERVICE DISCONNECTED");
    }

    /*OnMediaButtonClicked INTERFACE*/
    //Loop
    @Override
    public void onLoopClicked() {
        mService.loop();
    }

    //Next
    @Override
    public void onNextClicked() {
        //Next
        mService.next();
        //Play
        mService.play();
    }

    //Pause
    @Override
    public void onPauseClicked() {
        mService.pause();
    }

    //Play
    @Override
    public void onPlayClicked() {
        mService.play();
    }

    //Previous
    @Override
    public void onPreviousClicked() {
        //Previous
        mService.previous();
        //Play
        mService.play();
    }

    //Shuffle
    @Override
    public void onShuffleClicked() {
        mService.shuffle();
    }

    //Stop
    @Override
    public void onStopClicked() {
        mService.stop();
    }

    /*SEEK_BAR*/
    //Tracking Changes
    @Override
    public void onSeekBarStartedTrackingChanges() {
        //SeekBarFlags
        seekBarStoppedChanges = false;
        seekBarWillChange = true;
    }

    //Stop Tracking Changes
    @Override
    public void onSeekBarStoppedTrackingChanges() {
        //SeekBarFlags
        seekBarWillChange = false;
        seekBarStoppedChanges = true;
    }

    //Value Changed
    @Override
    public void onSeekBarValueChanged(int value) {
        //Get New SeekBar Value
        newSeekBarValue = value;
        //Change Song Value
        if (seekBarWillChange && mService.mPlayer.isPlaying()) {
            mService.mPlayer.seekTo(newSeekBarValue * 1000);
            Log.i(TAG, "onSeekBarValueChanged: " + "SONG SECOND: " + mService.mPlayer.getCurrentPosition());
        }
        //Dev
        Log.i(TAG, "onSeekBarValueChanged: " + newSeekBarValue);
    }

    /*Broadcasts*/
    //Broadcast Receiver on Song Completion & Not Looping -> Should Play Next Song
    /*Broadcast Receivers*/
    private BroadcastReceiver mNextSongReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Play Next Song
            mService.play();
        }
    };


}
