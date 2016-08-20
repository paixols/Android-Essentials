// Juan Pablo Anaya
// MDF3 - 201608
// FormActivity

package com.paix.jpam.anayajuan_ce07.form;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.paix.jpam.anayajuan_ce07.R;
import com.paix.jpam.anayajuan_ce07.list.PersonsListActivity;
import com.paix.jpam.anayajuan_ce07.utilities.StorageHelper;
import com.paix.jpam.anayajuan_ce07.dataModel.Person;
import com.paix.jpam.anayajuan_ce07.widget.UpdateHelper;

import java.util.ArrayList;

public class FormActivity extends AppCompatActivity implements NewPersonListener {

    //TAG
    //private static final String TAG = "FormActivity";

    private Boolean startedFromWidget;//Flag

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Intent intent = getIntent();
        startedFromWidget = intent.getBooleanExtra(getString(R.string.Started_From_Widget), false);

        //Set Form Fragment
        FormFragment formFragment = new FormFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_Form_FragHolder, formFragment).commit();
    }

    @Override
    public void saveNewPersonFromForm(Person person) {
        //First Read from internal Storage
        ArrayList persons = StorageHelper.readInternalStorage(this);
        if (person == null) {
            persons = new ArrayList<>();
            persons.clear();
        }
        persons.add(person);
        //Write to internal storage
        StorageHelper.writeInternalStorage(persons, this);

        //Update Fragment with new Saved Data
        UpdateHelper.updateWidgetListView(getApplicationContext());

        //Return to List Activity
        Intent intent = new Intent(FormActivity.this, PersonsListActivity.class);
        intent.putExtra(getString(R.string.Started_From_Widget), startedFromWidget);
        startActivity(intent);

        //Finish This Activity
        finish();
    }
}
