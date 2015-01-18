package com.magic.momir.activities;

import android.view.View;
import android.widget.EditText;

import com.magic.momir.MomirTestApplication;
import com.magic.momir.MomirTestRunner;
import com.magic.momir.R;
import com.magic.momir.models.Card;
import com.magic.momir.services.MomirService;
import com.magic.momir.services.MomirService.ChooseCardEvent;
import com.magic.momir.utils.SharedPrefUtil;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fest.assertions.api.ANDROID.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MomirTestRunner.class)
public class MainActivityTest {

    @Inject protected Bus mBus;
    @Inject protected Picasso mPicasso;
    private MainActivity mActivity;

    @Before
    public void setup() {
        MomirTestApplication.getInstance(Robolectric.application).inject(this);
        final ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class);
        mActivity = controller.create().start().get();
    }

    @Test
    public void onSubmitClick_sendsCorrectEvent() {
        final View button = mActivity.findViewById(R.id.activity_main_button);
        final EditText cmc = (EditText) mActivity.findViewById(R.id.activity_main_cmc);
        cmc.setText("6");
        button.performClick();

        final ArgumentCaptor<ChooseCardEvent> captor = ArgumentCaptor.forClass(ChooseCardEvent.class);
        verify(mBus).post(captor.capture());
        final ChooseCardEvent event = captor.getValue();
        assertThat(event.getCmc()).isEqualTo("6");
    }

    @Test
    public void onCardsLoadedWithImagesEnabled_shouldDisplayImage(){
        if (!SharedPrefUtil.imagesEnabled(Robolectric.application)){
            SharedPrefUtil.toggleImages(Robolectric.application);
        }
        final RequestCreator creator = mock(RequestCreator.class);
        when(mPicasso.load(eq("https://api.mtgdb.info/content/hi_res_card_images/15.jpg"))).thenReturn(creator);
        final Card card = new Card();
        card.setMultiverseId(15);
        mActivity.onCardsLoaded(new MomirService.CardChosenEvent(card));
        verify(creator).into(mActivity.mCardImage);
        assertThat(mActivity.mCardImage).isVisible();
        assertThat(mActivity.mNoImage).isGone();
    }

    @Test
    public void onCardsLoadedWithImagesDisabled_shouldDisplayText(){
        if (SharedPrefUtil.imagesEnabled(Robolectric.application)){
            SharedPrefUtil.toggleImages(Robolectric.application);
        }
        final Card card = new Card();
        card.setName("cardName");
        card.setManacost("manaCost");
        card.setType("type");
        card.setSubType("subtype");
        card.setDescription("description");
        card.setPower(1);
        card.setToughness(1);
        mActivity.onCardsLoaded(new MomirService.CardChosenEvent(card));
        assertThat(mActivity.mCardName).hasText("cardName");
        assertThat(mActivity.mManaCost).hasText("manaCost");
        assertThat(mActivity.mTypeSubtype).hasText("type - subtype");
        assertThat(mActivity.mDescription).hasText("description");
        assertThat(mActivity.mPowerToughness).hasText("1/1");
        assertThat(mActivity.mCardImage).isGone();
        assertThat(mActivity.mNoImage).isVisible();
        verifyZeroInteractions(mPicasso);
    }
}
