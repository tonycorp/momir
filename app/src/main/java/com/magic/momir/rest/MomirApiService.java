package com.magic.momir.rest;

import com.magic.momir.models.Card;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface MomirApiService {
    @GET("/momir/{cmc}")
    public void getCard(@Path("cmc") final String query, final Callback<Card[]> callback);
}
