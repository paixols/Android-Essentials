package com.example.paix.myeventbuses.core;

import android.annotation.SuppressLint;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paix on 8/3/17.
 * Core Activity to Control further application activities (Template)
 *
 * Sets up absolutely basic components to an Activity, assuming the Activity will mainly hold a fragment
 * Contains methods to deal with fragments
 */

public class CoreActivity extends AppCompatActivity {

    //TAG
    private static final String TAG = "CoreActivity";
    //Properties
    private List<OnBackPressListener> mOnBackPressListeners = new ArrayList<>();
    //Find View
    @SuppressWarnings("unchecked")
    public <V extends View> V findById(@IdRes int id) {
        return (V) findViewById(id);
    }

    //Add Fragment to Activity
    @SuppressLint("CommitTransaction")
    public void addFragment(@IdRes int containerId, @NonNull Fragment fragment, boolean addToBackStack) {
        //Dev
        Log.i(TAG, "addFragment: ");
        String name = fragment.getClass().getName();
        FragmentTransaction add = getSupportFragmentManager().beginTransaction().add(containerId, fragment, name);
        if (addToBackStack) {
            add.addToBackStack(name);
        }
        add.commit();
    }

    //Replace Fragment in Activity
    @SuppressLint("CommitTransaction")
    public void replaceFragment(@IdRes int containerId, @NonNull Fragment fragment, boolean addToBackStack) {
        //Dev
        Log.i(TAG, "replaceFragment: ");
        String name = fragment.getClass().getName();
        FragmentTransaction replaceTransaction = getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment, name);
        if (addToBackStack) {
            replaceTransaction.addToBackStack(name);
        }
        replaceTransaction.commit();
    }

    //Remove Fragment from Activity (fragment)
    public void removeFragment(@NonNull Fragment fragment) {
        //Dev
        Log.i(TAG, "removeFragment: ");
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    //Remove Fragment (fragment id)
    public void removeFragment(@IdRes int id) {
        //Dev
        Log.i(TAG, "removeFragment: ");
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(id);
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

    //Remove Fragment (fragment tag)
    public void removeFragment(@NonNull String tag) {
        //Dev
        Log.i(TAG, "removeFragment: ");
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

    //Remove fragment (fragment class)
    public void removeFragment(Class<? extends Fragment> fragmentClass) {
        //Dev
        Log.i(TAG, "removeFragment: ");
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentClass.getName());
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

    //Remove fragment from back-stack
    public void popFragment() {
        //Dev
        Log.i(TAG, "popFragment: ");
        getSupportFragmentManager().popBackStack();
    }

    /**
     * Remove fragment if available on back-stack
     * If not , act as back button pressed upon activity
     */

    @Override
    public void onBackPressed() {
        //Dev
        Log.i(TAG, "onBackPressed: ");
        if (interruptedByListener()) {
            //noinspection UnnecessaryReturnStatement
            return;
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * If there is back-stack remove the top fragment
     * If no back-stack is present, finish after transition.
     * */
    public void closeFromChild() {
        //Dev
        Log.i(TAG, "closeFromChild: ");
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            supportFinishAfterTransition();
        }
    }

    //Check back button pressed active listener
    private boolean interruptedByListener() {
        //Dev
        Log.i(TAG, "interruptedByListener: ");
        boolean interrupt = false;
        for (OnBackPressListener listener : mOnBackPressListeners) {
            if (listener.onBackPressed()) {
                interrupt = true;
            }
        }
        return interrupt;
    }

    //Add On-back press listener
    public void addOnBackPressListener(OnBackPressListener listener) {
        //Dev
        Log.i(TAG, "addOnBackPressListener: ");
        if (listener != null) {
            mOnBackPressListeners.add(listener);
        }
    }

    //Remove On-back press listener
    public void removeOnBackPressListener(OnBackPressListener listener) {
        //Dev
        Log.i(TAG, "removeOnBackPressListener: ");
        mOnBackPressListeners.remove(listener);
    }

    //Back Button pressed listener
    public interface OnBackPressListener {
        boolean onBackPressed();
    }

}
