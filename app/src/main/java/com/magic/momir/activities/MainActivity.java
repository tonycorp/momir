package com.magic.momir.activities;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.magic.momir.R;
import com.magic.momir.services.MomirService.ChooseCardEvent;
import com.magic.momir.services.MomirService.CardChosenEvent;
import com.magic.momir.utils.EndpointUtil;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends MomirActivity {
    @InjectView(R.id.activity_main_image) protected ImageView mCardImage;
    @InjectView(R.id.activity_main_cmc) protected EditText mCmc;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Subscribe
    public void onCardsLoaded(final CardChosenEvent event){
        final Integer multiverseId = event.getCard().getMultiverseId();
        final String imageUrl = EndpointUtil.getImageEndpoint(String.valueOf(multiverseId));
        Picasso.with(this).load(imageUrl).into(mCardImage);
    }

    @OnClick(R.id.activity_main_button)
    public void getData(){
        final String cmc = mCmc.getText().toString();
        mBus.post(new ChooseCardEvent(cmc));
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mCmc.getWindowToken(), 0);
    }
}
