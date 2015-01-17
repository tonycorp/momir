package com.magic.momir.dagger;

import com.magic.momir.activities.MainActivity;
import com.magic.momir.activities.MainActivityTest;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module(
        injects = {
                MainActivity.class,
                MainActivityTest.class
        },
        library = true,
        complete = false
)

public class OttoModuleMock {
    @Provides
    @Singleton
    Bus provideOttoBus() {
        return mock(Bus.class);
    }
}
