// Juan Pablo Anaya
// MDF3 - 201608
// WebDetailInterface

package com.paix.jpam.anayajuan_ce09.webDetail;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;


import com.paix.jpam.anayajuan_ce09.dataModel.BaseballPlayer;
import com.paix.jpam.anayajuan_ce09.utilities.StorageHelper;
import com.paix.jpam.anayajuan_ce09.webList.WebListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class WebDetailInterface {

    //TAG
    private static final String TAG = "WebDetailInterface";
    //Person Information
    private final String mFirstName;
    private final String mLastName;
    private final String mAge;
    private final Context mContext;

    //Constructor
    public WebDetailInterface(Context context, String firstName, String lastName, String age) {
        this.mAge = age;
        this.mLastName = lastName;
        this.mFirstName = firstName;
        this.mContext = context;
    }


    @JavascriptInterface
    public String getPlayer() {
        //Create JSON Array for Persons Data
        JSONObject personJsonObject = new JSONObject();
        JSONArray personJsonArray = new JSONArray();
        //Single Person JSONObject
        try {
            personJsonObject.put("firstName_key", mFirstName);
            personJsonObject.put("lastName_key", mLastName);
            personJsonObject.put("age_key", mAge);
            //Add Person JSON Object to JSON Array
            personJsonArray.put(personJsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Dev
        Log.i(TAG, "getPerson: " + personJsonArray);
        return String.valueOf(personJsonArray);
    }

    @JavascriptInterface
    public void deletePlayer() {
        //Read Internal Storage to compare
        ArrayList<BaseballPlayer> baseballPlayers = StorageHelper.
                readInternalStorage(mContext.getApplicationContext());
        if (baseballPlayers == null) {
            //Dev
            Toast.makeText(mContext,"Nothing to Delete",Toast.LENGTH_SHORT).show();
            //Dev
            //Log.i(TAG, "deleteFrom: " + "No Info Available");
            return;
        }
        for (int i = 0; i < baseballPlayers.size(); i++) {
            BaseballPlayer baseballPlayer = baseballPlayers.get(i);
            if (mFirstName.equals(baseballPlayer.getFirstName().trim()) &&
                    mLastName.equals(baseballPlayer.getLastName().trim())
                    && mAge.equals(String.valueOf(baseballPlayer.getAge()).trim())) {
                baseballPlayers.remove(i);
                //DEV
                Log.i(TAG, "deleteFrom: " + "Person Deleted");
            }
        }
        //Write new Info to Internal Storage
        StorageHelper.writeInternalStorage(baseballPlayers, mContext.getApplicationContext());
        //Transition to the ListActivity
        Intent listActivityIntent = new Intent(mContext.getApplicationContext(), WebListActivity.class);
        mContext.startActivity(listActivityIntent);
        //Dev
        Log.i(TAG, "deleteFrom: " + "Delete_Form");
    }


}
