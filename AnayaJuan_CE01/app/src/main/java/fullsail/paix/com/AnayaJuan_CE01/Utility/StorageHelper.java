// Juan Anaya
// MDF3 - 201608
// Storage Helper
package fullsail.paix.com.AnayaJuan_CE01.Utility;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class StorageHelper {

    /*Properties*/
    //TAG
    private static final String TAG = "StorageHelper";
    //App Storage File
    private final String FILE_NAME = "ce01_storage";

    //Internal Storage
    public void writeInternalStorage(ArrayList arrayList, Context context) {
        //Save Data
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(arrayList);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Dev
        Log.i(TAG, "writeInternalStorage: " + "ArrayList Size: " + arrayList.size());
    }

    public ArrayList readInternalStorage(Context context) {
        //New ArrayList to Hold Data
        ArrayList arrayList = new ArrayList();
        //Read Data
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            arrayList = (ArrayList) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Dev
        Log.i(TAG, "readInternalStorage: " + "ArrayList Size: " + arrayList.size());
        return arrayList;
    }
}
