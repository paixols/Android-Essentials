// Juan Anaya
// MDF3 - CE01
// FormActivity
package fullsail.paix.com.j_anaya_ce01.Form;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fullsail.paix.com.j_anaya_ce01.DataModel.Person;
import fullsail.paix.com.j_anaya_ce01.List.ContentListFragment;
import fullsail.paix.com.j_anaya_ce01.List.ListActivity;
import fullsail.paix.com.j_anaya_ce01.R;
import fullsail.paix.com.j_anaya_ce01.Receivers.SaveDeleteReceiver;

public class FormActivity extends AppCompatActivity implements SaveForm {

    //TAG
    private static final String TAG = "FormActivity";
    //Broadcast Receiver
    UpdateReceiver mReceiver;

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

    /*Form Fragment Interface*/
    @Override
    public void onSaveForm(Person person) {
        //Send Broadcast with data to be saved
        Intent intent = new Intent(SaveDeleteReceiver.ACTION_SAVE_DATA);
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
