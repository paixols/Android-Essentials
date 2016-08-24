// Juan Pablo Anaya
// MDF3 - 201608
// WebListActivity

package com.paix.jpam.anayajuan_ce09.webList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.paix.jpam.anayajuan_ce09.R;
import com.paix.jpam.anayajuan_ce09.webDetail.WebDetailActivity;
import com.paix.jpam.anayajuan_ce09.webForm.WebFormActivity;

public class WebListActivity extends AppCompatActivity implements OnNewForm {

    //TAG
    private static final String TAG = "WebListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_list);
        //Set Fragment
        WebListFragment listWebViewFragment = new WebListFragment();
        getFragmentManager().beginTransaction().replace(R.id.FrameLayout_List_FragHolder, listWebViewFragment).commit();
    }

    /*Interface*/
    @Override
    public void newFrom() {
        //Transition to FormActivity
        Intent formActivityIntent = new Intent(WebListActivity.this, WebFormActivity.class);
        startActivity(formActivityIntent);
        //Dev
        Log.i(TAG, "newFrom: " + "New_Form");
    }

    @Override
    public void newDetail(String firstName, String lastName, String age) {
        //Transition to Detail Activity (With Info)
        Intent detailActivityIntent = new Intent(WebListActivity.this, WebDetailActivity.class);
        detailActivityIntent.putExtra(getString(R.string.First_Name_Key), firstName);
        detailActivityIntent.putExtra(getString(R.string.Last_Name_Key), lastName);
        detailActivityIntent.putExtra(getString(R.string.Age_Key), age);
        startActivity(detailActivityIntent);
    }
}
