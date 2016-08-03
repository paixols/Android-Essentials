// Juan Pablo Anaya
// MDF3 - 201608
// NewsListFragment

package com.paix.jpam.anayajuan_ce03;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class NewsListFragment extends ListFragment implements NewsFeedListener {

    /*Properties*/
    //TAG
    private static final String TAG = "NewsListFragment";
    //Array for the News Feed
    ArrayList<News> newsList;
    //Base Adapter
    NewsFeedAdapter adapter;
    //Interface for List Cell listener
    NewsFeedListener listener;

    /*LifeCycle*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Outer Class Interface
        if (context instanceof NewsFeedListener) {
            listener = (NewsFeedListener) context;
        } else {
            throw new IllegalArgumentException(("Please Add News Feed Public-Interface"));
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Set Adapter

    }
}
