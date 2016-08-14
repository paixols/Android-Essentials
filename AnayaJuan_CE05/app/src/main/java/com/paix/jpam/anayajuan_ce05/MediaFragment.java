// Juan Pablo Anaya
// MDF3 - 201608
// MediaFragment

package com.paix.jpam.anayajuan_ce05;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.paix.jpam.anayajuan_ce05.services.AudioService;

public class MediaFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    //TAG
    private static final String TAG = "MediaFragment";

    /*Properties*/
    //Interface
    OnMediaButtonClicked listener;
    //SeekBar
    SeekBar seekBar;
    //Song Progress in Seconds (Received from Audio Service via Local Broadcast)
    int mProgress;
    int mDuration;
    private Handler mHandler = new Handler();
    //Song Information
    Song currentSong;
    //User Interface Flags
    boolean stopped;
    boolean playing;
    boolean looping;
    boolean shuffling;
    boolean paused;
    //Play Stop Pause UI
    Button playButton;
    Button stopButton;
    Button pauseButton;
    Button loopButton;
    Button shuffleButton;

    /*LifeCycle*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Outer Class Interface
        if (context instanceof OnMediaButtonClicked) {
            listener = (OnMediaButtonClicked) context;
        } else {
            throw new IllegalArgumentException("Please Add Media Interface to Media Fragment");
        }
    }

    //TODO should implement OnSaveInstance, to save the sate of the seekbar when the layout changes.

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Register Local Broadcast Manager for mProgress -> SeekBar
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mProgressUpdateReceiver,
                        new IntentFilter(AudioService.SONG_CURRENT_POSITION_UPDATE));
        //Register Local Broadcast Manager for Song Information -> User Interface
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mSongInformation, new IntentFilter(AudioService.SONG_INFORMATION));
        //Set UI Flags
        stopped = false;
        playing = false;
        shuffling = false;
        looping = false;
        paused = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Custom Layout
        View v = inflater.inflate(R.layout.fragment_media, container, false);
        //Set up Buttons
        Button mediaPlayerButton = (Button) v.findViewById(R.id.Button_MediaPlayer_Next);
        mediaPlayerButton.setOnClickListener(this);
        mediaPlayerButton = (Button) v.findViewById(R.id.Button_MediaPlayer_Previous);
        mediaPlayerButton.setOnClickListener(this);
        shuffleButton = (Button) v.findViewById(R.id.Button_MediaPlayer_Shuffle);
        shuffleButton.setOnClickListener(this);
        loopButton = (Button) v.findViewById(R.id.Button_MediaPlayer_Loop);
        loopButton.setOnClickListener(this);
        //Play Pause & Stop Button + Shuffle + Loop
        playButton = (Button) v.findViewById(R.id.Button_MediaPlayer_Play);
        playButton.setOnClickListener(this);
        pauseButton = (Button) v.findViewById(R.id.Button_MediaPlayer_Pause);
        pauseButton.setOnClickListener(this);
        stopButton = (Button) v.findViewById(R.id.Button_MediaPlayer_Stop);
        stopButton.setOnClickListener(this);
        //Setup SeekBar
        seekBar = (SeekBar) v.findViewById(R.id.SeekBar_MediaPlayer_Song);
        seekBar.setOnSeekBarChangeListener(this);
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Unregister Local Broadcast Receivers
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mProgressUpdateReceiver);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mSongInformation);
    }

    /*Media Player Buttons*/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Button_MediaPlayer_Loop:
                if (!shuffling) {//Shuffle Button Check
                    if (!looping) {//Loop Button Check
                        loopButton.setBackgroundResource(R.drawable.loop_looping);
                        looping = true;
                        listener.onLoopClicked();
                        return;
                    }
                    loopButton.setBackgroundResource(R.drawable.loop);
                    looping = false;
                    //Listener
                    listener.onLoopClicked();
                    //Dev
                    Log.i(TAG, "onClick: " + "LOOP BUTTON");
                }
                return;
            case R.id.Button_MediaPlayer_Next:
                //Listener
                listener.onNextClicked();
                //Dev
                Log.i(TAG, "onClick: " + "NEXT BUTTON");
                return;
            case R.id.Button_MediaPlayer_Pause:
                if (!paused && playing) {
                    pauseButton.setBackgroundResource(R.drawable.pause_paused);
                    playButton.setBackgroundResource(R.drawable.play);
                    paused = true;
                    playing = false;
                }
                //Listener
                listener.onPauseClicked();
                //Dev
                Log.i(TAG, "onClick: " + "PAUSE BUTTON");
                return;
            case R.id.Button_MediaPlayer_Play:
                if (stopped) {
                    stopButton.setBackgroundResource(R.drawable.stop);
                    playButton.setBackgroundResource(R.drawable.play_playing);
                    stopped = false;
                    playing = true;
                }
                if (!playing && !paused) {//First Time Only or Stopped
                    playButton.setBackgroundResource(R.drawable.play_playing);
                    playing = true;
                }
                if (!playing && paused) {
                    playButton.setBackgroundResource(R.drawable.play_playing);
                    pauseButton.setBackgroundResource(R.drawable.pause);
                    playing = true;
                    paused = false;
                }
                //Listener
                listener.onPlayClicked();
                //Dev
                Log.i(TAG, "onClick: " + "PLAY BUTTON");
                return;
            case R.id.Button_MediaPlayer_Previous:
                //Listener
                listener.onPreviousClicked();
                //Dev
                Log.i(TAG, "onClick: " + "PREVIOUS BUTTON");
                return;
            case R.id.Button_MediaPlayer_Shuffle:
                if (!shuffling && !looping) {
                    shuffleButton.setBackgroundResource(R.drawable.shuffle_shuffling);
                    shuffling = true;
                    listener.onShuffleClicked();
                    return;
                } else if (!looping) {
                    shuffleButton.setBackgroundResource(R.drawable.shuffle);
                    shuffling = false;
                    //Listener
                    listener.onShuffleClicked();
                    //Dev
                    Log.i(TAG, "onClick: " + "SHUFFLE BUTTON");
                }
                return;
            case R.id.Button_MediaPlayer_Stop:
                //Reset SeekBar Position
                if (playing || paused) {
                    stopButton.setBackgroundResource(R.drawable.stop_stopped);
                    playButton.setBackgroundResource(R.drawable.play);
                    pauseButton.setBackgroundResource(R.drawable.pause);
                    stopped = true;
                }
                mHandler.removeCallbacks(seekBarCurrentPositionUpdate);
                seekBar.setProgress(0);
                mProgress = 0;
                //Listener
                listener.onStopClicked();
                //Dev
                Log.i(TAG, "onClick: " + "STOP BUTTON");
                return;

        }
    }

    /*Seek Bar Change Listeners*/
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //Progress Changed From User
        if (fromUser) {
            listener.onSeekBarValueChanged(seekBar.getProgress());
        }
        //Song Progress
        listener.onSeekBarValueChanged(progress);
        //Set SeekBar length to Match Song Length
        seekBar.setMax(mDuration / 1000);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        listener.onSeekBarStartedTrackingChanges();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        listener.onSeekBarStoppedTrackingChanges();
    }

    /*Broadcast Receivers*/
    //Progress Update (Seek_bar)
    private BroadcastReceiver mProgressUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Get mProgress Update Value from intent
            mProgress = intent.getIntExtra("mProgress_key", 0);
            mDuration = intent.getIntExtra("mSongDuration_key", 0);
            //Change SeekBar value with the current song progress value -> mProgress
            Log.d(TAG, "onReceive: mProgressUpdateReceiver: "
                    + "Progress: " + mProgress + " / Duration:" + mDuration);
            seekBarCurrentPositionUpdate.run();
        }
    };
    //Song Information
    private BroadcastReceiver mSongInformation = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //When Song is preparing receive information to display when it's playing
            currentSong = (Song) intent.getSerializableExtra("songInfo_key");
            //Set UI with current song information
            ImageView albumCover = (ImageView) getActivity().findViewById(R.id.ImageView_MediaPlayer_AlbumCover);
            TextView artistInfo = (TextView) getActivity().findViewById(R.id.TextView_MediaPlayer_SongName);
            if (currentSong != null) {
                albumCover.setImageResource(currentSong.getArtwork());
                String artistInfoText = getResources().getString(R.string.Artist_Name) + currentSong.getArtist().toUpperCase() + "\n" +
                        getResources().getString(R.string.Album_Name) + currentSong.getSongName().toUpperCase() + "\n" +
                        getResources().getString(R.string.Song_Name) + currentSong.getSongName().toUpperCase();
                artistInfo.setText(artistInfoText); //Avoid String Concatenation
            }
        }
    };

    /*Async Tasks -> Runnable*/
    private Runnable seekBarCurrentPositionUpdate = new Runnable() {
        @Override
        public void run() {
            //Update SeekBar in UI
            seekBar.setProgress(mProgress);
            mHandler.postDelayed(seekBarCurrentPositionUpdate, 1000);//1 Second
        }
    };
}
