// Juan Pablo Anaya
// MDF3 - 201608
// ThumbnailTask

package com.paix.jpam.anayajuan_ce08.images;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.util.ArrayList;

public class ThumbnailTask extends AsyncTask<Void, Void, ArrayList<String>> {

    //TAG
    private static final String TAG = "ThumbnailTask";

    /*Properties*/
    AsyncListenerInterface listener;
    Context mContext;
    ProgressDialog progressDialog;
    ArrayList<String> imagesFilePaths;

    /*Constructor*/
    public ThumbnailTask(Context context) {
        mContext = context;
    }

    /*LifeCycle*/
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Interface attach
        if (mContext instanceof AsyncListenerInterface) {
            listener = (AsyncListenerInterface) mContext;
        } else {
            throw new IllegalArgumentException("Please Add Interface to Thumbnail Task");
        }
        //Progress Dialog
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle("Thumbnail Query");
        progressDialog.setMessage("Searching...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Initialize ArrayList
        imagesFilePaths = new ArrayList<>();
        imagesFilePaths.clear();
    }

    @Override
    protected ArrayList<String> doInBackground(Void... voids) {
        //Content Resolver to get the Images from External Public Storage
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI; // External Public Storage
        //Create the cursor pointing to External Public Storage
        Cursor cursor = mContext.getContentResolver().query(uri, // Return the ID Column
                null,
                null,
                null,
                MediaStore.Images.Media.DEFAULT_SORT_ORDER);//Do not sort
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                if (filePath != null) {
                    imagesFilePaths.add(filePath);
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        //Return Array of File Paths
        return imagesFilePaths;
    }

    @Override
    protected void onPostExecute(ArrayList<String> filePaths) {
        super.onPostExecute(filePaths);
        if (!filePaths.isEmpty()) {
            listener.filePathData(filePaths);
        }
        //Dismiss Progress Dialog
        progressDialog.dismiss();
    }

}
