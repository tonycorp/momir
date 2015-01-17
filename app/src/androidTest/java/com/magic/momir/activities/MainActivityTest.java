package com.magic.momir.activities;

import android.view.View;
import android.widget.EditText;

import com.magic.momir.MomirTestApplication;
import com.magic.momir.MomirTestRunner;
import com.magic.momir.R;
import com.magic.momir.rest.MomirApiService;
import com.magic.momir.services.MomirService.ChooseCardEvent;
import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MomirTestRunner.class)
public class MainActivityTest {

    @Inject protected MomirApiService mService;
    @Inject protected Bus mBus;

    @Before
    public void setup() {
        MomirTestApplication.getInstance(Robolectric.application).inject(this);
    }

    @Test
    public void onSubmitClick_sendsCorrectEvent() {
        final ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class);
        final MainActivity activity = controller.create().start().get();

        final View button = activity.findViewById(R.id.activity_main_button);
        final EditText cmc = (EditText) activity.findViewById(R.id.activity_main_cmc);
        cmc.setText("6");
        button.performClick();

        final ArgumentCaptor<ChooseCardEvent> captor = ArgumentCaptor.forClass(ChooseCardEvent.class);
        verify(mBus).post(captor.capture());
        final ChooseCardEvent event = captor.getValue();
        assertThat(event.getCmc()).isEqualTo("6");
    }
}
