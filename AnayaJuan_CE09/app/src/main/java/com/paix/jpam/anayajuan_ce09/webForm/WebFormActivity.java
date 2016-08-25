// Juan Pablo Anaya
// MDF3 - 201608
// WebFormActivity

package com.paix.jpam.anayajuan_ce09.webForm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.paix.jpam.anayajuan_ce09.R;
import com.paix.jpam.anayajuan_ce09.dataModel.BaseballPlayer;
import com.paix.jpam.anayajuan_ce09.utilities.StorageHelper;
import com.paix.jpam.anayajuan_ce09.webList.WebListActivity;

import java.util.ArrayList;

public class WebFormActivity extends AppCompatActivity implements OnFormSaved {

    //TAG
    private static final String TAG = "WebFormActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_form);
        //Set FormWebViewFragment
        WebFormFragment formWebViewFragment = new WebFormFragment();
        getFragmentManager().beginTransaction().replace(R.id.FrameLayout_Form_FragHolder, formWebViewFragment).commit();
    }

    /*Form Fragment Interface*/
    @Override
    public void saveFormData(BaseballPlayer baseballPlayer) {
        //Save Person to Internal Storage
        ArrayList<BaseballPlayer> baseballPlayers = StorageHelper.readInternalStorage(this);
        if (baseballPlayers == null) {
            baseballPlayers = new ArrayList<>();
            baseballPlayers.clear();
        }
        baseballPlayers.add(baseballPlayer);
        StorageHelper.writeInternalStorage(baseballPlayers, this);
        //Dev
        Log.i(TAG, "saveFormData: " + baseballPlayer.getFirstName() + "/" +
                baseballPlayer.getLastName() + "/" + baseballPlayer.getAge());
        //Transition to WebListActivity
        Intent listActivityIntent = new Intent(WebFormActivity.this, WebListActivity.class);
        startActivity(listActivityIntent);
        finish();
    }
}
