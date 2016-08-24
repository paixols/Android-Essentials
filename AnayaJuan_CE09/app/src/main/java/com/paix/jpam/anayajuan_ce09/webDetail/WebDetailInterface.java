// Juan Pablo Anaya
// MDF3 - 201608
// WebDetailInterface

package com.paix.jpam.anayajuan_ce09.webDetail;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.paix.jpam.anayajuan_ce09.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebDetailInterface {

    //TAG
    private static final String TAG = "WebDetailInterface";
    //Person Information
    String mFirstName;
    String mLastName;
    String mAge;

    //Constructor
    public WebDetailInterface(String firstName, String lastName, String age) {
        this.mAge = age;
        this.mLastName = lastName;
        this.mFirstName = firstName;
    }

    @JavascriptInterface
    public String getPerson() {
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
        return String.valueOf(personJsonArray);
    }

}
