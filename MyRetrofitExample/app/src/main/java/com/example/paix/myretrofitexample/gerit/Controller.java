package com.example.paix.myretrofitexample.gerit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by paix on 8/1/17.
 * - This class creates the Retrofit client, calls the Gerrit API and handles the result
 */

public class Controller implements Callback<List<Change>> {

    //TAG
    private static final String TAG = "Controller";
    //URL
    static final String BASE_URL = "https://git.eclipse.org/r/";

    //LifeCycle
    public void start() {
        //Parser
        Gson gson = new GsonBuilder()
                .setLenient() //Make parser liberal on data type
                .create();
        //RetroFit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        //API
        GerritAPI gerritApi = retrofit.create(GerritAPI.class); //Interface
        Call<List<Change>> call = gerritApi.loadChanges("status:open");
        call.enqueue(this);
    }

    //Retrofit Callback required methods
    @Override
    public void onResponse(Call<List<Change>> call, Response<List<Change>> response) {
        if (response.isSuccessful()) {
            List<Change> changesList = response.body();
            //Dev
            for(Change change: changesList){
                Log.i(TAG, "onResponse: " + change.getSubject());
            }
        } else {
            //Dev
            Log.i(TAG, "onResponse failure: " + response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Change>> call, Throwable t) {
        //Dev
        Log.i(TAG, "onFailure:  GerritAPI Failure" );
        //Dev
        t.printStackTrace();
    }

}
