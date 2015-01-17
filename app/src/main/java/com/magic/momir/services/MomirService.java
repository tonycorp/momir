package com.magic.momir.services;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import com.magic.momir.MomirApplication;
import com.magic.momir.datasets.CardTable;
import com.magic.momir.models.Card;
import com.magic.momir.providers.MomirContentProvider;
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
    private final Context mContext;
    @Inject protected MomirApiService mApi;
    @Inject protected Bus mBus;

    public MomirService(final Context context){
        mContext = context;
        MomirApplication.getInjectable(context).inject(this);
    }

    @Subscribe
    public void onChooseCard(final ChooseCardEvent event){
        final Random random = new Random();
        final ContentResolver contentResolver = mContext.getContentResolver();
        final Cursor query = contentResolver.query(MomirContentProvider.Uris.CARD_URI, null, CardTable.Columns.CMC + "=?", new String[]{event.getCmc()}, null);
        if (query.getCount() > 0) {
            final int i = random.nextInt(query.getCount());
            query.moveToPosition(i);
            final Card card = CardTable.fromCursor(query);
            query.close();
            mBus.post(new CardChosenEvent(card));
        } else {
            mApi.getCards(EndpointUtil.getCardQuery(event.getCmc()), new Callback<Card[]>() {
                @Override
                public void success(Card[] response, Response response2) {
                    if (response.length == 0) {
                        mBus.post(new ApiErrorEvent("No Creature With CMC " + event.getCmc()));
                    } else {
                        contentResolver.bulkInsert(MomirContentProvider.Uris.CARD_URI, CardTable.getContentValues(response));
                        final int i = random.nextInt(response.length);
                        mBus.post(new CardChosenEvent(response[i]));
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    mBus.post(new ApiErrorEvent(error.getMessage()));
                }
            });
        }
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
}
