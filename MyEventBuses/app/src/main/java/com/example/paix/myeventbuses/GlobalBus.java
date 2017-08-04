package com.example.paix.myeventbuses;

import org.greenrobot.eventbus.EventBus;

/**
 *
 * Created by paix on 8/3/17.
 * Global Bus singleton class
 *
 * Functionality:
 * Register : GlobalBus.getBus().register(this);
 * Un-Register : GlobalBus.getBus().unregister(this);
 * Broadcast an event : GlobalBus.getBus().post(eventName);
 * Receive an event : you need to write a method with annotation @Subscribe as below.
 * @Subscribe
 * public void getMessage(Events.EventName event) {}
 *
 */

public class GlobalBus {

    //TAG
    private static final String TAG = "GlobalBus";
    //Properties
    private static EventBus sBus;

    public static EventBus getsBus() {
        if (sBus == null) sBus = EventBus.getDefault();
        return sBus;
    }
}
