// Juan Anaya
// MDF3 - CE01
// SaveDeleteReceiver
package fullsail.paix.com.j_anaya_ce01.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import fullsail.paix.com.j_anaya_ce01.DataModel.Person;
import fullsail.paix.com.j_anaya_ce01.List.ListActivity;
import fullsail.paix.com.j_anaya_ce01.Utility.StorageHelper;

public class SaveDeleteReceiver extends BroadcastReceiver {

    //TAG
    private static final String TAG = "SaveDeleteReceiver";
    //Actions
    public static final String ACTION_SAVE_DATA = "com.fullsail.android.ACTION_SAVE_DATA";
    public static final String ACTION_DELETE_DATA = "com.fullsail.android.ACTION_DELETE_DATA";
    public static final String ACTION_UPDATE_LIST = "com.fullsail.android.ACTION_UPDATE_LIST";

    //On Receive
    @Override
    public void onReceive(Context context, Intent intent) {
        //Check for Save or Delete
        if (intent.getAction().equals(ACTION_SAVE_DATA)) {

            if (intent.hasExtra(ListActivity.EXTRA_FIRST_NAME) &&
                    intent.hasExtra(ListActivity.EXTRA_LAST_NAME) &&
                    intent.hasExtra(ListActivity.EXTRA_AGE)) {
                Person person = new Person(intent.getStringExtra(ListActivity.EXTRA_FIRST_NAME),
                        intent.getStringExtra(ListActivity.EXTRA_LAST_NAME),
                        intent.getIntExtra(ListActivity.EXTRA_AGE, 0));
                //Storage Helper
                StorageHelper storageHelper = new StorageHelper();
                //Read for available data
                ArrayList persons;
                persons = storageHelper.readInternalStorage(context);
                if (persons == null) {
                    persons = new ArrayList();
                    persons.clear();
                }
                //Add new data
                persons.add(person);
                //Save Data locally
                storageHelper.writeInternalStorage(persons, context);
            }

        } else if (intent.getAction().equals(ACTION_DELETE_DATA)) {

            //TODO delete data if firstName, lastName and Age match a Person record
            
        }

        //Send Broadcast for Update
        Intent updateIntent = new Intent(ACTION_UPDATE_LIST);
        context.sendBroadcast(updateIntent);
    }
}
