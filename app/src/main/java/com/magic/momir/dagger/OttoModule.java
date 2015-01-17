package com.magic.momir.dagger;

import com.magic.momir.MomirApplication;
import com.magic.momir.activities.MainActivity;
import com.magic.momir.activities.MomirActivity;
import com.magic.momir.services.MomirService;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                MomirActivity.class,
                MainActivity.class,
                MomirService.class,
                MomirApplication.class
        },
        library = true,
        complete = false
)

public class OttoModule {
    @Provides
    @Singleton
    Bus provideOttoBus() {
        return new Bus();
    }
}
