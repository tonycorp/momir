package com.magic.momir.dagger;

import com.magic.momir.activities.MainActivity;
import com.magic.momir.rest.MomirApiService;
import com.magic.momir.services.MomirService;
import com.magic.momir.services.MomirServiceTest;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                MainActivity.class,
                MomirService.class,
                MomirServiceTest.class
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
