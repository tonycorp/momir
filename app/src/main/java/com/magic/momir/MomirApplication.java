package com.magic.momir;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.google.common.eventbus.Subscribe;
import com.magic.momir.dagger.Injectable;
import com.magic.momir.dagger.OttoModule;
import com.magic.momir.dagger.RestClient;
import com.magic.momir.services.MomirService;
import com.magic.momir.services.MomirService.ApiErrorEvent;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import dagger.ObjectGraph;

public class MomirApplication extends Application implements Injectable {
    @Inject Bus mBus;

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
        mBus.register(this);
    }

    protected void createGraph() {
        mGraph = ObjectGraph.create(getModules());
    }

    public Object[] getModules() {
        final Object[] modules = new Object[2];
        modules[0] = new RestClient();
        modules[1] = new OttoModule();
        return modules;
    }

    @Subscribe
    public void onApiError(final ApiErrorEvent event) {
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void inject(Object obj) {
        mGraph.inject(obj);
    }
}