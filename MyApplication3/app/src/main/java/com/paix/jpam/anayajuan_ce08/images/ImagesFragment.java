// Juan Pablo Anaya
// MDF3 - 201608
// ImagesFragment

package com.paix.jpam.anayajuan_ce08.images;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
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

import java.util.ArrayList;

public class ImagesFragment extends Fragment {

    //TAG
    private static final String TAG = "ImagesFragment";

    /*Properties*/
    Context mContext;
    GridView gridView;
    ArrayList filePaths;
    public MyCursorAdapter adapter;
    public Cursor cs;

    /*Default Constructor*/
    public ImagesFragment() {
        super();
    }

    /*Constructor*/
    public ImagesFragment newInstanceOf(Context context, ArrayList<String> filePaths) {
        //New Instance
        ImagesFragment imagesFrag = new ImagesFragment();
        //Bundle
        mContext = context;
        Bundle arguments = new Bundle();
        arguments.putStringArrayList(context.getString(R.string.File_paths_key), filePaths);
        //Return Instance
        return imagesFrag;
    }

    /*LifeCycle*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Menu
        setHasOptionsMenu(true);
        //Get Bundle Info
        Bundle arguments = getArguments();
        if (arguments != null) {
            handleArguments(arguments);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_gridview, container, false);
        //Declare User Interface
        gridView = (GridView) v.findViewById(R.id.GridView_Fragment);

//        //Content Resolver to get the Images from External Public Storage
//        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI; // External Public Storage
//        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA}; // Images ID's
//
//        //Create the cursor pointing to External Public Storage
//        cs = getActivity().getContentResolver().query(uri,
//                projection, // Return the ID Column
//                null,       // Return all Rows
//                null,
//                null);      //Do not sort
//
//
//        adapter = new MyCursorAdapter(getActivity(), cs);
//        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Open Image with ACTION_VIEW Intent*/
//                Intent viewIntent = new Intent();
//                viewIntent.setAction(Intent.ACTION_VIEW);
//                cs.moveToPosition(position);
//                int column_index = cs.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                viewIntent.setDataAndType(Uri.parse("file://" + cs.getString(column_index)), "image/jpg");
//                startActivity(viewIntent);
                //Dev
                //Log.i(TAG, "onItemClick: " + position);

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
//                //Update Cursor Adapter
//                MyCursorAdapter.updateCursorAdapter(getActivity(), cs, adapter);
                //Update the Arrays with Async Task
//                ThumbnailTask thumbnailTask = new ThumbnailTask();
//                thumbnailTask.execute();
                //Dev
                Log.i(TAG, "onOptionsItemSelected: " + "REFRESH");


                return true;
        }
        return false;
    }
    /*Arguments Handling*/
    public void handleArguments(Bundle arguments) {
        filePaths = arguments.getStringArrayList(mContext.getString(R.string.File_paths_key));
        //Dev
        Log.i(TAG, "handleArguments: " + filePaths.size());
    }

}
