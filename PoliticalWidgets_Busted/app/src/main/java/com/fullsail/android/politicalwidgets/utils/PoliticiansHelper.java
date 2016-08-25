package com.fullsail.android.politicalwidgets.utils;

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

import com.fullsail.android.politicalwidgets.storage.Politician;

public class PoliticiansHelper {

	public static ArrayList<Politician> getAllPoliticians() {
		ArrayList<Politician> politicians = new ArrayList<>();

		try {

			URL url = new URL("https://www.gov track.us/api/v2/role?current=true");

			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.connect();

			InputStream is = connection.getInputStream();
			String data = IOUtils.toString(is);
			is.close();
			connection.disconnect();

			JSONObject response = new JSONObject(data);
			JSONArray objects = response.getJSONArray("objects");

			for(int i = 0; i < objects.length(); i++) {
				JSONObject obj = objects.getJSONObject(i);
				JSONObject person = obj.getJSONObject("person");

				int id = person.getInt("id");
				String name = person.getString("name");
				String party = obj.getString("party");
				String description = obj.getString("description");
				politicians.add(new Politician(name, party, description, id));
			}

		} catch(Exception e) {
			e.printStackTrace();
		}

		return politicians;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Politician> getFavoritePoliticians(Context context) {
		ArrayList<Politician> politicians = new ArrayList<>();

		try {
			InputStream is = context.openFileInput("favorites.dat");
			ObjectInputStream ois = new ObjectInputStream(is);
			ArrayList<Politician> data = (ArrayList<Politician>)ois.readObject();
			ois.close();
			if(data != null) {
				politicians.addAll(data);
			}

		} catch(Exception e) {
			e.printStackTrace();
		}

		return politicians;
	}

	@SuppressWarnings("unchecked")
	public static void saveToFavorites(Context context, Politician politician) {

		ArrayList<Politician> data = null;

		try {
			InputStream is = context.openFileInput("favorites.dat");
			ObjectInputStream ois = new ObjectInputStream(is);
			data = (ArrayList<Politician>)ois.readObject();
			ois.close();
		} catch(Exception e) {
			e.printStackTrace();
		}

		if(data == null) {
			data = new ArrayList<>();
		}

		if(!data.contains(politician)) {
			data.add(politician);
		}

		try {

			OutputStream os = context.openFileOutput("favorites.dat", Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(data);
			oos.close();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}