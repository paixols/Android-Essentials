package com.example.paix.myeventbuses;

import android.util.Log;

import com.example.paix.myeventbuses.core.CoreActivity;

/**
 * Created by paix on 8/3/17.
 * Event that will be broadcasted when needed
 * This will be used for communication between components
 */

public class Events {

    //Event used to send message from Fragment to Activity
    public static class FragmentActivityMessage{
        //Tag
        private static final String TAG = "FragmentActivityMessage";
        //Properties
        private String message;
        //Constructor
        public FragmentActivityMessage(String message){
            this.message = message;
        }
        //Getter
        public String getMessage() {
            Log.i(TAG, "getMessage: ");
            return message;
        }
    }

    //Event used to send message from Activity to Fragment
    public static class ActivityFragmentMessage{
        //TAG
        private static final String TAG = "ActivityFragmentMessage";
        //Properties
        private String message;
        //Constructor
        public ActivityFragmentMessage(String message) {
            this.message = message;
        }
        //Getter
        public String getMessage() {
            return message;
        }
    }

    //Event used to send message from Activity to Activity
    public static class ActivityActivityMessage{
        //TAG
        private static final String TAG = "ActivityActivityMessage";
        //Properties
        private String message;
        //Constructor
        public ActivityActivityMessage(String message) {
            this.message = message;
        }
        //Getter
        public String getMessage() {
            return message;
        }
    }
}
