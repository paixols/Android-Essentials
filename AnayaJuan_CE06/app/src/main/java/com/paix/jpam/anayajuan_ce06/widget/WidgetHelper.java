// Juan Pablo Anaya
// MDF3 - 201608
// Widget Helper

package com.paix.jpam.anayajuan_ce06.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import com.paix.jpam.anayajuan_ce06.R;
import com.paix.jpam.anayajuan_ce06.WeatherActivity;
import com.paix.jpam.anayajuan_ce06.dataModel.Weather;
import com.paix.jpam.anayajuan_ce06.utilities.StorageHelper;
import com.paix.jpam.anayajuan_ce06.widget.preferences.WeatherWidgetPreferencesAct;

public class WidgetHelper {

    //TAG
    private static final String TAG = "WidgetHelper";

    /*Widget Update*/
    public static void updateWidget(Context context, AppWidgetManager manager, int widgetId) {
        //Remote Views
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        //Config Activity (Button Intent)
        Intent configIntent = new Intent(context, WeatherWidgetPreferencesAct.class);
        configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, configIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.ImageButton_WidgetConfig, pendingIntent);

        //Weather Activity (Click Widget Intent)
        Intent weatherActivityIntent = new Intent(context, WeatherActivity.class);
        PendingIntent weatherActivityPendingIntent = PendingIntent.getActivity(context, 1,
                weatherActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.ImageView_WidgetLayout_Icon,
                weatherActivityPendingIntent);

        //Read Internal Storage for Cache Data
        Weather weather = StorageHelper.read(context);
        if (weather != null) {
            /*Widget UI*/
            remoteViews.setTextViewText(R.id.TextView_Widget_WeatherDescription, weather.
                    getCurrentConditionInfo().getWeatherDescription());
            remoteViews.setTextViewText(R.id.TextView_Widget_LocalTime, weather.
                    getLocationInfo().getLocalTime().trim());
        }
        //Weather Icon
        byte[] icon = StorageHelper.readWidgetIcon(context);
        if (icon != null) {
            //Icon
            BitmapFactory.Options bfo = new BitmapFactory.Options();
            bfo.inSampleSize = 1;
            Bitmap bitmap = BitmapFactory.decodeByteArray(icon, 0, icon.length, bfo);
            //Canvass
            Bitmap canvassBm = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(canvassBm);
            canvas.drawBitmap(bitmap, new Matrix(), null);
            remoteViews.setImageViewBitmap(R.id.ImageView_WidgetLayout_Icon, canvassBm);
        }
        //Widget Theme
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (prefs.getString("THEME_WIDGET", context.getString(R.string.ThemeDark)).equals(context.getString(R.string.ThemeDark))) {
            remoteViews.setInt(R.id.LinearLayout_Widget_Root, "setBackgroundColor", android.graphics.Color.BLACK);
        } else {
            remoteViews.setInt(R.id.LinearLayout_Widget_Root, "setBackgroundColor", Color.YELLOW);
        }

        //Dev
        Log.i(TAG, "updateWidget: " + "UPDATE_WIDGET");

        //Update Widget
        manager.updateAppWidget(widgetId, remoteViews);
    }
}
