package com.magic.momir;

import android.app.Application;
import android.content.Context;

import com.magic.momir.dagger.Injectable;
import com.magic.momir.dagger.OttoModule;
import com.magic.momir.dagger.PicassoModule;
import com.magic.momir.dagger.RestClient;
import com.magic.momir.services.MomirService;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import dagger.ObjectGraph;

public class MomirApplication extends Application implements Injectable {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "momir";

    @Inject protected Bus mBus;
    private ObjectGraph mGraph;

    public static Injectable getInjectable(final Context context) {
        return (Injectable) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createGraph();
        inject(this);
        mBus.register(new MomirService(this));
    }

    protected void createGraph() {
        mGraph = ObjectGraph.create(getModules());
    }

    public Object[] getModules() {
        final Object[] modules = new Object[3];
        modules[0] = new RestClient();
        modules[1] = new OttoModule();
        modules[2] = new PicassoModule(this);
        return modules;
    }

    public void inject(Object obj) {
        mGraph.inject(obj);
    }
}