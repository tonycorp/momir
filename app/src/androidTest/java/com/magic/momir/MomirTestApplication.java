package com.magic.momir;

import android.content.Context;

import com.magic.momir.dagger.Injectable;
import com.magic.momir.dagger.OttoModuleMock;
import com.magic.momir.dagger.PicassoModuleMock;
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
        createGraph();
    }

    @Override
    protected void createGraph() {
        mGraph = ObjectGraph.create(getModules());
    }

    public Object[] getModules() {
        final Object[] modules = new Object[3];
        modules[0] = new RestClientMock();
        modules[1] = new OttoModuleMock();
        modules[2] = new PicassoModuleMock();
        return modules;
    }

    public void inject(Object obj) {
        mGraph.inject(obj);
    }
}
