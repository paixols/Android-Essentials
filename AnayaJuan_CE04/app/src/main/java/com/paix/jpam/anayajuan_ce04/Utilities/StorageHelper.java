// Juan Pablo Anaya
// MDF3 - 201608
// StorageHelper

package com.paix.jpam.anayajuan_ce04.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StorageHelper {

    //TAG
    private static final String TAG = "StorageHelper";
    private static final String FOLDER_NAME = "JAnayaCE04";
    private static final String FILE_NAME = "ImageLocationData";

    //Generate Media File
    public File getOutputMediaFile() {
        //TODO check for SD card
        File mediaStorageDir;
        // If the external directory is writable then then return the External pictures directory.
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), StorageHelper.FOLDER_NAME);
        } else {
            mediaStorageDir = Environment.getDownloadCacheDirectory();
        }

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    //Get File Path
    public String getCurrentFilePath(File file) {
        return "file:" + file.getAbsolutePath();
    }

    //Query File and return if it exists receiving the File Name
    public File getFileFromName(String fileName) {
        File imageLocationResult;
        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), StorageHelper.FOLDER_NAME);
        if (path.exists()) {
            //Dev
            Log.i(TAG, "getFileFromName: " + "Path Exists");
            if (path.list() != null) {
                //Loop through the available files on the FOLDER_NAME folder
                String[] availableFiles = path.list();
                for (String availableFile : availableFiles) {
                    Log.i(TAG, "getFileFromName: " + availableFile);
                    if (availableFile.equals(fileName)) {
                        imageLocationResult = new File(path, fileName);
                        return imageLocationResult;
                    }
                }
            }
        }
        return null;
    }


    //Get Last Index Bitmap from Selected Directory
    public Bitmap getLastBitmapFromFileFolder() {
        File image;
        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), StorageHelper.FOLDER_NAME);
        if (path.exists()) {
            //String[] fileNames = path.list();
            Log.i(TAG, "onViewCreated: " + "Path Exists");
            if (path.list() != null) {
                String[] fileName = path.list();
                Log.i(TAG, "onViewCreated: " + fileName.length);
                String photoName;
                if (fileName.length == 0) {
                    //photoName = fileName[0];
                    return null;
                } else {
                    photoName = fileName[fileName.length - 1];
                }
                image = new File(path, photoName);
                return BitmapFactory.decodeFile(image.getPath());
                //locationImageView.setImageBitmap(BitmapFactory.decodeFile(image.getPath()));
            }
        }
        return null;
    }

    //Get Bitmap from selected ImageLocation File (Scaled for InfoWindow)
    public Bitmap getBitmapFromFile(String fileName){
        File image;
        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), StorageHelper.FOLDER_NAME);
        if(path.exists()){
            if(path.list() != null){
               image = new File(path,fileName);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 9;
                return BitmapFactory.decodeFile(image.getPath(),options);
            }
        }
        return null;
    }

    //Write ImageLocation Objects
    public void writeInternalStorage(ArrayList<ImageLocation> arrayListLocationData, Context context) {
        //Try & Catch block to save Data internally
        try {
            //Create an instance of the file output stream (Mode Private to create or Overwrite file)
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            //Create the Object Output Stream
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            //Write the Object
            oos.writeObject(arrayListLocationData);
            //Close File Stream and Object Stream
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Development
        Log.i(TAG, "writeInternalStorage: " + arrayListLocationData.size());
    }//Write Internal Storage END

    //Read ImageLocation Objects from internal Storage
    public ArrayList readInternalStorage(Context context) {
        //Data to be filled and returned
        ArrayList arrayList = new ArrayList<>();
        try {
            //Create an instance of the file input stream
            FileInputStream fis = context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            //Type cast for object reading
            //Set array to be returned
            arrayList = (ArrayList) ois.readObject();
            //Close File Stream and Object Stream
            fis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Development
        Log.i(TAG, "readInternalStorage: " + arrayList.size());
        return arrayList;
    }//Read Internal Storage END

    //Delete ImageLocation Object from Internal Storage
    public Boolean deleteFile(String fileName, Context context) {
        //Data to be filled
        ArrayList arrayList;
        arrayList = readInternalStorage(context);
        if (arrayList.size() > 0) { // If there is internal data
            for (int i = 0; i < arrayList.size(); i++) { //Loop through all the records
                ImageLocation imageLocation = (ImageLocation) arrayList.get(i);
                //Dev
                Log.i(TAG, "deleteFile: " + imageLocation.getFilePath());
                if (imageLocation.getFilePath() == null) {
                    arrayList.remove(i);
                    writeInternalStorage(arrayList, context);
                    return true;
                }
                if (!imageLocation.getFilePath().equals("No Photo !")) { // If there is a File Path Available
                    Uri uri = Uri.parse(imageLocation.getFilePath());
                    String photoNameFromPath = uri.getLastPathSegment();
                    //Dev
                    Log.i(TAG, "deleteFile: " + photoNameFromPath + "////" + fileName);
                    if (photoNameFromPath.equals(fileName)) {
                        arrayList.remove(i);
                        writeInternalStorage(arrayList, context);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
