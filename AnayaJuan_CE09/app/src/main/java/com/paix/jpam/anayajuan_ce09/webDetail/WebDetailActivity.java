// Juan Pablo Anaya
// MDF3 - 201608
// WebDetailActivity

package com.paix.jpam.anayajuan_ce09.webDetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.paix.jpam.anayajuan_ce09.R;
import com.paix.jpam.anayajuan_ce09.dataModel.BaseballPlayer;
import com.paix.jpam.anayajuan_ce09.utilities.StorageHelper;
import com.paix.jpam.anayajuan_ce09.webList.WebListActivity;

import java.util.ArrayList;

public class WebDetailActivity extends AppCompatActivity implements OnDeleteForm {

    //TAG
    private static final String TAG = "WebDetailActivity";
    //Person Information
    String mFirstName;
    String mLastName;
    String mAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_detail);
        Intent intent = getIntent();
        mFirstName = intent.getStringExtra(getString(R.string.First_Name_Key));
        mLastName = intent.getStringExtra(getString(R.string.Last_Name_Key));
        mAge = intent.getStringExtra(getString(R.string.Age_Key));

        //Log.i(TAG, "onCreate: " + mFirstName + "/" + mLastName + "/" + mAge);

        //Set DetailWebViewFragment
        WebDetailFragment detailWebViewFragment = new WebDetailFragment().newInstanceOf(mFirstName, mLastName, mAge);
        getFragmentManager().beginTransaction().replace(R.id.FrameLayout_Detail_FragHolder, detailWebViewFragment).commit();
    }

    /*On Delete Interface*/
    @Override
    public void deleteFrom() {
        //Read Internal Storage to compare
        ArrayList<BaseballPlayer> baseballPlayers = StorageHelper.readInternalStorage(this);
        if (baseballPlayers == null) {
            baseballPlayers.clear();
            //Dev
            Log.i(TAG, "deleteFrom: " + "No Info Available");
            return;
        }
        for (int i = 0; i < baseballPlayers.size(); i++) {
            BaseballPlayer baseballPlayer = baseballPlayers.get(i);
            if (mFirstName.equals(baseballPlayer.getFirstName().trim()) &&
                    mLastName.equals(baseballPlayer.getLastName().trim())
                    && mAge.equals(String.valueOf(baseballPlayer.getAge()).trim())) {
                baseballPlayers.remove(i);
                //DEV
                Log.i(TAG, "deleteFrom: " + "Person Deleted");
            }
        }

        //Write new Info to Internal Storage
        StorageHelper.writeInternalStorage(baseballPlayers, this);

        //Transition to the ListActivity
        Intent listActivityIntent = new Intent(WebDetailActivity.this, WebListActivity.class);
        startActivity(listActivityIntent);
        finish();
        //Dev
        Log.i(TAG, "deleteFrom: " + "Delete_Form");
    }
}
