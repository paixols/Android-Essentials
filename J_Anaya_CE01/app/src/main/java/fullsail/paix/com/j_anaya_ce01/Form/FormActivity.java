// Juan Anaya
// MDF3 - CE01
// FormActivity
package fullsail.paix.com.j_anaya_ce01.Form;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import fullsail.paix.com.j_anaya_ce01.DataModel.Person;
import fullsail.paix.com.j_anaya_ce01.R;

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

    }
}
