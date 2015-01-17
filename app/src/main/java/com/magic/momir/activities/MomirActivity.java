package com.magic.momir.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.magic.momir.MomirApplication;
import com.magic.momir.R;
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
