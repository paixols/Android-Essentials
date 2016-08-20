// Juan Pablo Anaya
// MDF3 - 201608
// DetailFragment

package com.paix.jpam.anayajuan_ce07.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paix.jpam.anayajuan_ce07.R;
import com.paix.jpam.anayajuan_ce07.dataModel.Person;

public class DetailFragment extends Fragment {

    //TAG
    private static final String TAG = "DetailFragment";

    private DeletePerson listener;
    private TextView firstNameHolder;
    private TextView lastNameHolder;
    private TextView ageHolder;

    public static DetailFragment newInstanceOf(Person person) {
        //New Instance
        DetailFragment detailFragment = new DetailFragment();
        //Bundle
        Bundle arguments = new Bundle();
        //Bundle Arguments
        arguments.putSerializable("person_key", person);
        detailFragment.setArguments(arguments);
        //Return instance
        return detailFragment;
    }

    /*LifeCycle*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DeletePerson) {
            listener = (DeletePerson) context;
        } else {
            throw new IllegalArgumentException("Please Add Interface");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //Dev
        Log.i(TAG, "onCreate: " + "DETAIL_FRAGMENT");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Custom View
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        //Text Views
        firstNameHolder = (TextView) v.findViewById(R.id.TextView_Detail_FirstName);
        lastNameHolder = (TextView) v.findViewById(R.id.TextView_Detail_LastName);
        ageHolder = (TextView) v.findViewById(R.id.TextView_Detail_Age);
        //Return Custom View
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Handle Arguments
        Bundle arguments = getArguments();
        Person person = (Person) arguments.getSerializable("person_key");
        //Populate Text Views with data
        if (person != null) {
            firstNameHolder.setText(person.getFirstName());
            lastNameHolder.setText(person.getLastName());
            String age = String.valueOf(person.getAge());
            ageHolder.setText(age);
        }
    }

    /*Menu*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MenuItem_Detail:
                //Call listener to delete in Main Activity
                listener.deletePerson();
                return true;
        }
        return false;
    }
}
