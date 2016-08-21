// Juan Pablo Anaya
// MDF3 - 201608
// MainActivity

package com.paix.jpam.anayajuan_ce08;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.paix.jpam.anayajuan_ce08.permissions.PermissionsHelper;

public class MainActivity extends AppCompatActivity {

    //TAG
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(PermissionsHelper.onRequestPermissions(this,this)){
            Log.i(TAG, "onCreate: " + "Permissions Requested");
        }

    }
}
