// Juan Anaya
// MDF3 - CE01
// ListActivity

package fullsail.paix.com.j_anaya_ce01.List;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import fullsail.paix.com.j_anaya_ce01.Form.FormActivity;
import fullsail.paix.com.j_anaya_ce01.R;

public class ListActivity extends AppCompatActivity {

    //TAG
    private static final String TAG = "ListActivity";
    //Form Request Code
    private static final int FORM_REQUEST_CODE = 0001;
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

    /*Menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Load Custom Menu
        getMenuInflater().inflate(R.menu.menu_content_list,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_newForm:
                //Transition to Form Activity
                Intent intent = new Intent(ListActivity.this, FormActivity.class);
                startActivityForResult(intent,FORM_REQUEST_CODE);
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
        switch (requestCode){
            case FORM_REQUEST_CODE:
                //Dev
                Log.i(TAG, "onActivityResult: " + "Back from Form Activity");
                return;
        }
    }
}
