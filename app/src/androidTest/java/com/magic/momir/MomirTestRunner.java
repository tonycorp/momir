package com.magic.momir;

import android.app.Application;

import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.DefaultTestLifecycle;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.TestLifecycle;
import org.robolectric.annotation.Config;
import org.robolectric.res.Fs;
import org.robolectric.res.FsFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class MomirTestRunner extends RobolectricTestRunner {

    public MomirTestRunner(final Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected Class<? extends TestLifecycle> getTestLifecycleClass() {
        return MyTestLifecycle.class;
    }

    public static class MyTestLifecycle extends DefaultTestLifecycle {
        @Override
        public Application createApplication(Method method, AndroidManifest appManifest, Config config) {
            return new MomirTestApplication();
        }
    }

    @Override
    protected AndroidManifest createAppManifest(FsFile manifestFile, FsFile resDir, FsFile assetsDir) {
        String file = "AndroidManifest.xml";
        try {
            final String current = new File(".").getCanonicalPath();
            if (!current.endsWith("app")) {
                file = "app/" + file;
            }
        } catch (IOException exc){
            exc.printStackTrace();
        }
        FsFile fsFile = Fs.newFile(new File("."));
        FsFile customManifest = fsFile.join(file);
        FsFile appBaseDir = customManifest.getParent();
        return new AndroidManifest(customManifest, appBaseDir.join("res"), appBaseDir.join("assets"));
    }
}
