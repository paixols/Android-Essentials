// Juan Pablo Anaya
// MDF3 - 201608
// WebListInterface

package com.paix.jpam.anayajuan_ce09.webList;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.paix.jpam.anayajuan_ce09.dataModel.BaseballPlayer;
import com.paix.jpam.anayajuan_ce09.utilities.StorageHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WebListInterface {

    //TAG
    private static final String TAG = "WebListInterface";
    //Context
    Context context;
    //List Activity Interface
    OnNewForm listener;

    //Constructor
    public WebListInterface(Context context) {
        //Set Context
        this.context = context;
        //Outer Class Interface
        if (context instanceof OnNewForm) {
            listener = (OnNewForm) context;
        } else {
            throw new IllegalArgumentException("Please Add Interface");
        }
    }

    //Create JSON Array with existing data
    @JavascriptInterface
    public String createJsonArray() {
        //Read Internal Storage For Data
        ArrayList<BaseballPlayer> baseballPlayers = StorageHelper.readInternalStorage(context);
        if (baseballPlayers == null) {
            baseballPlayers = new ArrayList<>();
            baseballPlayers.clear();
            return null;
        }

        //JSON Array of Persons that will be passed to Javascript
        JSONArray playersJSONArray = new JSONArray();
        //Loop through all the existing persons data
        for (int i = 0; i < baseballPlayers.size(); i++) {
            String firstName = baseballPlayers.get(i).getFirstName();
            String lastName = baseballPlayers.get(i).getLastName();
            int age = baseballPlayers.get(i).getAge();
            String url = baseballPlayers.get(i).localSchemaUrl();
            //Create New JSONObject & Array
            try {
                //Create JSON Array for Persons Data
                JSONObject playersJSONObject = new JSONObject();
                //Single Person JSONObject
                playersJSONObject.put("firstName", firstName);
                playersJSONObject.put("lastName", lastName);
                playersJSONObject.put("age", age);
                playersJSONObject.put("url", url);
                //Add Person JSON Object to JSON Array
                playersJSONArray.put(playersJSONObject);
                //Clear
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String returnString = String.valueOf(playersJSONArray);
        Log.i(TAG, "createJsonArray: " + "JSON ARRAY CREATED");
        return returnString;
    }

}
