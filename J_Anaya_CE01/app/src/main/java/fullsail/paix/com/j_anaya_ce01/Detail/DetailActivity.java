// Juan Anaya
// MDF3 - CE01
// DetailActivity
package fullsail.paix.com.j_anaya_ce01.Detail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fullsail.paix.com.j_anaya_ce01.DataModel.Person;
import fullsail.paix.com.j_anaya_ce01.List.ListActivity;
import fullsail.paix.com.j_anaya_ce01.R;
import fullsail.paix.com.j_anaya_ce01.Receivers.SaveDeleteReceiver;

public class DetailActivity extends AppCompatActivity implements DeleteRecord {

    //TAG
    private static final String TAG = "DetailActivity";
    //Person
    Person mPerson;
    //Broadcast Receiver
    UpdateReceiver mReceiver;


    /*LifeCycle*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Get info to display
        Intent intent = getIntent();
        String firstName = intent.getStringExtra(ListActivity.EXTRA_FIRST_NAME);
        String lastName = intent.getStringExtra(ListActivity.EXTRA_LAST_NAME);
        int age = intent.getIntExtra(ListActivity.EXTRA_AGE, 0);
        //Create new person
        mPerson = new Person(firstName, lastName, age);
        //Set Fragment
        DetailFragment detailFragment = DetailFragment.newInstanceOf(mPerson);
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_Detail_FragmentHolder
                , detailFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Update Receiver Registration & Filter
        mReceiver = new UpdateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(SaveDeleteReceiver.ACTION_UPDATE_LIST);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Update Receiver Un-registration
        unregisterReceiver(mReceiver);
    }

    /*Interface*/
    @Override
    public void onDeleteRecord(Person person) {
        //Send Broadcast with data to be saved
        Intent intent = new Intent(SaveDeleteReceiver.ACTION_DELETE_DATA);
        intent.putExtra(ListActivity.EXTRA_FIRST_NAME, person.getFirstName());
        intent.putExtra(ListActivity.EXTRA_LAST_NAME, person.getLastName());
        intent.putExtra(ListActivity.EXTRA_AGE, person.getAge());
        sendBroadcast(intent);
    }

    /*Broadcast Receiver*/
    private class UpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Transition back to List Activity
            finish();
        }
    }


}
