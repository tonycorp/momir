package com.magic.momir.dagger;

import com.magic.momir.activities.MainActivity;
import com.magic.momir.activities.MainActivityTest;
import com.magic.momir.activities.MomirActivity;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module(
        injects = {
                MainActivity.class,
                MomirActivity.class,
                MainActivityTest.class
        },
        library = true,
        complete = false
)

public class PicassoModuleMock {
    @Provides
    @Singleton
    Picasso providePicasso() {
        return mock(Picasso.class);
    }
}
