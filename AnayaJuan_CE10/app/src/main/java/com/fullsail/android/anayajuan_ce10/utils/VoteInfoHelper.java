// Juan Pablo Anaya
// MDF3 - 201608
// VoteInfoHelper

package com.fullsail.android.anayajuan_ce10.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fullsail.android.anayajuan_ce10.storage.VoteInfo;

public class VoteInfoHelper {

    /*Politician Vote History*/
    public static ArrayList<VoteInfo> getVoteHistory(int politicianId) {
        ArrayList<VoteInfo> votes = new ArrayList<>();
        //Invalid Politician ID Check
        if (politicianId == -1) {
            throw new IllegalArgumentException("You must pass in a valid politician ID.");
        }
        /*Retrieve Selected Politician*/
        try {
            //Build URL
            String urlString = "https://www.govtrack.us/api/v2/vote_voter/?limit=100" +
                    "&order_by=created&fields=vote__id,created,option__value,vote__category," +
                    "vote__chamber,vote__question,vote__number&person=" + politicianId;

            URL url = new URL(urlString);
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
                JSONObject option = obj.getJSONObject("option");
                JSONObject vote = obj.getJSONObject("vote");

                int id = vote.getInt("id");
                String question = vote.getString("question");
                String voteOption = option.getString("value");
                votes.add(new VoteInfo(id, question, voteOption));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return votes;
    }
}