// Juan Pablo Anaya
// MDF3 - 201608
// DetailFragment

package com.paix.jpam.anayajuan_ce04.detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.paix.jpam.anayajuan_ce04.R;
import com.paix.jpam.anayajuan_ce04.utilities.StorageHelper;

import java.io.File;

public class DetailFragment extends Fragment {

    //TAG
    private static final String TAG = "DetailFragment";

    /*Properties*/
    private TextView latText;
    private TextView lngText;
    private ImageView image;
    //Bundle properties
    private LatLng latLng;
    private String imageName;
    //Interface
    private OnDetailMenuSelection listener;
    //Files
    private File currentFile;


    /*Constructor*/
    public DetailFragment newInstanceOf(LatLng latLng, String imageName) {
        //Create Instance
        DetailFragment detailFragment = new DetailFragment();
        //Create Bundle
        Bundle args = new Bundle();
        args.putParcelable("LatLng_key", latLng);
        args.putString("ImageName_key", imageName);
        detailFragment.setArguments(args);
        //Return Instance
        return detailFragment;
    }

    /*LifeCycle*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Outer Class Interface
        if (context instanceof OnDetailMenuSelection) {
            listener = (OnDetailMenuSelection) context;
        } else {
            throw new IllegalArgumentException(("Please Add News Feed Public-Interface"));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Menu
        setHasOptionsMenu(true);
    }

    /*Menu*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                //Dev Should delete the Item
                Log.i(TAG, "onOptionsItemSelected: " + "Delete Item");
                listener.selectedWindow();
                return true;
        }
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        latText = (TextView) v.findViewById(R.id.TextView_Detail_Lat);
        lngText = (TextView) v.findViewById(R.id.TextView_Detail_Lng);
        image = (ImageView) v.findViewById(R.id.ImageView_Detail);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (handleArguments(args)) {
            latText.setText(String.valueOf(latLng.latitude));
            lngText.setText(String.valueOf(latLng.longitude));
            //Set Image on ImageView
            StorageHelper storageHelper = new StorageHelper();
            currentFile = storageHelper.getFileFromName(imageName);
            if (currentFile != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(currentFile.getPath());
                image.setImageBitmap(bitmap);
            } else {
                Log.i(TAG, "selectedWindow: " + "File has no Photo");
                Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.earth);
                image.setImageBitmap(bitmap);
            }
        }
    }

    private Boolean handleArguments(Bundle args) {
        if (args != null) {
            latLng = args.getParcelable("LatLng_key");
            imageName = args.getString("ImageName_key");
            return true;
        }
        return false;
    }
}
