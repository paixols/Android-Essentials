// Juan Pablo Anaya
// MDF3 - 201608
// DetailFragment

package com.paix.jpam.anayajuan_ce07.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    //Person to display &| delete
    Person person;
    //Interface for person deletion
    DeletePerson listener;
    //Text Views
    private TextView firstNameHolder;
    private TextView lastNameHolder;
    private TextView ageHolder;

    //Instance Constructor (Width Bundle to Pass information)
    public static DetailFragment newInstanceOf(Person person) {
        //New Instance
        DetailFragment detailFragment = new DetailFragment();
        //Bundle
        Bundle arguments = new Bundle();
        //Bundle Arguments
        arguments.putSerializable("person_key", person);
        //Set arguments to fragment
        detailFragment.setArguments(arguments);
        //Return instance
        return detailFragment;
    }

    /*LifeCycle*/
    //On Attach
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DeletePerson) {
            listener = (DeletePerson) context;
        } else {
            throw new IllegalArgumentException("Please Add Interface");
        }
    }

    //On Create
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        //Text Views
        firstNameHolder = (TextView) v.findViewById(R.id.TextView_Detail_FristName);
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
        person = (Person) arguments.getSerializable("person_key");
        //Populate Text Views with data
        firstNameHolder.setText(person.getFirstName());
        lastNameHolder.setText(person.getLastName());
        String age = String.valueOf(person.getAge());
        ageHolder.setText(age);
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
                listener.deletePerson(person);
                return true;
        }
        return false;
    }
}
