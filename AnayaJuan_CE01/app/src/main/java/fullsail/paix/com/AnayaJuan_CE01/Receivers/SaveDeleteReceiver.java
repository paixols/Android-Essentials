// Juan Anaya
// MDF3 - 201608
// SaveDeleteReceiver
package fullsail.paix.com.AnayaJuan_CE01.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

import fullsail.paix.com.AnayaJuan_CE01.DataModel.Person;
import fullsail.paix.com.AnayaJuan_CE01.Utility.StorageHelper;
import fullsail.paix.com.AnayaJuan_CE01.List.ListActivity;

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
        //Storage Helper Instance
        StorageHelper storageHelper = new StorageHelper();
        //Read for available data
        ArrayList persons;
        persons = storageHelper.readInternalStorage(context);
        //If there is no Data available
        if (persons == null) {
            persons = new ArrayList();
            persons.clear();
        }

        //Check for Extras
        if (intent.hasExtra(ListActivity.EXTRA_FIRST_NAME) &&
                intent.hasExtra(ListActivity.EXTRA_LAST_NAME) &&
                intent.hasExtra(ListActivity.EXTRA_AGE)) {
            //New Person
            Person person = new Person(intent.getStringExtra(ListActivity.EXTRA_FIRST_NAME),
                    intent.getStringExtra(ListActivity.EXTRA_LAST_NAME),
                    intent.getIntExtra(ListActivity.EXTRA_AGE, 0));

            //Check for Save or Delete
            if (intent.getAction().equals(ACTION_SAVE_DATA)) { //Save

                //Add new data
                //noinspection unchecked
                persons.add(person);
                //Save Data locally
                storageHelper.writeInternalStorage(persons, context);

            } else if (intent.getAction().equals(ACTION_DELETE_DATA)) { //Delete

                //Check for properties and Delete if matches
                for (int i = 0; i < persons.size(); i++) {
                    Person personCheck = (Person) persons.get(i);
                    if (personCheck.getFirstName().equals(person.getFirstName())) {
                        if (personCheck.getLastName().equals(person.getLastName())) {
                            if (personCheck.getAge() == person.getAge()) {
                                persons.remove(i);
                                //Dev
                                Log.i(TAG, "onReceive: " + "Record Deleted");
                            }
                        }
                    }
                }
                //Update Internal Storage
                storageHelper.writeInternalStorage(persons, context);
            }

        }

        //Send Broadcast for Update
        Intent updateIntent = new Intent(ACTION_UPDATE_LIST);
        context.sendBroadcast(updateIntent);
    }
}
