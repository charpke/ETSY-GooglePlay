package com.chuckharpke.android.samples.googleplayservices.api;

import com.chuckharpke.android.samples.googleplayservices.model.ActiveListings;



import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;


/**
 * Created by charpke on 5/30/15.
 */
public interface Api {

    @GET("/listings/active")
    void activeListings(@Query("includes") String includes,
                        Callback<ActiveListings> callback);
}
