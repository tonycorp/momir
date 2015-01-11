package com.magic.momir;

import android.app.Application;
import android.content.Context;

import com.magic.momir.dagger.Injectable;
import com.magic.momir.rest.RestClient;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;

public class MomirApplication extends Application implements Injectable {
    private ObjectGraph mGraph;

    public static Injectable getInjectable(final Context context) {
        return (Injectable) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mGraph = ObjectGraph.create(new RestClient());//getModules());
    }

    public List<Object> getModules() {
        final List<Object> modules = new ArrayList<>();
        modules.add(new RestClient());
        return modules;
    }

    public void inject(Object obj) {
        mGraph.inject(obj);
    }
}