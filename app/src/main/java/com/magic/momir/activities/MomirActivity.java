package com.magic.momir.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.magic.momir.MomirApplication;

public class MomirActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MomirApplication.getInjectable(this).inject(this);
    }
}
