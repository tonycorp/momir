package com.magic.momir.dagger;

import com.magic.momir.activities.MainActivity;
import com.magic.momir.rest.MomirApiService;
import com.magic.momir.services.MomirService;
import com.magic.momir.utils.EndpointUtil;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

@Module(
        injects = {
                MainActivity.class,
                MomirService.class
        },
        library = true,
        complete = false
)

public class RestClient {
    @Provides
    @Singleton
    MomirApiService provideRestClient() {
        final RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(EndpointUtil.ROOT).setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL);
        final RestAdapter adapter = builder.build();
        return adapter.create(MomirApiService.class);
    }
}
