package com.paix.jpam.anayajuan_ce05.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;

import com.paix.jpam.anayajuan_ce05.Song;

public class AudioService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    //TAG
    private static final String TAG = "AudioService";
    /*States for Media Player Lifecycle: State Diagram: https://goo.gl/ii8xIP */
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
    public static final String SONG_CURRENT_POSITION_UPDATE = "com.paix.jpam.j_anaya_ce05_mProgress";
    //Song Finished & Should Play Next one notification
    public static final String SONG_HAS_FINISHED_PLAY_NEXT = "com.paix.jpam.j_anaya_ce05_mNext";
    //Song Information to populate User Interface
    public static final String SONG_INFORMATION = "com.paix.jpam.j_anaya_ce05_mSongInfo";

    //Foreground Notification
    private static final int FOREGROUND_NOTIFICATION = 0x10101;

    //Notification Next and Previous Intents
    private static final String SONG_NEXT = "com.paix.jpam.j_anaya_ce05_mSongNext";
    private static final String SONG_PREVIOUS = "com.paix.jpam.j_anaya_ce05_mSongPrevious";

    //Media Player
    MediaPlayer mPlayer;

    //Song ArrayList
    Song[] songs;
    int songIndex;

    //Shuffle Flag
    boolean isShuffling;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }
}
