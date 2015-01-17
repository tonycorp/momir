package com.magic.momir.dagger;

public interface Injectable {
    public Object[] getModules();

    public void inject(final Object object);
}
