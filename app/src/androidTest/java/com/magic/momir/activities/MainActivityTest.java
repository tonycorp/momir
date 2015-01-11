package com.magic.momir.activities;

import android.view.View;

import com.magic.momir.MomirTestApplication;
import com.magic.momir.MomirTestRunner;
import com.magic.momir.R;
import com.magic.momir.rest.MomirApiService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import javax.inject.Inject;

import retrofit.Callback;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

@RunWith(MomirTestRunner.class)
public class MainActivityTest {

    @Inject
    protected MomirApiService mService;

    @Before
    public void setup() {
        MomirTestApplication.getInstance(Robolectric.application).inject(this);
    }

    @Test
    public void test() {
        final ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class);
        final MainActivity activity = controller.create().start().get();

        final View button = activity.findViewById(R.id.activity_main_button);
        button.performClick();

        Mockito.verify(mService).getCards(anyString(), any(Callback.class));
    }
}
