// Juan Pablo Anaya
// MDF3 - 201608
// FormActivity

package com.paix.jpam.anayajuan_ce04.Form;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.paix.jpam.anayajuan_ce04.R;

public class FormActivity extends AppCompatActivity implements OnFormMenuSelection {

    //TAG
    private static final String TAG = "FormActivity";

    /*Properties*/
    View mLayout;
    //External Storage Permission
    private static final String[] PERMISSION_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 0x0002;

    /*LifeCycle*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        //Snackbar Layout
        mLayout = findViewById(R.id.LinearLayout_FormActivity);
        //Get LatLng Info
        Intent intent = getIntent();
        LatLng latLng = intent.getParcelableExtra("LatLng_value");
        Log.i(TAG, "onCreate: " + latLng.latitude + "/" + latLng.longitude);

        //Set Form Fragment
        FormFragment formFragment = new FormFragment().newInstanceOf(latLng);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.FrameLayout_FormHolder, formFragment).commit();
    }


    /*OnFormMenuSelection Interface*/
    //On Saved Location
    @Override
    public void saveLocation() {
        //Check SDK version (For permissions)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //If Build Version >= API 23, request permissions at runtime
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //Request Permissions
                requestExternalStoragePermissions();
            } else {
                //TODO Save IMAGE with LocationTag to Private External Storage
            }
        }
        //Dev
        Log.i(TAG, "saveLocation: " + "Save Location Interface");
    }

    //On Open Camera
    @Override
    public void openCamera() {
        //TODO open camera with Explicit Intent
        //TODO take picture and save it to Protected External Storage
        //TODO Put picture on the Image View of the Form Fragment ImageView (REFRESH IMAGE VIEW)
        //Dev
        Log.i(TAG, "openCamera: " + "Open Camera Interface");
    }

    /*Permissions Requests*/
    //Internal Storage Permission Request (Runtime)
    private void requestExternalStoragePermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            /*Provide permissions rationale if the permission was not granted previously*/
            Snackbar.make(mLayout, "Storage permission needed to save location", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(FormActivity.this, PERMISSION_STORAGE, REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    }).show();
        } else {
            //Permissions for Writing to External Storage have not been granted, request directly
            ActivityCompat.requestPermissions(this, PERMISSION_STORAGE, REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    //On Request Permissions Result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //External Storage
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            //Received permission result for External Storage
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Let the user know the permissions have been granted
                Snackbar.make(mLayout, "External Storage permissions granted !", Snackbar.LENGTH_SHORT).show();
            } else {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }
}