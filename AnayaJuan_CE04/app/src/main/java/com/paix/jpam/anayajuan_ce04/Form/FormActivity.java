// Juan Pablo Anaya
// MDF3 - 201608
// FormActivity

package com.paix.jpam.anayajuan_ce04.Form;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.paix.jpam.anayajuan_ce04.R;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        //Get LatLng Info
        Intent intent = getIntent();
        LatLng latLng = intent.getParcelableExtra("LatLng_value");

        //Set Form Fragment
        FormFragment formFragment = new FormFragment().newInstanceOf(latLng);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.FrameLayout_FormHolder,formFragment).commit();
    }


}
