// Juan Pablo Anaya
// MDF3 - 201608
// ImagesFragment

package com.paix.jpam.anayajuan_ce08.images;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.paix.jpam.anayajuan_ce08.R;

public class ImagesFragment extends Fragment {

    //TAG
    private static final String TAG = "ImagesFragment";

    private Cursor mCursor;
    private MyCursorAdapter mAdapter;

    /*Default Constructor*/
    public ImagesFragment() {
        super();
    }

    /*Constructor*/
    public ImagesFragment newInstanceOf(Cursor cursor) {
        //New Instance
        ImagesFragment imagesFrag = new ImagesFragment();
        mCursor = cursor;
        //Return Instance
        return imagesFrag;
    }

    /*LifeCycle*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Menu
        setHasOptionsMenu(true);
        //Dev
        Log.i(TAG, "onCreate: " + "IMAGES_FRAGMENT");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_gridview, container, false);
        //Declare User Interface
        GridView gridView = (GridView) v.findViewById(R.id.GridView_Fragment);
        mAdapter = new MyCursorAdapter(getActivity(), mCursor);

        //Update Cursor Adapter (Shows images on App Launch)
        mCursor = MyCursorAdapter.updateCursorAdapter(getActivity(), mAdapter);

        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Open Image with ACTION_VIEW Intent*/
                Intent viewIntent = new Intent();
                viewIntent.setAction(Intent.ACTION_VIEW);
                //mCursor.moveToPosition(position);
                int column_index = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                viewIntent.setDataAndType(Uri.parse("file://" + mCursor.getString(column_index))
                        , "image/jpg");
                startActivity(viewIntent);
//                //Dev
//                Log.i(TAG, "onItemClick: " + position);
            }
        });
        return v;
    }

    /*Menu*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_gridview, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MenuItem_Refresh:
                //Update Cursor Adapter
                mCursor = MyCursorAdapter.updateCursorAdapter(getActivity(), mAdapter);
//                //Dev
//                Log.i(TAG, "onOptionsItemSelected: " + "REFRESH");
                return true;
        }
        return false;
    }

}
