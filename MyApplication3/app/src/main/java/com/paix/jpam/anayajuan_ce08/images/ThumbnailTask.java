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
        //Content Resolver to get the Images from External Public Storage
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI; // External Public Storage
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
        //Create the cursor pointing to External Public Storage
        Cursor cursor = mContext.getContentResolver().query(uri, // Return the ID Column
                projection,
                null,
                null,
                MediaStore.Images.Media.DEFAULT_SORT_ORDER);//Do not sort
        if (cursor != null) {
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
