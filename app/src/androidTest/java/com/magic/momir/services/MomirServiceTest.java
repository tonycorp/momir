package com.magic.momir.services;

import com.magic.momir.MomirTestApplication;
import com.magic.momir.MomirTestRunner;
import com.magic.momir.models.Card;
import com.magic.momir.rest.MomirApiService;
import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.Robolectric;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MomirTestRunner.class)
public class MomirServiceTest {
    @Inject protected MomirApiService mApiService;
    @Inject protected Bus mBus;
    private Callback<Card[]> mCallback;

    @Before
    public void setup(){
        MomirTestApplication.getInstance(Robolectric.application).inject(this);
        final MomirService service = new MomirService(Robolectric.application);
        final ArgumentCaptor<Callback> captor = ArgumentCaptor.forClass(Callback.class);
        service.onChooseCard(new MomirService.ChooseCardEvent("5"));
        verify(mApiService).getCard(eq("5"), captor.capture());
        mCallback = (Callback<Card[]>) captor.getValue();
    }

    @Test
    public void onEmptyResponse_shouldSendErrorToastSayingNoCreatures(){
        mCallback.success(new Card[]{}, null);
        final ArgumentCaptor<MomirService.ApiErrorEvent> captor = ArgumentCaptor.forClass(MomirService.ApiErrorEvent.class);
        verify(mBus).post(captor.capture());
        final MomirService.ApiErrorEvent error = captor.getValue();
        assertThat(error.getMessage()).isEqualToIgnoringCase("No Creature With CMC 5");
    }

    @Test
    public void onProperResponse_sendsCardChosenEventOut(){
        mCallback.success(new Card[]{new Card()}, null);
        final ArgumentCaptor<MomirService.CardChosenEvent> captor = ArgumentCaptor.forClass(MomirService.CardChosenEvent.class);
        verify(mBus).post(captor.capture());
        assertThat(captor.getValue()).isInstanceOf(MomirService.CardChosenEvent.class);
    }

    @Test
    public void onNetworkError_sendsOutErrorWithMessage(){
        mCallback.failure(RetrofitError.unexpectedError("testUrl", new Exception("testException")));
        final ArgumentCaptor<MomirService.ApiErrorEvent> captor = ArgumentCaptor.forClass(MomirService.ApiErrorEvent.class);
        verify(mBus).post(captor.capture());
        final MomirService.ApiErrorEvent error = captor.getValue();
        assertThat(error.getMessage()).isEqualToIgnoringCase("testException");
    }
}