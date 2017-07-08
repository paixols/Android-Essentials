// Juan Pablo Anaya
// MDF3 - 201608
// GridViewAdapter

package com.paix.jpam.anayajuan_ce02.gridView;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.paix.jpam.anayajuan_ce02.R;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

class GridViewAdapter extends BaseAdapter {

    //TAG
    private static final String TAG = "GridViewAdapter";
    //Context
    private final Context context;
    //ID
    private static final long ID_CONSTANT = 0xDEADBEEF;
    //Layout Inflater
    private final LayoutInflater inflater;
    //Adapter Data
    private final ArrayList adapterData;

    /*Constructor*/
    public GridViewAdapter(Context context, ArrayList adapterData) {
        this.context = context;
        this.adapterData = adapterData;
        inflater = LayoutInflater.from(this.context);
    }

    /*LifeCycle*/
    @Override
    public int getCount() {
        if (adapterData != null) {
            return adapterData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (adapterData != null && i < adapterData.size() && i >= 0) {
            return adapterData.get(i);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int i) {
        return ID_CONSTANT + i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //New View Holder
        ViewHolder viewHolder;

        //If there is no view
        if (view == null) {
            view = inflater.inflate(R.layout.gridview_cell, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //Read Files Array
        File protectedExternal = context.getExternalFilesDir(null);
        File[] files = new File[0];
        if (protectedExternal != null) {
            files = protectedExternal.listFiles();
        }

        //File external = context.getExternalFilesDir(null);
        File fileImage = new File(protectedExternal, files[i].getName());

        //Set Image from Bitmap
        try {
            FileInputStream fis = new FileInputStream(fileImage);
            byte[] data = IOUtils.toByteArray(fis);
            BitmapFactory.Options bmfOptions = new BitmapFactory.Options();
            //Scale Image
            bmfOptions.inSampleSize = 4;
            //Create Bitmap & load image to imageView
            viewHolder.thumbnail.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length, bmfOptions));

        } catch (IOException e) {
            e.printStackTrace();
            //Dev
            Log.i(TAG, "getView: " + "Failure loading image to GridView Cell");
        }
        //Return Custom View Holder
        return view;
    }

    /*Custom View Holder*/
    private class ViewHolder {
        final ImageView thumbnail;

        public ViewHolder(View v) {
            thumbnail = (ImageView) v.findViewById(R.id.imageView_gridView_thumbnail_cell);
        }
    }
}
