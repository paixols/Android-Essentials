// Juan Pablo Anaya
// MDF3 - 201608
// VoteHistoryFragment + Adapter + Task

package com.fullsail.android.anayajuan_ce10.fragments;

import java.util.ArrayList;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fullsail.android.anayajuan_ce10.R;
import com.fullsail.android.anayajuan_ce10.VoteInfoActivity;
import com.fullsail.android.anayajuan_ce10.storage.Politician;
import com.fullsail.android.anayajuan_ce10.storage.VoteInfo;
import com.fullsail.android.anayajuan_ce10.utils.VoteInfoHelper;

public class VoteHistoryFragment extends ListFragment {

    //TAG
    public static final String TAG = "VoteHistoryFragment.TAG";
    /*Properties*/
    public static final String ARG_POLITICIAN = "VoteHistoryFragment.ARG_POLITICIAN";
    private static final int REQUEST_VOTE_INFO = 0x05001;
    private Politician mPolitician;
    private VoteHistoryTask mTask;

    /*Instance Constructor*/
    public static VoteHistoryFragment newInstance(Politician politician) {
        //Create Instance
        VoteHistoryFragment frag = new VoteHistoryFragment();
        //Bundle
        Bundle args = new Bundle();
        args.putSerializable(ARG_POLITICIAN, politician);
        frag.setArguments(args);
        //Return Instance
        return frag;
    }

    /*LifeCycle*/
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Get Bundle
        Bundle args = getArguments();
        if (args == null || args.isEmpty()) {
            throw new IllegalArgumentException("Must pass in a politician to view voting history.");
        }
        //Bundle Info Check
        if (args.containsKey(ARG_POLITICIAN)) {
            mPolitician = (Politician) args.getSerializable(ARG_POLITICIAN);
            if (mPolitician == null) {
                throw new IllegalArgumentException("Must pass in a valid politician to view voting history.");
            }
        }
        //Load Voting History
        loadVotingHistory();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTask();
    }

    /*Custom Methods*/
    //Load Voting History (Start Task)
    private void loadVotingHistory() {
        stopTask();
        mTask = new VoteHistoryTask();
        mTask.execute(mPolitician.getId());
    }

    //Load Voting History (Stop Task)
    private void stopTask() {
        if (mTask != null) {
            mTask.cancel(false);
            mTask = null;
        }
    }

    /*Selected Voting session*/
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        VoteHistoryAdapter adapter = (VoteHistoryAdapter) l.getAdapter();
        VoteInfo info = adapter.getItem(position);

        Intent intent = new Intent(getActivity(), VoteInfoActivity.class);
        intent.putExtra(VoteInfoActivity.EXTRA_VOTE_ID, info.getId());
        startActivityForResult(intent, REQUEST_VOTE_INFO);
    }

    /*Voting History Base Adapter*/
    private class VoteHistoryAdapter extends BaseAdapter {

        private static final int ID_CONSTANT = 0x20202020;

        private Context mContext;
        private ArrayList<VoteInfo> mVotes;

        public VoteHistoryAdapter(Context context, ArrayList<VoteInfo> votes) {
            mContext = context;
            mVotes = votes;
        }

        @Override
        public int getCount() {
            return mVotes.size();
        }

        @Override
        public VoteInfo getItem(int position) {
            return mVotes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return ID_CONSTANT + position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.vote_info_list_item, parent, false);
            }

            VoteInfo info = getItem(position);

            TextView tv = (TextView) convertView.findViewById(R.id.question);
            tv.setText(info.getQuestion());

            tv = (TextView) convertView.findViewById(R.id.vote);
            tv.setText(info.getVote());

            return convertView;
        }

    }

    private class VoteHistoryTask extends AsyncTask<Integer, Void, ArrayList<VoteInfo>> {

        @Override
        protected ArrayList<VoteInfo> doInBackground(Integer... params) {

            int politicianId = params[0];

            ArrayList<VoteInfo> votes = VoteInfoHelper.getVoteHistory(politicianId);

            return votes;
        }

        @Override
        protected void onPostExecute(ArrayList<VoteInfo> result) {
            super.onPostExecute(result);

            mTask = null;

            VoteHistoryAdapter adapter = new VoteHistoryAdapter(getActivity(), result);
            setListAdapter(adapter);
        }

    }
}