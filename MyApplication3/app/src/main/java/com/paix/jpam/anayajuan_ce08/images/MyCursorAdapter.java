// Juan Pablo Anaya
// MDF3 - 201608
// MyCursorAdapter

package com.paix.jpam.anayajuan_ce08.images;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.Toast;

import com.paix.jpam.anayajuan_ce08.R;
import com.squareup.picasso.Picasso;

class MyCursorAdapter extends ResourceCursorAdapter {

    //TAG
    private static final String TAG = "MyCursorAdapter";

    /*Default Constructor*/
    public MyCursorAdapter(Context context, Cursor cursor) {
        super(context, R.layout.item_gridview, cursor, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA));
        Log.i(TAG, "bindView: " + filePath);
        //Avoids UI Lag
        Picasso.with(context).load("file:" + filePath).resize(600, 400).centerCrop()
                .into((ImageView) view.findViewById(R.id.ImageView_GridView_Item));
//        //Dev
//        Log.i(TAG, "bindView: " + filePath);
    }

    /*Update Cursor Adapter*/
    public static Cursor updateCursorAdapter(Context context, MyCursorAdapter adapter) {
        Cursor cursor = mediaStoreImagesQuery(context);
        adapter.swapCursor(cursor); //Swap Existing Cursor
        //Show toast to the user
        Toast.makeText(context, "Adapter Refreshed", Toast.LENGTH_SHORT).show();
        return cursor;
    }

    public static Cursor mediaStoreImagesQuery(Context context) {
        //Content Resolver to get the Images from External Public Storage
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI; // External Public Storage
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Thumbnails.DATA}; // Images ID's
        //Create the cursor pointing to External Public Storage
        return context.getContentResolver().query(uri,
                projection, // Return the ID Column
                null,
                null,
                null);
    }

}
