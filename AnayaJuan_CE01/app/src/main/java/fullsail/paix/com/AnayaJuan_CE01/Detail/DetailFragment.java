// Juan Anaya
// MDF3 - 201608
// DetailFragment
package fullsail.paix.com.AnayaJuan_CE01.Detail;

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
import android.widget.Toast;

import fullsail.paix.com.AnayaJuan_CE01.DataModel.Person;
import fullsail.paix.com.AnayaJuan_CE01.R;

public class DetailFragment extends Fragment {

    //TAG
    private static final String TAG = "DetailFragment";
    //Interface
    private DeleteRecord listener;
    //Record Properties
    private String mFirstName;
    private String mLastName;
    private int mAge;
    private Person mPerson;

    //Instance Constructor
    public static DetailFragment newInstanceOf(Person person) {
        //New Instance
        DetailFragment detailFragment = new DetailFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable("person_key", person);
        detailFragment.setArguments(arguments);
        return detailFragment;
    }

    /*LifeCycle*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DeleteRecord) {
            listener = (DeleteRecord) context;
        } else {
            throw new IllegalArgumentException("Please Add Detail Form Interface");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //Handle Bundle info
        Bundle arguments = getArguments();
        if (arguments != null) {
            handleArguments(arguments);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Set Custom View
        View detailView = inflater.inflate(R.layout.fragment_detail, container, false);
        //Declare User Interface
        TextView firstNameTextView = (TextView) detailView.findViewById(R.id.TextView_Detail_FirstName);
        TextView lastNameTextView = (TextView) detailView.findViewById(R.id.TextView_Detail_LastName);
        TextView ageTextView = (TextView) detailView.findViewById(R.id.TextView_Detail_Age);
        //Set Info (If no info available , it's handled before in OnCreate)
        firstNameTextView.setText(mFirstName);
        lastNameTextView.setText(mLastName);
        ageTextView.setText(String.valueOf(mAge));

        return detailView;
    }

    /*Menu*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                //Send record to be deleted to the Detail Activity
                if (mPerson != null) {
                    listener.onDeleteRecord(mPerson);
                    //Dev
                    Log.i(TAG, "onOptionsItemSelected: " + "Delete Record");
                    return true;
                }
                //If there is nothing to delete
                Toast.makeText(getContext(), "Nothing to Delete", Toast.LENGTH_SHORT).show();
                return false;
        }
        return false;
    }

    /*Custom Argument Handling*/
    private void handleArguments(Bundle arguments) {
        //Person
        mPerson = (Person) arguments.getSerializable("person_key");
        //Get Data
        if (mPerson != null) {
            mFirstName = mPerson.getFirstName();
            mLastName = mPerson.getLastName();
            mAge = mPerson.getAge();
        } else {
            mFirstName = "Not Available";
            mLastName = "Not Available";
            mAge = 0;
        }
    }
}
