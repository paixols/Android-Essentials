// Juan Pablo Anaya
// MDF3 - 201608
// FavoriteSaverService

package com.fullsail.android.anayajuan_ce10;

import android.app.IntentService;
import android.content.Intent;

import com.fullsail.android.anayajuan_ce10.storage.Politician;
import com.fullsail.android.anayajuan_ce10.utils.PoliticiansHelper;

public class FavoriteSaverService extends IntentService {

    /*Properties*/
    public static final String EXTRA_POLITICIAN = "FavoriteSaverService.EXTRA_POLITICIAN";

    /*Default Constructor*/
    public FavoriteSaverService() {
        super("FavoriteSaverService");
    }

    /*LifeCycle*/
    @Override
    protected void onHandleIntent(Intent intent) {
        //Politician Info Check
        if (!intent.hasExtra(EXTRA_POLITICIAN)) {
            throw new IllegalArgumentException("You must specify a politician to save.");
        }

        Politician p = (Politician) intent.getSerializableExtra(EXTRA_POLITICIAN);
        PoliticiansHelper.saveToFavorites(this, p);

        //TODO Check this intent
        Intent updateIntent = new Intent(WidgetUpdater.ACTION_UPDATE_WIDGETS);
        sendBroadcast(updateIntent);
    }

}
