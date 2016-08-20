// Juan Pablo Anaya
// MDF3 - 201608
// CustomListFragment

package com.paix.jpam.anayajuan_ce07.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.paix.jpam.anayajuan_ce07.R;
import com.paix.jpam.anayajuan_ce07.utilities.StorageHelper;
import com.paix.jpam.anayajuan_ce07.dataModel.Person;

import java.util.ArrayList;

public class CustomListFragment extends ListFragment {

    //TAG
    private static final String TAG = "CustomListFragment";
    //Array List for Persons
    ArrayList persons;
    //Base Adapter
    CustomListAdapter adapter;
    //Interface Listener
    OnPersonClicked listener;


    /*Life Cycle*/
    //On Attach
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Outer Class Interface
        if (context instanceof OnPersonClicked) {
            listener = (OnPersonClicked) context;
        } else {
            throw new IllegalArgumentException(("Please Add Interface"));
        }
    }

    //On Create
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //Dev
        Log.i(TAG, "onCreate: " + "CUSTOM_LIST_FRAGMENT");
    }

    //
    @Override
    public void onStart() {
        super.onStart();
        //Read Internal Storage
        persons = StorageHelper.readInternalStorage(getActivity());
        if (persons == null) {
            persons = new ArrayList<>();
            persons.clear();
        }
        adapter = new CustomListAdapter(persons, getActivity());
        this.setListAdapter(adapter);
    }

    /*Menu*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MenuItem_ToForm:
                //Send to Form Activity from List Activity
                listener.toFormForNewPerson();
                return true;
        }
        return false;
    }

    /*Listener*/

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //Interface Callback
        listener.itemClicked((Person)persons.get(position), position);
    }

}