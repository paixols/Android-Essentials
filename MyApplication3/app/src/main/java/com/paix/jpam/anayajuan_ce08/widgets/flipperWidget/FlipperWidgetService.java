// Juan Pablo Anaya
// MDF3 - 201608
// FlipperWidgetService

package com.paix.jpam.anayajuan_ce08.widgets.flipperWidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.paix.jpam.anayajuan_ce08.R;
import com.paix.jpam.anayajuan_ce08.utilities.storage.StorageHelper;

import java.util.ArrayList;

public class FlipperWidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FlipperWidgetFactory(this.getApplicationContext(), intent);
    }
}

// Juan Pablo Anaya
// MDF3 - 201608
// FlipperWidgetFactory

class FlipperWidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    //TAG
    private static final String TAG = "FlipperWidgetFactory";

    /*Properties*/
    private static final int ID_CONSTANT = 0x0001;
    //Context
    private final Context mContext;
    //ArrayList of Persons
    private ArrayList filePaths;

    /*Constructor*/
    public FlipperWidgetFactory(Context context, Intent intent) {
        mContext = context;
        int mWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        //Dev
        Log.i(TAG, "FlipperWidgetFactory: " + "WIDGET_ID: " + mWidgetId);
    }

    @Override
    public void onCreate() {
        //Data Update
        filePaths = StorageHelper.readInternalStorage(mContext);

    }

    @Override
    public void onDataSetChanged() {
        //Data Update
        filePaths = StorageHelper.readInternalStorage(mContext);
    }

    @Override
    public void onDestroy() {
        filePaths.clear();
        filePaths = null;
    }

    @Override
    public int getCount() {
        if (filePaths == null || filePaths.size() == 0) {
            return 0;
        }
        return filePaths.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        //TODO get custom ViewFlipper
        //TODO Make custom method that listens for the buttons and returns the desired image

        //File Path (Image)
        String filePath = (String) filePaths.get(i);
        //Remote Views with custom layout
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.fllipperwidget_item);
        if (filePath != null) {
            //Set Image
            try {
                BitmapFactory.Options bfo = new BitmapFactory.Options();
                bfo.inSampleSize = 6;
                Bitmap bitmap = BitmapFactory.decodeFile(filePath, bfo);
                rv.setImageViewBitmap(R.id.ImageView_ImageTop_ViewFlipper, bitmap);
            } catch (Exception e) {
                Log.e(TAG, "getViewAt: Error Loading Bitmap on Widget ", e);
            }
        }

        //Fill In Intent (Data carrier)
        Intent intent = new Intent();
        intent.setDataAndType(Uri.parse("file://" + filePaths.get(i)), "image/jpg");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        rv.setOnClickFillInIntent(R.id.LinearLayout_FlipperWidget_Item, intent);

        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;//Default Loading View
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return ID_CONSTANT + i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}


