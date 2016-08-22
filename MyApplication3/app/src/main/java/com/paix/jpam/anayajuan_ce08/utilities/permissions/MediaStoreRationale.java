// Juan Pablo Anaya
// MDF3 - 201608
// MediaStoreRationale

package com.paix.jpam.anayajuan_ce08.utilities.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.paix.jpam.anayajuan_ce08.R;

class MediaStoreRationale extends AsyncTask<Void, Void, Void> {

    //TAG
    private static final String TAG = "MediaStoreRationale";

    /*Properties*/
    private final Context mContext;
    private final Activity mActivity;
    private AlertDialog.Builder mAlertDialogBuilder;

    /*Constructor*/
    public MediaStoreRationale(Context context, Activity activity) {
        this.mContext = context;
        this.mActivity = activity;
    }

    /*LifeCycle*/

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Create Alert Dialog
        mAlertDialogBuilder = new AlertDialog.Builder(mContext);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //Build Alert Dialog
        mAlertDialogBuilder.setTitle(mContext.getString(R.string.Media_Rationale_Title));
        mAlertDialogBuilder.setMessage(mContext.getString(R.string.Media_Rationale_Message));
        mAlertDialogBuilder.setPositiveButton(mContext.getString
                (R.string.Media_Rationale_Positive_Button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PermissionsHelper.MY_PERMISSIONS_REQUEST_READ_PHOTOS);
            }
        });

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Show Alert Dialog
        mAlertDialogBuilder.show();
        //Dev
        Log.i(TAG, "onPostExecute: " + "Media_Permissions_Rationale");
    }
}
