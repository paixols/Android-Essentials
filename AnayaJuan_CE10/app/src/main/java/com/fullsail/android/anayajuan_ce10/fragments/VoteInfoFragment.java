// Juan Pablo Anaya
// MDF3 - 201608
// VoteInfoFragment + Task

package com.fullsail.android.anayajuan_ce10.fragments;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fullsail.android.anayajuan_ce10.R;

public class VoteInfoFragment extends Fragment {

    public static final String TAG = "VoteInfoFragment.TAG";
    private static final String ARG_VOTE_ID = "VoteInfoFragment.ARG_VOTE_ID";

    public static VoteInfoFragment newInstance(int voteId) {
        VoteInfoFragment frag = new VoteInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_VOTE_ID, voteId);
        frag.setArguments(args);
        return frag;
    }

    private int mVoteId;
    private VoteLoaderTask mTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.vote_info_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        if (args == null || args.isEmpty()) {
            throw new IllegalArgumentException("Must pass in a valid vote ID for viewing vote information.");
        }

        if (args.containsKey(ARG_VOTE_ID)) {
            mVoteId = args.getInt(ARG_VOTE_ID);
        }
        //Dev
        Log.i(TAG, "onActivityCreated: " + "VOTE ID: " + mVoteId);

        loadVoteInfo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTask();
    }

    private void loadVoteInfo() {
        stopTask();
        mTask = new VoteLoaderTask();
        mTask.execute(mVoteId);
    }

    private void stopTask() {
        if (mTask != null) {
            mTask.cancel(false);
            mTask = null;
        }
    }

    //Update User Interface
    private void updateUI(Vote vote) {
        View root = getView();
        //Set Politician Info
        TextView tv;
        if (root != null) {
            tv = (TextView) root.findViewById(R.id.chamber);
            tv.setText(vote.chamber);

            tv = (TextView) root.findViewById(R.id.question);
            tv.setText(vote.question);

            tv = (TextView) root.findViewById(R.id.session);
            tv.setText(vote.session);

            tv = (TextView) root.findViewById(R.id.outcome);
            tv.setText(vote.outcome);
        }

    }

    // TODO: Fix class declaration to have proper return type.
    private class VoteLoaderTask extends AsyncTask<Integer, Void, Vote> {

        @Override
        protected Vote doInBackground(Integer... params) {

            int voteId = params[0];

            String urlString = "https://www.govtrack.us/api/v2/vote?id=" + voteId; //49642

            try {

                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream is = connection.getInputStream();
                String data = IOUtils.toString(is);
                is.close();

                connection.disconnect();

                JSONObject response = new JSONObject(data);
                JSONArray objects = response.getJSONArray("objects");

                if (objects.length() > 0) {
                    JSONObject obj = objects.getJSONObject(0);

                    Vote vote = new Vote();
                    vote.chamber = obj.getString("chamber_label");
                    vote.question = obj.getString("question");
                    vote.session = obj.getString("session");
                    vote.outcome = obj.getString("result");

                    return vote;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Vote result) {
            super.onPostExecute(result);

            if (result == null) {
                getActivity().finish();
                return;
            }

            updateUI(result);

            mTask = null;
        }
    }

    private class Vote {
        public String chamber;
        public String question;
        public String session;
        public String outcome;
    }
}
