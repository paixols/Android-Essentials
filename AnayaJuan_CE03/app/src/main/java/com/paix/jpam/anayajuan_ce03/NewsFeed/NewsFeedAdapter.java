// Juan Pablo Anaya
// MDF3 - 201608
// NewsFeedAdapter

package com.paix.jpam.anayajuan_ce03.NewsFeed;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.paix.jpam.anayajuan_ce03.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class NewsFeedAdapter extends BaseAdapter {

    /*Properties*/
    //TAG
    private static final String TAG = "NewsFeedAdapter";
    //ID
    private static final long ID_CONSTANT = 0XDEADBEEF;
    //ArrayList for News Feed
    private final ArrayList<News> newsList;
    //Context
    private final Context context;
    //Layout Inflater
    private final LayoutInflater layoutInflater;

    /*Constructor*/
    public NewsFeedAdapter(ArrayList<News> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
        layoutInflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        //Perform Null Check
        if (newsList != null) {
            return newsList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (newsList != null && i < newsList.size() && i >= 0) {
            return newsList.get(i);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int i) {
        return ID_CONSTANT + i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //Custom View Holder for the News Feed List View "cell"
        NewsViewHolder newsViewHolder;

        //If there is no View, create one
        if (view == null) {
            view = layoutInflater.inflate(R.layout.newsfeed_cell, viewGroup, false);
            newsViewHolder = new NewsViewHolder(view);
            view.setTag(newsViewHolder);
        } else {
            newsViewHolder = (NewsViewHolder) view.getTag();
        }

        //Populate the custom Cell View
        News cellNews = newsList.get(i);
        newsViewHolder.title.setText(cellNews.getTitle());
        newsViewHolder.section.setText(cellNews.getSection());
        if (!cellNews.getSubSection().equals("")) {
            newsViewHolder.subsection.setText(cellNews.getSubSection());
        } else {
            newsViewHolder.subsection.setText(context.getString(R.string.unavailable));
        }
        if (!cellNews.getThumbnailUrl().equals("")) { //If a thumbnail is available
            Picasso.with(context).load(cellNews.getThumbnailUrl()).into(newsViewHolder.thumbnail);
        }
        //Return Custom Cell View
        return view;
    }

    //Custom View Holder for the Cell of the List View
    private class NewsViewHolder {

        final TextView section;
        final TextView subsection;
        final TextView title;
        final ImageView thumbnail;

        public NewsViewHolder(View v) {
            section = (TextView) v.findViewById(R.id.TextView_Cell_Section);
            subsection = (TextView) v.findViewById(R.id.TextView_Cell_Subsection);
            title = (TextView) v.findViewById(R.id.TextView_Cell_Title);
            thumbnail = (ImageView) v.findViewById(R.id.ImageView_Cell_Thumbnail);
        }
    }
}
