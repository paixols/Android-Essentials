// Juan Pablo Anaya
// MDF3 - 201608
// ExternalStorage

package com.paix.jpam.anayajuan_ce02.Storage;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExternalStorage {

    //TAG
    private static final String TAG = "ExternalStorage";

    /*Read External Storage*/
    public static ArrayList<String> readExternal(Context context) {
        //Directory
        File fileDirectory = context.getExternalFilesDir(null);
        File[] dirFiles = new File[0];
        //Array List to return
        ArrayList<String> fileOutput = new ArrayList<>();
        fileOutput.clear();
        //Check for File Directory
        if (fileDirectory != null) {
            dirFiles = fileDirectory.listFiles();
        }
        //Loop through the files
        if (dirFiles.length != 0) {
            //Loop through the array of files
            for (File dirFile : dirFiles) {
                String file = dirFile.getAbsolutePath();
                fileOutput.add(file);
                //Dev
                Log.i(TAG, "readExternal: " + file);
            }
        }
        //Dev
        Log.i(TAG, "readExternal: " + "File Array: " + fileOutput);
        return fileOutput;
    }//Read External END

    /*Write to external storage*/
    public static void writeExternal(Context context, String photoName, byte[] photoData) {
        //Directory
        File externalDir = context.getExternalFilesDir(null); //Protected External Storage
        //File
        File image = new File(externalDir, photoName);
        //Save to External Storage
        try {
            FileOutputStream fos = new FileOutputStream(image);
            fos.write(photoData);
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
            //Dev
            Log.i(TAG, "writeExternal: " + "Exception in Writing photo", e);
        }
        //Dev
        Log.i(TAG, "writeExternal: " + "Image Saved to External Storage");
    }//WriteExternal END

    /*Duplicate Images check*/
    public static boolean imageExists(Context context, String fileName) {
        File protectedStorage = context.getExternalFilesDir(null);
        File image = new File(protectedStorage, fileName);
        //Dev
        Log.i(TAG, "imageExists: " + image.exists());
        return image.exists();
    }
}
