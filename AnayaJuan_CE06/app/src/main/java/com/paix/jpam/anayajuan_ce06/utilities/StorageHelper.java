// Juan Pablo Anaya
// MDF3 - 201608
// StorageHelper

package com.paix.jpam.anayajuan_ce06.utilities;

import android.content.Context;
import android.util.Log;

import com.paix.jpam.anayajuan_ce06.dataModel.Weather;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class StorageHelper {

    //TAG
    private static final String TAG = "StorageHelper";
    /*Properties*/
    private static final String FILE_NAME = "com.paix.jpam.anayajuan_ce06.WEATHER_DATA";
    private static final String ICON_FILE_NAME = "com.paix.jpam.anayajuan_ce06.WEATHER_ICON";

    /*Save to Internal Storage*/
    public static void save(Context context, Weather weather) {
        if (weather != null) {
            //Save Weather to Internal Storage
            try {
                FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(weather);
                oos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Save Icon to Internal Storage

        }
        //Dev
        Log.i(TAG, "save: " + "WRITE_INTERNAL_STORAGE");
    }

    /*Read from Internal Storage*/
    public static Weather read(Context context) {
        File file = context.getFilesDir();
        try {
            File a = new File(file, FILE_NAME);
            FileInputStream fis = new FileInputStream(a);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Weather weather = (Weather) ois.readObject();
            ois.close();
            fis.close();
            if (weather != null) {
                //Dev
                Log.i(TAG, "read: " + "READ_INTERNAL_STORAGE");
                return weather;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*Save Widget Icon*/
    public static void saveWidgetIcon(Context context, byte[] image) {
        if (image.length > 0) {
            try {
                FileOutputStream fos = context.openFileOutput(ICON_FILE_NAME, Context.MODE_PRIVATE);
                fos.write(image);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Dev
        Log.i(TAG, "saveWidgetIcon: " + "SAVE_WIDGET_ICON_INTERNAL_STORAGE");
    }

    /*Read Widget Icon*/
    public static byte[] readWidgetIcon(Context context) {
        File file = context.getFilesDir();
        try {
            File a = new File(file, ICON_FILE_NAME);
            FileInputStream fis = new FileInputStream(a);
            byte[] image = IOUtils.toByteArray(fis);
            fis.close();
            if (image.length > 0) {
                //Dev
                Log.i(TAG, "readWidgetIcon: " + "READ_WIDGET_ICON_INTERNAL_STORAGE");
                return image;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
