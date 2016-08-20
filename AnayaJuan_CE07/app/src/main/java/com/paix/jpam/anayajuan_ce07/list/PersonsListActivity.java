// Juan Pablo Anaya
// MDF3 - 201608
// PersonsListActivity

package com.paix.jpam.anayajuan_ce07.list;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.paix.jpam.anayajuan_ce07.R;
import com.paix.jpam.anayajuan_ce07.dataModel.Person;
import com.paix.jpam.anayajuan_ce07.detail.DetailActivity;
import com.paix.jpam.anayajuan_ce07.form.FormActivity;

public class PersonsListActivity extends AppCompatActivity implements OnPersonClicked  {

    //TAG
    private static final String TAG = "ListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Check intent, if it comes from the widget
        Intent intent = getIntent();
        //If activity was started from Widget, finish this activity and go to Home Screen.
        if (intent.getBooleanExtra(getString(R.string.Started_From_Widget), false)) { //"widget_root"
            finish();
        }
        setContentView(R.layout.activity_list);
        //Set List Fragment
        CustomListFragment listFragment = new CustomListFragment();
        setListFragment(listFragment);
    }



    /*List Fragment Interface for Person Clicked - > Detail Activity*/
    @Override
    public void itemClicked(Person person, int position) {
        //Take user to the Detail Activity
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("person_key", person);
        intent.putExtra("person_position", position);
        intent.putExtra("widget_root", false);
        //Finish this activity
        finish();
        //Start New Activity
        startActivity(intent);

    }

    /*List Fragment Interface for To Form button -> Form Activity*/
    @Override
    public void toFormForNewPerson() {
        //Take user to new Form
        Intent intent = new Intent(this, FormActivity.class);
        intent.putExtra(getString(R.string.Started_From_Widget), false);
        //Finish current Activity
        finish();
        //Start New Activity
        startActivity(intent);

    }


    /*List Fragment Method*/
    private void setListFragment(CustomListFragment listFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_List_FragHolder, listFragment).commit();
    }

}
