package com.example.paix.myeventbuses.core;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.appcompat.BuildConfig;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by paix on 8/3/17.
 */

public abstract class CoreFragment extends Fragment implements CoreActivity.OnBackPressListener {

    //TAG
    private static final String TAG = "CoreFragment";
    //Properties
    public View view;

    //Add internal fragment
    @SuppressLint("CommitTransaction")
    public void addFragmentInternal(int containerId, Fragment fragment, boolean addToBackStack) {
        //Dev
        Log.i(TAG, "addFragmentInternal: ");
        String name = fragment.getClass().getName();
        FragmentTransaction add = getChildFragmentManager().beginTransaction().add(containerId, fragment, name);
        if (addToBackStack) {
            add.addToBackStack(name);
        }
        add.commit();
    }

    //Replace internal fragment
    public void replaceFragmentInternal(@IdRes int containerId, @NonNull Fragment fragment) {
        getChildFragmentManager().beginTransaction()
                .replace(containerId, fragment, fragment.getClass().getName())
                .commit();
    }

    //Remove internal fragment (fragment)
    public void removeFragmentInternal(@NonNull Fragment fragment) {
        if (getChildFragmentManager().findFragmentByTag(fragment.getClass().getName()) != null) {
            getChildFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    //Remove internal fragment (id)
    public void removeFragmentInternal(@IdRes int id) {
        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(id);
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

    //Remove internal fragment (tag)
    public void removeFragmentInternal(@NonNull String tag) {
        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

    //Remove internal fragment (class)
    public void removeFragmentInternal(Class<? extends Fragment> fragmentClass) {
        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentClass.getName());
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }


    //Layout ID
    protected abstract int layoutId();

    /*Fragment LifeCycle*/

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(layoutId(), container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof CoreActivity) {
            ((CoreActivity) getActivity()).addOnBackPressListener(this);
        }
    }

    @Override
    public void onDestroy() {
        if (getActivity() instanceof CoreActivity) {
            ((CoreActivity) getActivity()).removeOnBackPressListener(this);
        }
        super.onDestroy();
    }

    @Override
    public boolean onBackPressed() {
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            getChildFragmentManager().popBackStack();
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    public ActionBar getActionBar() {
        if (getActivity() instanceof AppCompatActivity) {
            return ((AppCompatActivity) getActivity()).getSupportActionBar();
        } else
            return null;
    }

    //Fragment View
    @SuppressWarnings("unchecked")
    public <V extends View> V findById(@IdRes int id) {
        if (getView() != null) {
            return (V) getView().findViewById(id);
        } else {
            if (BuildConfig.DEBUG) {
                throw new IllegalStateException("getView() returned null");
            } else {
                return null;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V findById(@NonNull View view, @IdRes int id) {
        return (V) view.findViewById(id);
    }

}
