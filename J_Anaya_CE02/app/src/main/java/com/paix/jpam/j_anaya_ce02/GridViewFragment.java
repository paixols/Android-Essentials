// Juan Pablo Anaya
// MDF3 - CE02
// GridViewFragment
package com.paix.jpam.j_anaya_ce02;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.GridView;

public class GridViewFragment extends Fragment {

    //TAG
    private static final String TAG = "GridViewFragment";
    //Grid View
    GridView mGridView;

    /*Life Cycle*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
