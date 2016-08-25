package com.fullsail.android.politicalwidgets;

import android.app.IntentService;
import android.content.Intent;

import com.fullsail.android.politicalwidgets.storage.Politician;
import com.fullsail.android.politicalwidgets.utils.PoliticiansHelper;

public class FavoriteSaverService extends IntentService {

    public static final String EXTRA_POLITICIAN = "FavoriteSaverService.EXTRA_POLITICIAN";

    public FavoriteSaverService() {
        super("FavoriteSaverService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!intent.hasExtra(EXTRA_POLITICIAN)) {
            throw new IllegalArgumentException("You must specify a politician to save.");
        }

        Politician p = (Politician) intent.getSerializableExtra(EXTRA_POLITICIAN);
        PoliticiansHelper.saveToFavorites(this, p);

        Intent updateIntent = new Intent(WidgetUpdater.ACTION_UPDATE_WIDGETS);
        sendBroadcast(updateIntent);
    }

}
