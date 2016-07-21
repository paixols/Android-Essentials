// Juan Anaya
// MDF3 - CE01
// ContentListFragment

package fullsail.paix.com.j_anaya_ce01.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import fullsail.paix.com.j_anaya_ce01.DataModel.Person;
import fullsail.paix.com.j_anaya_ce01.Utility.StorageHelper;

public class ContentListFragment extends ListFragment {

    //TAG
    private static final String TAG = "ContentListFragment";
    //Interface
    SelectedPerson listener;
    //Available Data
    ArrayList persons;
    //Base Adapter
    ContentListAdapter adapter;

    /*LifeCycle*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Outer Class Interface
        if (context instanceof SelectedPerson) {
            listener = (SelectedPerson) context;
        } else {
            throw new IllegalArgumentException("Please Add Content List Fragment Interface");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        //Read Data
        StorageHelper storageHelper = new StorageHelper();
        persons = storageHelper.readInternalStorage(getContext());
        if (persons == null) {
            persons = new ArrayList();
            persons.clear();
        }
        adapter = new ContentListAdapter(persons, getContext());
        this.setListAdapter(adapter);
    }

    /*Listener*/

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //Interface Callback
        if (persons != null) {
            Person person = (Person) persons.get(position);
            listener.selectedPerson(person);
        }

    }

}
