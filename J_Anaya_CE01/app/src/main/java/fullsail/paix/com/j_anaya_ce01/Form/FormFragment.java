// Juan Anaya
// MDF3 - CE01
// FormFragment

package fullsail.paix.com.j_anaya_ce01.Form;

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
import android.widget.EditText;
import android.widget.Toast;

import fullsail.paix.com.j_anaya_ce01.DataModel.Person;
import fullsail.paix.com.j_anaya_ce01.R;
import fullsail.paix.com.j_anaya_ce01.Utility.EditTextHelper;
import fullsail.paix.com.j_anaya_ce01.Utility.StorageHelper;


public class FormFragment extends Fragment {

    //TAG
    private static final String TAG = "FormFragment";
    //Interface
    SaveForm listener;
    //Fragment Components
    EditText firstNameField;
    EditText lastNameField;
    EditText ageField;

    /*LifeCycle*/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Outer Class Interface
        if (context instanceof SaveForm) {
            listener = (SaveForm) context;
        } else {
            throw new IllegalArgumentException("Please Add Save Form Interface");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View viewForm = inflater.inflate(R.layout.fragment_form, container, false);
        //Declare UI
        firstNameField = (EditText) viewForm.findViewById(R.id.EditText_Form_FirstName);
        lastNameField = (EditText) viewForm.findViewById(R.id.EditText_Form_LastName);
        ageField = (EditText) viewForm.findViewById(R.id.EditText_Form_Age);
        return viewForm;
    }

    /*Menu*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_form, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_saveForm:

                //Get input info
                if (EditTextHelper.isEmpty(firstNameField) || EditTextHelper.isEmpty(lastNameField) ||
                        EditTextHelper.isEmpty(ageField)) {
                    //Let the user know everything has to be filled in
                    Toast.makeText(getContext(), "Incomplete Info", Toast.LENGTH_SHORT).show();
                    return false;
                }
                //Retrieve input info
                Person person = new Person(firstNameField.getText().toString().trim(),
                        lastNameField.getText().toString().trim(),
                        Integer.valueOf(ageField.getText().toString().trim()));
                //Interface Listener for Form Activity
                listener.onSaveForm(person);
                //Dev
                Log.i(TAG, "onOptionsItemSelected: " + "Save Form");
                return true;
        }
        return false;
    }


}
