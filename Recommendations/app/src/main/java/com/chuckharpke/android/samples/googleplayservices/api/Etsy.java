package com.chuckharpke.android.samples.googleplayservices.api;

import com.chuckharpke.android.samples.googleplayservices.model.ActiveListings;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by charpke on 5/24/15.
 */
public class Etsy {

    private static final String API_KEY = "miewgbuqdhe6884xof5zq7h7";

    public static RequestInterceptor getInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addEncodedQueryParam("api key", API_KEY);
            }
        };
    }

    private static Api getApi() {
        return new RestAdapter.Builder()
                .setEndpoint("https://openapi.etsy.com/v2")
                .setRequestInterceptor(getInterceptor())
                .build()
                .create(Api.class);
    }

    public static void getActiveListings(Callback<ActiveListings> callback) {
        getApi().activeListings("Images.Shop", callback);

    }
}
