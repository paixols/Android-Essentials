// Juan Pablo Anaya
// MDF3 - 201608
// StorageHelper

package com.paix.jpam.anayajuan_ce07.utilities;

import android.content.Context;
import android.util.Log;

import com.paix.jpam.anayajuan_ce07.dataModel.Person;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class StorageHelper {

    //TAG
    private static final String TAG = "StorageHelper";

    //App's File Name
    private static final String FILE_NAME = "LAB_CE07_Persons";

    /*Write Internal Storage*/
    public static void writeInternalStorage(ArrayList<Person> persons, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(persons);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Dev
        Log.i(TAG, "writeInternalStorage:  /*/  No.records: " + persons.size());
    }

    //Read data from internal Storage
    public static ArrayList readInternalStorage(Context context) {
        ArrayList arrayList = new ArrayList();
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            arrayList = (ArrayList<Person>) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Development
        Log.i(TAG, "readInternalStorage:   /*/  No.records: " + arrayList.size());
        return arrayList;
    }

}
