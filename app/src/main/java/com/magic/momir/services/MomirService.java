package com.magic.momir.services;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.magic.momir.MomirApplication;
import com.magic.momir.R;
import com.magic.momir.datasets.CardTable;
import com.magic.momir.models.Card;
import com.magic.momir.providers.MomirContentProvider;
import com.magic.momir.rest.MomirApiService;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MomirService {
    @Inject protected MomirApiService mApi;
    @Inject protected Bus mBus;
    private final Context mContext;

    public MomirService(final Context context){
        mContext = context;
        MomirApplication.getInjectable(context).inject(this);
    }

    @Subscribe
    public void onChooseCard(final ChooseCardEvent event){
        mApi.getCard(event.getCmc(), new Callback<Card[]>() {
            @Override
            public void success(Card[] response, Response response2) {
                if (response.length == 0) {
                    mBus.post(new ApiErrorEvent(mContext.getString(R.string.no_creature) + event.getCmc()));
                } else {
                    mBus.post(new CardChosenEvent(response[0]));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                mBus.post(new ApiErrorEvent(error.getMessage()));
            }
        });
    }

    @Subscribe
    public void addCardToBoard(final AddCardEvent event) {
        final ContentResolver resolver = mContext.getContentResolver();
        final ContentValues contentValues = CardTable.getContentValues(event.getCard());
        resolver.insert(MomirContentProvider.Uris.CARD_URI, contentValues);
    }

    public static class ChooseCardEvent {
        private final String mCmc;

        public ChooseCardEvent(final String cmc){
            mCmc = cmc;
        }

        public String getCmc(){
            return mCmc;
        }
    }

    public static class CardChosenEvent {
        private final Card mCard;

        public CardChosenEvent(final Card card){
            mCard = card;
        }

        public Card getCard(){
            return mCard;
        }
    }

    public static class ApiErrorEvent {
        private final String mMessage;

        public ApiErrorEvent(final String message) {
            mMessage = message;
        }

        public String getMessage(){
            return mMessage;
        }
    }

    public static class AddCardEvent {
        private final Card mCard;

        public AddCardEvent(final Card card) {
            mCard = card;
        }

        public Card getCard(){
            return mCard;
        }
    }
}
