// Juan Pablo Anaya
// MDF3 - 201608
// Politicians Helper (All & Favorites)

package com.fullsail.android.anayajuan_ce10.utils;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.fullsail.android.anayajuan_ce10.storage.Politician;

public class PoliticiansHelper {
    
    //TAG
    private static final String TAG = "PoliticiansHelper";

    /*Properties*/
    public static final String FILE_NAME = "favorites.dat";

    /*All Politicians*/
    public static ArrayList<Politician> getAllPoliticians() {
        //Politicians
        ArrayList<Politician> politicians = new ArrayList<>();
        //Retrieve Politicians from API
        try {

            URL url = new URL("https://www.govtrack.us/api/v2/role?current=true");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream is = connection.getInputStream();
            String data = IOUtils.toString(is);
            is.close();
            connection.disconnect();

            JSONObject response = new JSONObject(data);
            JSONArray objects = response.getJSONArray("objects");

            for (int i = 0; i < objects.length(); i++) {
                JSONObject obj = objects.getJSONObject(i);
                JSONObject person = obj.getJSONObject("person");

                int id = person.getInt("id");
                String name = person.getString("name");
                String party = obj.getString("party");
                String description = obj.getString("description");
                politicians.add(new Politician(name, party, description, id));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return politicians;
    }

    /*Read Favorite Politicians*/
    @SuppressWarnings("unchecked")
    public static ArrayList<Politician> getFavoritePoliticians(Context context) {
        ArrayList<Politician> politicians = new ArrayList<>();

        try {
            InputStream is = context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(is);
            ArrayList<Politician> data = (ArrayList<Politician>) ois.readObject();
            ois.close();
            if (data != null) {
                politicians.addAll(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return politicians;
    }

    /*Save Favorite Politicians*/
    @SuppressWarnings("unchecked")
    public static void saveToFavorites(Context context, Politician politician) {
        //Politicians
        ArrayList<Politician> data = null;
        //Read already saved politicians
        try {
            InputStream is = context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(is);
            data = (ArrayList<Politician>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (data == null) {
            data = new ArrayList<>();
        }
        //Add Favorite Politician
        if (!data.contains(politician)) {
            data.add(politician);
        }
        //Save Favorite Politicians
        try {

            OutputStream os = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(data);
            oos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //Dev
        Log.i(TAG, "saveToFavorites: " + "Politicians Storage: " + data.size());
    }
}