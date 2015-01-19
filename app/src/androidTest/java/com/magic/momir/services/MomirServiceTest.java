package com.magic.momir.services;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;

import com.magic.momir.MomirTestApplication;
import com.magic.momir.MomirTestRunner;
import com.magic.momir.datasets.CardTable;
import com.magic.momir.models.Card;
import com.magic.momir.providers.MomirContentProvider;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MomirTestRunner.class)
public class MomirServiceTest {
    @Inject protected MomirApiService mApiService;
    @Inject protected Bus mBus;
    private Callback<Card[]> mCallback;
    private ContentResolver mResolver;
    private MomirService mService;

    @Before
    public void setup(){
        MomirTestApplication.getInstance(Robolectric.application).inject(this);
        final Application application = spy(Robolectric.application);
        mResolver = mock(ContentResolver.class);
        when(application.getContentResolver()).thenReturn(mResolver);
        mService = new MomirService(application);
    }

    protected void getCallback() {
        final ArgumentCaptor<Callback> captor = ArgumentCaptor.forClass(Callback.class);
        mService.onChooseCard(new MomirService.ChooseCardEvent("5"));
        verify(mApiService).getCard(eq("5"), captor.capture());
        mCallback = (Callback<Card[]>) captor.getValue();
    }

    @Test
    public void onEmptyCardResponse_shouldSendErrorToastSayingNoCreatures(){
        getCallback();
        mCallback.success(new Card[]{}, null);
        final ArgumentCaptor<MomirService.ApiErrorEvent> captor = ArgumentCaptor.forClass(MomirService.ApiErrorEvent.class);
        verify(mBus).post(captor.capture());
        final MomirService.ApiErrorEvent error = captor.getValue();
        assertThat(error.getMessage()).isEqualToIgnoringCase("No Creature With CMC 5");
    }

    @Test
    public void onProperCardResponse_sendsCardChosenEventOut(){
        getCallback();
        mCallback.success(new Card[]{new Card()}, null);
        final ArgumentCaptor<MomirService.CardChosenEvent> captor = ArgumentCaptor.forClass(MomirService.CardChosenEvent.class);
        verify(mBus).post(captor.capture());
        assertThat(captor.getValue()).isInstanceOf(MomirService.CardChosenEvent.class);
    }

    @Test
    public void onNetworkError_sendsOutErrorWithMessage(){
        getCallback();
        mCallback.failure(RetrofitError.unexpectedError("testUrl", new Exception("testException")));
        final ArgumentCaptor<MomirService.ApiErrorEvent> captor = ArgumentCaptor.forClass(MomirService.ApiErrorEvent.class);
        verify(mBus).post(captor.capture());
        final MomirService.ApiErrorEvent error = captor.getValue();
        assertThat(error.getMessage()).isEqualToIgnoringCase("testException");
    }

    @Test
    public void onAddToBoard_addsToCorrectBoard(){
        final Card card = new Card();
        card.setBoard(0);
        mService.addCardToBoard(new MomirService.AddCardEvent(card));
        final ArgumentCaptor<ContentValues> captor = ArgumentCaptor.forClass(ContentValues.class);
        verify(mResolver).insert(eq(MomirContentProvider.Uris.CARD_URI), captor.capture());
        final ContentValues value = captor.getValue();
        final Integer board = value.getAsInteger(CardTable.Columns.BOARD);
        assertThat(board).isEqualTo(0);
    }
}