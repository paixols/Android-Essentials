package com.example.paix.myretrofitexample.gerit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by paix on 8/1/17.
 * Gerrit API
 */

public interface GerritAPI {

    /*Subject of "Changes" from the Gerrit API*/
    @GET("changes/")
    Call<List<Change>> loadChanges(@Query("q") String status);

}
