// Juan Pablo Anaya
// MDF3 - 201608
// MyCursorAdapter

package com.paix.jpam.anayajuan_ce08.images;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.Toast;

import com.paix.jpam.anayajuan_ce08.R;

public class MyCursorAdapter extends ResourceCursorAdapter {

    //TAG
    private static final String TAG = "MyCursorAdapter";

    /*Default Constructor*/
    public MyCursorAdapter(Context context, Cursor cursor) {
        super(context, R.layout.item_gridview, cursor, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        ((MyImageView) view.findViewById(R.id.ImageView_GridView_Item)).setImageBitmap(bitmap);
        //Dev
        Log.i(TAG, "bindView: " + filePath);
    }

    /*Update Cursor Adapter*/
    public static void updateCursorAdapter(Context context, Cursor cs, MyCursorAdapter adapter) {
        //Content Resolver to get the Images from External Public Storage
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI; // External Public Storage
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA}; // Images ID's
        //Create the cursor pointing to External Public Storage
        cs = context.getContentResolver().query(uri,
                projection, // Return the ID Column
                null,
                null,
                null);      //Do not sort
        adapter.swapCursor(cs);
        //Show toast to the user
        Toast.makeText(context, "Adapter Refreshed", Toast.LENGTH_SHORT).show();
    }
}
