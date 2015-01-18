package com.magic.momir.dagger;

import android.content.Context;

import com.magic.momir.activities.MainActivity;
import com.magic.momir.activities.MomirActivity;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                MainActivity.class,
                MomirActivity.class
        },
        library = true,
        complete = false
)

public class PicassoModule {
    private final Context mContext;

    public PicassoModule(final Context context){
        mContext = context;
    }
    @Provides
    @Singleton
    Picasso providePicasso() {
        return Picasso.with(mContext);
    }
}
