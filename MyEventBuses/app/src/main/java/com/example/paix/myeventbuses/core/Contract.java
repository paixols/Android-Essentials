package com.example.paix.myeventbuses.core;

import android.content.Context;

/**
 * Created by paix on 8/3/17.
 * Contract interface will be the core of the Contract-Interface for the app activities
 */

public interface Contract {
    //Base View Contract Interface
    interface BaseView {
        //Context
        Context getContext();
        //Error message
        void showError(String errorMessage);
        //Progress Dialog control
        void showProgress();
        void hideProgress();
    }
    //LifeCycle Contract Interface
    interface BaseController {
        void onStart();
        void onResume();
        void onPause();
        void onStop();
    }
}
