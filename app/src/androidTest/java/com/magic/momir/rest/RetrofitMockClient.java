package com.magic.momir.rest;


import java.io.IOException;
import java.util.Collections;

import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class RetrofitMockClient implements Client {
    private static final int HTTP_OK_STATUS = 200;

    @Override
    public Response execute(Request request) throws IOException {
        return createSearchResult(HTTP_OK_STATUS, MockData.VALID_SEARCH_RESPONSE);
    }

    private Response createSearchResult(final int responseCode, final String json) {
        return new Response("url", responseCode, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json", json.getBytes()));
    }
}
