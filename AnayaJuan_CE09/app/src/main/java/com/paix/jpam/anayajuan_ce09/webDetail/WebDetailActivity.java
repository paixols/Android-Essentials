// Juan Pablo Anaya
// MDF3 - 201608
// WebDetailActivity

package com.paix.jpam.anayajuan_ce09.webDetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.paix.jpam.anayajuan_ce09.R;

public class WebDetailActivity extends AppCompatActivity{

    //TAG
    //private static final String TAG = "WebDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_detail);
        Intent intent = getIntent();
        String mFirstName = intent.getStringExtra(getString(R.string.First_Name_Key));
        String mLastName = intent.getStringExtra(getString(R.string.Last_Name_Key));
        String mAge = intent.getStringExtra(getString(R.string.Age_Key));

        //Log.i(TAG, "onCreate: " + mFirstName + "/" + mLastName + "/" + mAge);

        //Set DetailWebViewFragment
        WebDetailFragment detailWebViewFragment = new WebDetailFragment().newInstanceOf(mFirstName, mLastName, mAge);
        getFragmentManager().beginTransaction().replace(R.id.FrameLayout_Detail_FragHolder, detailWebViewFragment).commit();
    }
}
