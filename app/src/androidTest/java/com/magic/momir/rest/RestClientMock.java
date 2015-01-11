package com.magic.momir.rest;

import com.magic.momir.activities.MainActivity;
import com.magic.momir.activities.MainActivityTest;
import com.magic.momir.utils.EndpointUtil;

import org.mockito.Mock;
import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module(
        injects = {
                MainActivity.class,
                MainActivityTest.class
        },
        overrides = true,
        library = true,
        complete = false
)
public class RestClientMock {
    @Provides
    @Singleton
    MomirApiService provideRestClient() {
        return Mockito.mock(MomirApiService.class);//new RestAdapter.Builder().setEndpoint(EndpointUtil.ROOT).setClient(new RetrofitMockClient()).build().create(MomirApiService.class);
    }
}
