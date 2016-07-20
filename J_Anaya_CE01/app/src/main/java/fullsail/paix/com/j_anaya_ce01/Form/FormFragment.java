// Juan Anaya
// MDF3 - CE01
// FormFragment

package fullsail.paix.com.j_anaya_ce01.Form;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import fullsail.paix.com.j_anaya_ce01.R;


public class FormFragment extends Fragment {

    //TAG
    private static final String TAG = "FormFragment";
    //Interface
    SaveForm listener;

    /*LifeCycle*/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Outer Class Interface
        if (context instanceof SaveForm) {
            listener = (SaveForm) context;
        } else {
            throw new IllegalArgumentException("Please Add Save Form Interface");
        }
    }

    /*Menu*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_form, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_saveForm:

                //Interface Listener for Form Activity
                
                //Dev
                Log.i(TAG, "onOptionsItemSelected: " + "Save Form");
                return true;
        }
        return false;
    }


}
