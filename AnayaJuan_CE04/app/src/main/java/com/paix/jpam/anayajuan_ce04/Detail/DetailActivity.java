// Juan Pablo Anaya
// MDF3 - 201608
// DetailActivity

package com.paix.jpam.anayajuan_ce04.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.paix.jpam.anayajuan_ce04.R;
import com.paix.jpam.anayajuan_ce04.map.MyMapActivity;
import com.paix.jpam.anayajuan_ce04.utilities.StorageHelper;

public class DetailActivity extends AppCompatActivity implements OnDetailMenuSelection {

    //TAG
    private static final String TAG = "DetailActivity";

    /*Properties*/
    //Details Shown
    private LatLng latLng;
    private String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        latLng = intent.getParcelableExtra("LatLng_key");
        imageName = intent.getStringExtra("PhotoName_key");
        DetailFragment detailFragment = new DetailFragment().newInstanceOf(latLng, imageName);
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_DetailHolder, detailFragment).commit();
    }

    @Override
    public void selectedWindow() {
        StorageHelper storageHelper = new StorageHelper();
        if (storageHelper.deleteFile(imageName, this)) {
            Log.i(TAG, "selectedWindow: " + "FILE DELETED");
        }
        //Update Map Broadcast
        Intent updateMapIntent = new Intent(MyMapActivity.UPDATE_MAP);
        this.sendBroadcast(updateMapIntent);
        //Return to Map
        finish();
    }
}
