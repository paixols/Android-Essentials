// Juan Pablo Anaya
// MDF3 - CE02
// ExternalStorage
package com.paix.jpam.AnayaJuan_CE02.Storage;

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
    public static ArrayList readExternal(Context context) {
        //Directory
        File fileDirectory = context.getExternalFilesDir(null);
        File[] dirFiles = new File[0];
        //Array List to return
        ArrayList fileOutput = new ArrayList();
        fileOutput.clear();
        //Check for File Directory
        if (fileDirectory != null) {
            dirFiles = fileDirectory.listFiles();
        }
        //Loop through the files
        if (dirFiles.length != 0) {
            //Loop through the array of files
            for (int i = 0; i < dirFiles.length; i++) {
                String file = dirFiles[i].getAbsolutePath();
                fileOutput.add(file);
                //Dev
                Log.i(TAG, "readExternal: " + file);
            }
        }
        Log.i(TAG, "readExternal: " + "File Array: " + fileOutput);
        return fileOutput;
    }//Read External END

    /*Write to external storage*/
    public static void writeExternal(Context context, String album, String photoName, byte[] photoData) {
        //Directory
        File externalDir = context.getExternalFilesDir(null);
        //File
        File image = new File(externalDir, photoName);
        //Check for duplicates
        if (!image.exists()) {
            //If image doesn't exist on File Save to External Storage
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
        }
    }//WriteExternal END
}
