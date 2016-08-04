// Juan Pablo Anaya
// MDF3 - 201608
// GridViewFragment

package com.paix.jpam.anayajuan_ce02.GridView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.paix.jpam.anayajuan_ce02.R;
import com.paix.jpam.anayajuan_ce02.Storage.ExternalStorage;

import java.util.ArrayList;

public class GridViewFragment extends Fragment {

    //TAG
    public static final String TAG = "GridViewFragment";
    //Grid View
    private GridView mGridView;
    //Images for GridViewAdapter
    private ArrayList images;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Custom View
        View view = inflater.inflate(R.layout.fragment_gridview, container, false);
        //Declare UI
        mGridView = (GridView) view.findViewById(R.id.GridView_Fragment);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*Open image with ACTION_VIEW Intent*/
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + images.get(i)), "image/jpg"); // Adding "file://" is necessary
                startActivity(intent);
                //Dev
                Log.i(TAG, "onItemClick: " + "Image Clicked in GridView");
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshAdapter();
    }

    /*Refresh Adapter*/
    public void refreshAdapter() {

        //Refresh Adapter data
        images = ExternalStorage.readExternal(getActivity());
        GridViewAdapter adapter = new GridViewAdapter(getActivity(), images);
        mGridView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
        //Dev
        Log.i(TAG, "refreshAdapter: " + images.size() + "/" + adapter.getCount());
    }

}
