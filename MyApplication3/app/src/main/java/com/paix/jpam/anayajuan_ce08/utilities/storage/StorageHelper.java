// Juan Pablo Anaya
// MDF3 - 201608
// StorageHelper

package com.paix.jpam.anayajuan_ce08.utilities.storage;

/*File Path Storage for Widget Access*/

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class StorageHelper {

    //TAG
    private static final String TAG = "StorageHelper";

    /*Properties*/
    public static final String FILE_NAME = "LAB_CE07_FILE_PATHS";

    /*Write to Internal Storage*/
    public static void writeInternalStorage(Context context, ArrayList filePaths) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(filePaths);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Dev
        Log.i(TAG, "writeInternalStorage: " + "/*/" + filePaths.size());
    }

    /*Read from Internal Storage*/
    public static ArrayList readInternalStorage(Context context) {
        ArrayList filePaths = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            filePaths = (ArrayList) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Dev
        Log.i(TAG, "readInternalStorage: " + "/*/" + filePaths.size());
        return filePaths;
    }
}
