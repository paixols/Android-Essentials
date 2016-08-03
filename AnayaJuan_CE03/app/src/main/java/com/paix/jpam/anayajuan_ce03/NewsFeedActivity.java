// Juan Pablo Anaya
// MDF3 - 201608
// NewsFeedActivity
package com.paix.jpam.anayajuan_ce03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewsFeedActivity extends AppCompatActivity implements NewsFeedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*Public Interface for Favorite News Article Selected on News List Fragment*/
    @Override
    public void itemSelected(News newsArticle) {

    }
}
