// Juan Anaya
// MDF3 - CE01
// ListActivity

package fullsail.paix.com.j_anaya_ce01.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import fullsail.paix.com.j_anaya_ce01.DataModel.Person;
import fullsail.paix.com.j_anaya_ce01.Form.FormActivity;
import fullsail.paix.com.j_anaya_ce01.R;
import fullsail.paix.com.j_anaya_ce01.Receivers.SaveDeleteReceiver;

public class ListActivity extends AppCompatActivity implements SelectedPerson {

    //TAG
    private static final String TAG = "ListActivity";
    //Form Request Code
    private static final int FORM_REQUEST_CODE = 0001;
    //Actions
    private static final String ACTION_VIEW_DATA = "com.fullsail.android.ACTION_VIEW_DATA";
    //Extras
    public static final String EXTRA_FIRST_NAME = "com.fullsail.android.EXTRA_FIRST_NAME";
    public static final String EXTRA_LAST_NAME = "com.fullsail.android.EXTRA_LAST_NAME";
    public static final String EXTRA_AGE = "com.fullsail.android.EXTRA_AGE";
    //Broadcast Receiver
    UpdateReceiver mReceiver;

    /*Life Cycle*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //Set Content List Fragment
        ContentListFragment contentListFragment = new ContentListFragment();
        getSupportFragmentManager().beginTransaction().
                replace(R.id.FrameLayout_ContentList_FragHolder, contentListFragment).commit();

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

    /*Menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Load Custom Menu
        getMenuInflater().inflate(R.menu.menu_content_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_newForm:
                //Transition to Form Activity
                Intent intent = new Intent(ListActivity.this, FormActivity.class);
                startActivityForResult(intent, FORM_REQUEST_CODE);
                //Dev
                Log.i(TAG, "onOptionsItemSelected: " + "New Form");
                return true;
        }
        return false;
    }

    /*Activity Result*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FORM_REQUEST_CODE:
                //Dev
                Log.i(TAG, "onActivityResult: " + "Back from Form Activity");
                return;
        }
    }

    /*Interface from Content List Fragment triggered when a Cell on the list is selected*/
    @Override
    public void selectedPerson(Person person) {
        //Create the intent
        Intent intent = new Intent();
        intent.setAction(ACTION_VIEW_DATA);
        intent.putExtra(EXTRA_FIRST_NAME, person.getFirstName());
        intent.putExtra(EXTRA_LAST_NAME, person.getLastName());
        intent.putExtra(EXTRA_AGE, person.getAge());
        //Verify the intent will resolve to an Activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /*Broadcast Receiver*/
    private class UpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Set new Content List Fragment
            //Set Content List Fragment
            ContentListFragment contentListFragment = new ContentListFragment();
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.FrameLayout_ContentList_FragHolder, contentListFragment).commit();

        }
    }
}
