package com.magic.momir.dagger;

import java.util.List;

public interface Injectable {
    public List<Object> getModules();

    public void inject(final Object object);
}
