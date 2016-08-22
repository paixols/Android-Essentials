// Juan Pablo Anaya
// MDF3 - 201608
// StackWidgetService

package com.paix.jpam.anayajuan_ce08.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.paix.jpam.anayajuan_ce08.R;
import com.paix.jpam.anayajuan_ce08.utilities.storage.StorageHelper;

import java.util.ArrayList;

/*Widget Service*/
public class StackWidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackViewsFactory(this.getApplicationContext(), intent);
    }

}

/*Widget Stack RemoteViews Factory*/
class StackViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    //TAG
    private static final String TAG = "StackViewsFactory";

    /*Properties*/
    private static final int ID_CONSTANT = 0x0001;
    private int mWidgetId;
    //Context
    private final Context mContext;
    //ArrayList of Persons
    private ArrayList filePaths;

    /*Constructor*/
    public StackViewsFactory(Context context, Intent intent) {
        mContext = context;
        mWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        //Dev
        Log.i(TAG, "StackViewsFactory: " + "WIDGET_ID: " + mWidgetId);
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
        //Destroy Widget Data
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

        //File Path (Image)
        String filePath = (String) filePaths.get(i);
        //Remote Views with custom layout
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.stackwidget_item);
        if (filePath != null) {
            //Set Image
            try {
                Bitmap bitmap = BitmapFactory.decodeFile("file: " + filePath);
                rv.setImageViewBitmap(R.id.ImageView_StackWidget_Item, bitmap);
            } catch (Exception e) {
                Log.e(TAG, "getViewAt: Error Loading Bitmap on Widget ", e);
            }
        }

        //TODO setOnClickFillInIntent

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null; //Default Loading View
    }

    @Override
    public int getViewTypeCount() {
        return 1; //One kind of Widget
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


