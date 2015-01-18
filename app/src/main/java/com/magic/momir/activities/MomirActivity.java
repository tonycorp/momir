package com.magic.momir.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.magic.momir.MomirApplication;
import com.magic.momir.R;
import com.magic.momir.utils.SharedPrefUtil;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class MomirActivity extends Activity {
    @Inject
    protected Bus mBus;

    public abstract int getContentViewId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MomirApplication.getInjectable(this).inject(this);
        setContentView(getContentViewId());
        ButterKnife.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBus.unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem item = menu.findItem(R.id.action_images_allowed);
        final boolean currentState = SharedPrefUtil.imagesEnabled(this);
        item.setTitle(currentState ? R.string.images_enabled : R.string.images_disabled);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_images_allowed) {
            final boolean currentState = SharedPrefUtil.toggleImages(this);
            item.setTitle(currentState ? R.string.images_enabled : R.string.images_disabled);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
