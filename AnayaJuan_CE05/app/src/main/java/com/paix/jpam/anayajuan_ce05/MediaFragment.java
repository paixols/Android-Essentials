// Juan Pablo Anaya
// MDF3 - 201608
// MediaFragment

package com.paix.jpam.anayajuan_ce05;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public class MediaFragment extends Fragment {

    //TAG
    private static final String TAG = "MediaFragment";

    /*Properties*/
    //Interface
    OnMediaButtonClicked listener;


    /*LifeCycle*/
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }
}
