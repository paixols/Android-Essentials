// Juan Pablo Anaya
// MDF3 - 201608
// CustomListAdapter

package com.paix.jpam.anayajuan_ce07.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.paix.jpam.anayajuan_ce07.R;
import com.paix.jpam.anayajuan_ce07.dataModel.Person;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {

    //TAG
    private static final String TAG = "CustomListAdapter";

    /*Properties*/
    //Id
    private static final long ID_CONSTANT = 0XDEADBEEF;
    //ArrayList for Person's Data
    ArrayList persons;
    //Context
    Context context;
    //Layout Inflater
    LayoutInflater layoutInflater;

    /*Constructor*/
    public CustomListAdapter(ArrayList persons, Context context) {
        this.persons = persons;
        this.context = context;
        layoutInflater = LayoutInflater.from(this.context);
    }

    //Get Count
    @Override
    public int getCount() {
        //Perform Null Check
        if (persons != null) {
            return persons.size();
        }
        return 0;
    }

    //Get Item
    @Override
    public Object getItem(int position) {
        if (persons != null && position < persons.size() && position >= 0) {
            return persons.get(position);
        } else {
            return null;
        }
    }

    //Get Item ID
    @Override
    public long getItemId(int position) {
        return ID_CONSTANT + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Custom View Holder
        PersonViewHolder personViewHolder;
        //If there is no View
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.viewholder_list, parent, false);
            personViewHolder = new PersonViewHolder(convertView);
            convertView.setTag(personViewHolder);
        } else {
            personViewHolder = (PersonViewHolder) convertView.getTag();
        }
        //Populate the custom Cell View
        Person person = (Person) persons.get(position);
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        String age = String.valueOf(person.getAge());

        personViewHolder.firstNameHolder.setText(firstName);
        personViewHolder.lastNameHolder.setText(lastName);
        personViewHolder.ageHolder.setText(age);

        //Return new View
        return convertView;
    }

    //Custom View Holder for the Cell of the List View
    private class PersonViewHolder {
        //Properties
        TextView firstNameHolder;
        TextView lastNameHolder;
        TextView ageHolder;

        //Constructor
        public PersonViewHolder(View v) {
            firstNameHolder = (TextView) v.findViewById(R.id.TextView_ViewHolder_FristName);
            lastNameHolder = (TextView) v.findViewById(R.id.TextView_ViewHolder_LastName);
            ageHolder = (TextView) v.findViewById(R.id.TextView_ViewHolder_Age);
        }
    }

}