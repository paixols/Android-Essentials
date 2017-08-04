package com.example.paix.myeventbuses.core;

import android.annotation.SuppressLint;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paix on 8/3/17.
 * Core Activity to Control further application activities (Template)
 * <p>
 * Sets up absolutely basic components to an Activity, assuming the Activity will mainly hold a fragment
 * Contains methods to deal with fragments
 */

public class CoreActivity extends AppCompatActivity {

    //TAG
    private static final String TAG = "CoreActivity";
    //Properties
    private List<OnBackPressListener> mOnBackPressListeners = new ArrayList<>();

    //Add Fragment to Activity
    @SuppressLint("CommitTransaction")
    public void addFragment(@IdRes int containerId, @NonNull Fragment fragment, boolean addToBackStack) {
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
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    //Remove Fragment (fragment id)
    public void removeFragment(@IdRes int id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(id);
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

    //Remove Fragment (fragment tag)
    public void removeFragment(@NonNull String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

    //Remove fragment (fragment class)
    public void removeFragment(Class<? extends Fragment> fragmentClass) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentClass.getName());
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

    //Remove fragment from back-stack
    public void popFragment() {
        getSupportFragmentManager().popBackStack();
    }

    //TODO: Keep documenting
    @Override
    public void onBackPressed() {
        if (interruptedByListener()) {
            //noinspection UnnecessaryReturnStatement
            return;
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void closeFromChild() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            supportFinishAfterTransition();
        }
    }

    private boolean interruptedByListener() {
        boolean interrupt = false;
        for (OnBackPressListener listener : mOnBackPressListeners) {
            if (listener.onBackPressed()) {
                interrupt = true;
            }
        }
        return interrupt;
    }

    public void addOnBackPressListener(OnBackPressListener listener) {
        if (listener != null) {
            mOnBackPressListeners.add(listener);
        }
    }

    public void removeOnBackPressListener(OnBackPressListener listener) {
        mOnBackPressListeners.remove(listener);
    }

    public interface OnBackPressListener {
        boolean onBackPressed();
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V findById(@IdRes int id) {
        return (V) findViewById(id);
    }

}
