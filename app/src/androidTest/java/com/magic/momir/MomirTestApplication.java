package com.magic.momir;

import android.content.Context;

import com.magic.momir.dagger.Injectable;
import com.magic.momir.dagger.OttoModuleMock;
import com.magic.momir.dagger.RestClientMock;

import dagger.ObjectGraph;

public class MomirTestApplication extends MomirApplication implements Injectable {
    private ObjectGraph mGraph;

    public static Injectable getInstance(final Context context) {
        return (Injectable) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mGraph = ObjectGraph.create(getModules());
    }

    public Object[] getModules() {
        final Object[] modules = new Object[2];
        modules[0] = new RestClientMock();
        modules[1] = new OttoModuleMock();
        return modules;
    }

    public void inject(Object obj) {
        mGraph.inject(obj);
    }
}
