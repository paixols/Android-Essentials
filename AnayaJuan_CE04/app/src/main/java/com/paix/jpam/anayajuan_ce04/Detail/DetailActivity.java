// Juan Pablo Anaya
// MDF3 - 201608
// DetailActivity

package com.paix.jpam.anayajuan_ce04.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.paix.jpam.anayajuan_ce04.R;

public class DetailActivity extends AppCompatActivity implements OnDetailMenuSelection {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        LatLng latLng = intent.getParcelableExtra("LatLng_key");
        String imageName = intent.getStringExtra("PhotoName_key");
        DetailFragment detailFragment = new DetailFragment().newInstanceOf(latLng, imageName);
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_DetailHolder, detailFragment).commit();
    }

    @Override
    public void selectedWindow() {
        //Todo Should delete current ImageLocation Object
        //Todo Should send an update Map broadcast to MyMapActivity
        //Return to Map
        finish();
    }
}
