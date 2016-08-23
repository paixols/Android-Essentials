// Juan Pablo Anaya
// MDF3 - 201608
// ThumbnailTask

package com.paix.jpam.anayajuan_ce08.images;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.paix.jpam.anayajuan_ce08.utilities.storage.StorageHelper;
import com.paix.jpam.anayajuan_ce08.widgets.UpdateHelper;

import java.util.ArrayList;

class ThumbnailTask extends AsyncTask<Void, Void, Cursor> {

    /*Properties*/
    private AsyncListenerInterface listener;
    private final Context mContext;
    private ProgressDialog progressDialog;

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
    }

    @Override
    protected Cursor doInBackground(Void... voids) {
        //Get Cursor
        Cursor cursor = MyCursorAdapter.mediaStoreImagesQuery(mContext);
        if (cursor != null) {
            //Retrieve File Paths for Media Images
            ArrayList<String> imageFilePaths = new ArrayList<>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                //Log.d(TAG, " - File Path : " + cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                String filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                if (filePath != null) {
                    imageFilePaths.add(filePath);
                }
                cursor.moveToNext();
            }
            cursor.close();
            StorageHelper.writeInternalStorage(mContext, imageFilePaths);
            //Notify Widgets for new Data
            UpdateHelper.updateStackWidget(mContext); // Notify Data Changed
            return cursor;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        //Share Cursor with Image Activity
        listener.filePathData(cursor);
        //Dismiss Progress Dialog
        progressDialog.dismiss();
    }

}
