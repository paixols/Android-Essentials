// Juan Pablo Anaya
// MDF3 - CE02
// Main Activity
package com.paix.jpam.j_anaya_ce02;

import android.Manifest;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //TAG
    private static final String TAG = "MainActivity";
    //External Storage Permission
    int externalStoragePermissionCheck = ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


}
