// Juan Anaya
// MDF3 - CE01
// FormActivity
package fullsail.paix.com.j_anaya_ce01.Form;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import fullsail.paix.com.j_anaya_ce01.DataModel.Person;
import fullsail.paix.com.j_anaya_ce01.R;
import fullsail.paix.com.j_anaya_ce01.Utility.StorageHelper;

public class FormActivity extends AppCompatActivity implements SaveForm {

    //TAG
    private static final String TAG = "FormActivity";

    /*LifeCycle*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        //Set Form Fragment
        FormFragment formFragment = new FormFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_Form_FragHolder,
                formFragment).commit();
    }


    @Override
    public void onSaveForm(Person person) {
        //Storage Helper
        StorageHelper storageHelper = new StorageHelper();
        //Read for available data
        ArrayList persons;
        persons = storageHelper.readInternalStorage(this);
        if (persons == null) {
            persons = new ArrayList();
            persons.clear();
        }
        //Add new data
        persons.add(person);
        //Save Data locally
        storageHelper.writeInternalStorage(persons, this);
        this.finish();
    }
}
