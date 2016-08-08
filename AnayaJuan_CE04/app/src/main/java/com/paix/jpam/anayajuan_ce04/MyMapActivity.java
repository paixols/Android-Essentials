// Juan Pablo Anaya
// MDF3 - 201608
// MyMapActivity

package com.paix.jpam.anayajuan_ce04;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MyMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_map);
        //Set Map Fragment
        MyMapFragment myMapFragment = new MyMapFragment();
        getFragmentManager().beginTransaction().replace(R.id.FrameLayout_Map_FragHolder,myMapFragment).commit();
    }


}
