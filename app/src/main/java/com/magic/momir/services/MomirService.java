package com.magic.momir.services;

import android.content.Context;

import com.magic.momir.MomirApplication;
import com.magic.momir.models.Card;
import com.magic.momir.rest.MomirApiService;
import com.magic.momir.utils.EndpointUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Random;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MomirService {
    @Inject protected MomirApiService mApi;
    @Inject protected Bus mBus;

    public MomirService(final Context context){
        MomirApplication.getInjectable(context).inject(this);
    }

    @Subscribe
    public void onChooseCard(final ChooseCardEvent event){
        mApi.getCards(EndpointUtil.getCardQuery(event.getCmc()), new Callback<Card[]>() {
            @Override
            public void success(Card[] response, Response response2) {
                final Random random = new Random();
                final int i = random.nextInt(response.length);
                mBus.post(new CardChosenEvent(response[i]));
            }

            @Override
            public void failure(RetrofitError error) {
                mBus.post(new ApiErrorEvent(error.getMessage()));
            }
        });
    }

    public static class ChooseCardEvent{
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
}
