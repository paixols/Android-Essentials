// Juan Anaya
// MDF3 - CE01
// ContentListAdapter

package fullsail.paix.com.j_anaya_ce01.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fullsail.paix.com.j_anaya_ce01.DataModel.Person;
import fullsail.paix.com.j_anaya_ce01.R;

public class ContentListAdapter extends BaseAdapter {

    //TAG
    private static final String TAG = "ContentListAdapter";
    //ID
    private static final long ID_CONSTANT = 0XDEADBEEF;
    //Persons on record
    ArrayList persons;
    //Context
    Context context;
    //Layout Inflater
    LayoutInflater layoutInflater;

    /*Constructor*/
    public ContentListAdapter(ArrayList persons, Context context) {
        this.persons = persons;
        this.context = context;
        layoutInflater = LayoutInflater.from(this.context);
    }

    /*Required Methods*/
    @Override
    public int getCount() {
        if (persons != null) {
            return persons.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (persons != null && i < persons.size() && i >= 0) {
            return persons.get(i);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int i) {
        return ID_CONSTANT + i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //Person View Holder
        PersonViewHolder personViewHolder;
        //If there is no View, create one
        if (view == null) {
            view = layoutInflater.inflate(R.layout.personviewholder_cell, viewGroup, false);
            personViewHolder = new PersonViewHolder(view);
            view.setTag(personViewHolder);
        } else {
            personViewHolder = (PersonViewHolder) view.getTag();
        }
        //Populate personViewHolder
        Person person = (Person) persons.get(i);
        personViewHolder.firstName.setText(person.getFirstName());
        personViewHolder.lastName.setText(person.getLastName());
        personViewHolder.age.setText(String.valueOf(person.getAge()));
        //Return custom Person View Holder Cell
        return view;
    }

    /*View Holder*/
    private class PersonViewHolder {
        TextView firstName;
        TextView lastName;
        TextView age;

        public PersonViewHolder(View v) {
            firstName = (TextView) v.findViewById(R.id.TextView_Cell_FirstName);
            lastName = (TextView) v.findViewById(R.id.TextView_Cell_LastName);
            age = (TextView) v.findViewById(R.id.TextView_Cell_Age);
        }
    }
}
