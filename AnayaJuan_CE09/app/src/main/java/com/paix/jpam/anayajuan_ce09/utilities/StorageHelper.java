// Juan Pablo Anaya
// MDF3 - 201608
// StorageHelper

package com.paix.jpam.anayajuan_ce09.utilities;

import android.content.Context;
import android.util.Log;

import com.paix.jpam.anayajuan_ce09.dataModel.BaseballPlayer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class StorageHelper {

    //TAG
    private static final String TAG = "StorageHelper";

    //App's File Name for Witting and Reading Person Data
    private static final String FILE_NAME = "Baseball_Players";

    //Write data to internal Storage
    public static void writeInternalStorage(ArrayList<BaseballPlayer> baseballPlayers, Context context) {
        //Save Data (Internal Storage)
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(baseballPlayers);
            oos.close();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        //Development
        Log.i(TAG, "writeInternalStorage: " + baseballPlayers.size());
    }//Write Internal Storage END

    //Read data from internal Storage
    public static ArrayList<BaseballPlayer> readInternalStorage(Context context) {
        ArrayList<BaseballPlayer> baseballPlayers = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            baseballPlayers = (ArrayList<BaseballPlayer>) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Development
        Log.i(TAG, "readInternalStorage: " + baseballPlayers.size());
        return baseballPlayers;
    }
}
