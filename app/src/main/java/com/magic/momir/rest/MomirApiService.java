package com.magic.momir.rest;

import com.magic.momir.models.Card;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface MomirApiService {
    @GET("/search")
    void getCards(@Query("?q") final String query, final Callback<Card[]> callback);
}
