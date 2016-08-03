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
import android.widget.ListView;

import com.paix.jpam.anayajuan_ce03.Utilities.StorageUtility;

import java.util.ArrayList;

public class NewsListFragment extends ListFragment {

    /*Properties*/
    //TAG
    private static final String TAG = "NewsListFragment";
    //Array for the News Feed
    ArrayList<News> newsFeedFavorite;
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
        StorageUtility storageUtility = new StorageUtility();
        //Get Data from the News Feed Favorite File - Internal Storage
        newsFeedFavorite = storageUtility.readInternalStorage(getContext(),
                getContext().getString(R.string.newsFeedFavorite));
        if (newsFeedFavorite == null) {
            newsFeedFavorite = new ArrayList<News>();
            newsFeedFavorite.clear();
        }
        //Set Adapter
        adapter = new NewsFeedAdapter(newsFeedFavorite, getContext());
        this.setListAdapter(adapter);
    }

    /*Listener & Public-Interface*/
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //Set Interface Listener for News Feed Activity
        News selectedArticle = newsFeedFavorite.get(position);
        listener.itemSelected(selectedArticle);
    }

}