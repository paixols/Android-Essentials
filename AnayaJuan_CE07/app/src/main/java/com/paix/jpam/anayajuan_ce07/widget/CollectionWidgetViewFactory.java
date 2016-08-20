// Juan Pablo Anaya
// MDF3 - 201608
// CollectionWidgetViewFactory

package com.paix.jpam.anayajuan_ce07.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.paix.jpam.anayajuan_ce07.R;
import com.paix.jpam.anayajuan_ce07.dataModel.Person;
import com.paix.jpam.anayajuan_ce07.utilities.StorageHelper;

import java.util.ArrayList;

class CollectionWidgetViewFactory implements RemoteViewsService.RemoteViewsFactory {
    //TAG
    //private static final String TAG = "CollectionWidgetViewFactory";

    //ID CONSTANT
    private static final int ID_CONSTANT = 0x0001;
    //Context
    private final Context mContext;
    //ArrayList of Persons
    private ArrayList persons;

    /*Constructor*/
    public CollectionWidgetViewFactory(Context context) {
        //Set Context
        mContext = context;
    }

    /*LifeCycle*/
    @Override
    public void onCreate() {
        //Read Data from internal storage (Data used for the Views Factory)
        updateFromInternalStorage();
    }


    @Override
    public void onDataSetChanged() {
        //updateFromInternalStorage();
        updateFromInternalStorage();
    }

    @Override
    public void onDestroy() {
        /*Clean data we have*/
        persons.clear();
        persons = null;
    }

    @Override
    public int getCount() {
        if (persons == null || persons.size() == 0) {
            return 0;
        }
        return persons.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        //Person
        Person person = (Person) persons.get(position);
        //Remote Views to populate
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.viewholder_list);
        //Retrieve each piece of data to populate
        remoteViews.setTextViewText(R.id.TextView_ViewHolder_FirstName, person.getFirstName());
        remoteViews.setTextViewText(R.id.TextView_ViewHolder_LastName, person.getLastName());
        String age = String.valueOf(person.getAge());
        remoteViews.setTextViewText(R.id.TextView_ViewHolder_Age, age);

        //Set Fill-in Intent (Intent needs to be on the root layout of the view holder list !
        Intent intent = new Intent();
        intent.putExtra(mContext.getString(R.string.Person_key), person);
        intent.putExtra(mContext.getString(R.string.Started_From_Widget), true);//Activity Started From widget
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        remoteViews.setOnClickFillInIntent(R.id.LinearLayout_WidgetHolder_List_Root, intent);


        //Return Remote Views Object
        return remoteViews;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return ID_CONSTANT + position;
    }

    @Override
    public boolean hasStableIds() {
        /*Because it's based on the position we have stable IDs*/
        return true;
    }

    /*Update Array of Data from Internal Storage*/
    private void updateFromInternalStorage() {
        persons = StorageHelper.readInternalStorage(mContext);
        if (persons == null) {
            persons = new ArrayList<>();
            persons.clear();
        }
    }
}
