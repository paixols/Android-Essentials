// Juan Pablo Anaya
// MDF3 - 201608
// DetailActivity

package com.paix.jpam.anayajuan_ce07.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.paix.jpam.anayajuan_ce07.R;
import com.paix.jpam.anayajuan_ce07.utilities.StorageHelper;
import com.paix.jpam.anayajuan_ce07.dataModel.Person;
import com.paix.jpam.anayajuan_ce07.list.PersonsListActivity;
import com.paix.jpam.anayajuan_ce07.widget.UpdateHelper;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements DeletePerson {

    //TAG
    private static final String TAG = "DetailActivity";
    //Person (Received From List Activity)
    Person person;
    int position;
    //Array to read persons from Internal Storage
    ArrayList persons;
    //Started from Widget Flag
    Boolean startedFromWidget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Get Intent
        Intent intent = getIntent();
        person = (Person) intent.getSerializableExtra("person_key");
        position = intent.getIntExtra("person_position", 0);
        startedFromWidget = intent.getBooleanExtra("widget_root",false);

        //Terminate Activity if there is no Information
        if (person == null) {
            finish();
            return;
        }

        //Set Fragment
        DetailFragment detailFragment = DetailFragment.newInstanceOf(person);
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_Detail_FragHolder, detailFragment).commit();
    }

    /*Delete Person Listener*/
    @Override
    public void deletePerson(Person person) {
        //Delete From Internal Storage
        persons = StorageHelper.readInternalStorage(this);
        if (persons == null) {
            persons = new ArrayList<>();
            persons.clear();
        }
        persons.remove(position);
        StorageHelper.writeInternalStorage(persons, this);

        //Update Fragment with new Saved Data
        UpdateHelper.updateWidgetListView(getApplicationContext());

        //Return to ListActivity
        Intent intent = new Intent(DetailActivity.this, PersonsListActivity.class);
        intent.putExtra(getString(R.string.Started_From_Widget), startedFromWidget);
        startActivity(intent);
        //Finish This Activity
        finish();
    }


}
