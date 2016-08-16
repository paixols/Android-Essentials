// Juan Pablo Anaya
// MDF3 - 201608
// OnMediaButtonClicked

package com.paix.jpam.anayajuan_ce05.media;

interface OnMediaButtonClicked {
    //Loop
    void onLoopClicked();

    //Next
    void onNextClicked();

    //Pause
    void onPauseClicked();

    //Play
    void onPlayClicked();

    //Previous
    void onPreviousClicked();

    //Shuffle
    void onShuffleClicked();

    //Stop
    void onStopClicked();

    /*SeekBar Listeners*/

    //Started Tracking Changes
    void onSeekBarStartedTrackingChanges();

    //Stopped Tracking Changes
    void onSeekBarStoppedTrackingChanges();

    //SeekBar Value Changed
    void onSeekBarValueChanged(int value);

}
