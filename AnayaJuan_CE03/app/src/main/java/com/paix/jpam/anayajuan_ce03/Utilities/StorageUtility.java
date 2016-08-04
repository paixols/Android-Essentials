// Juan Pablo Anaya
// MDF3 - 201608
// StorageUtility

package com.paix.jpam.anayajuan_ce03.Utilities;

import android.content.Context;
import android.util.Log;

import com.paix.jpam.anayajuan_ce03.NewsFeed.News;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class StorageUtility {

    /*Properties*/
    //TAG
    private static final String TAG = "StorageUtility";
    //File Names
    public String NEWSFEED_FILE = "newsfeed";
    public String NEWSFEED_FAVORITE_FILE = "newsfeedfavorite";

    //Write Data to Internal Storage
    public void writeInternalStorage(ArrayList<News> newsFeed, Context context, String fileName) {
        try {
            //File Output Stream
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            //Object Output Stream
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            //Write to Internal Storage
            oos.writeObject(newsFeed);
            //Close output streams
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //DEV
        Log.i(TAG, "writeInternalStorage: " + "Array List Size: " + newsFeed.size());
    }

    //Read Data from Internal Storage
    public ArrayList<News> readInternalStorage(Context context, String fileName) {
        //Data to be returned
        ArrayList<News> newsFeed = new ArrayList<>();
        //File Input Stream
        try {
            //File Input Stream
            FileInputStream fis = context.openFileInput(fileName);
            //Object Input Stream
            ObjectInputStream ois = new ObjectInputStream(fis);
            //Read
            //noinspection unchecked
            newsFeed = (ArrayList<News>) ois.readObject();
            //Close input streams
            fis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Dev
        Log.i(TAG, "readInternalStorage: " + "Array List Size: " + newsFeed.size());
        return newsFeed;
    }

}
