package com.magic.momir.activities;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.magic.momir.R;
import com.magic.momir.models.Card;
import com.magic.momir.services.MomirService;
import com.magic.momir.services.MomirService.CardChosenEvent;
import com.magic.momir.services.MomirService.ChooseCardEvent;
import com.magic.momir.utils.EndpointUtil;
import com.magic.momir.utils.SharedPrefUtil;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends MomirActivity {
    @InjectView(R.id.activity_main_image) protected ImageView mCardImage;
    @InjectView(R.id.activity_main_cmc) protected EditText mCmc;
    @InjectView(R.id.activity_main_no_images) protected LinearLayout mNoImage;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Subscribe
    public void onCardsLoaded(final CardChosenEvent event){
        if (SharedPrefUtil.imagesEnabled(this)) {
            final Integer multiverseId = event.getCard().getMultiverseId();
            final String imageUrl = EndpointUtil.getImageEndpoint(String.valueOf(multiverseId));
            Picasso.with(this).load(imageUrl).into(mCardImage);
            mNoImage.setVisibility(View.GONE);
            mCardImage.setVisibility(View.VISIBLE);
        } else {
            populateCardData(event.getCard());
            mCardImage.setVisibility(View.GONE);
            mNoImage.setVisibility(View.VISIBLE);
        }
    }

    protected void populateCardData(final Card card) {
        ((TextView)mNoImage.findViewById(R.id.activity_card_name)).setText(card.getName());
        ((TextView)mNoImage.findViewById(R.id.activity_card_manacost)).setText(card.getManacost());
        ((TextView)mNoImage.findViewById(R.id.activity_card_description)).setText(card.getDescription());
        ((TextView)mNoImage.findViewById(R.id.activity_card_type_subtype)).setText(card.getType() + " - " + card.getSubType());
        ((TextView)mNoImage.findViewById(R.id.activity_card_power_toughness)).setText(card.getPower() + "/" + card.getToughness());
    }

    @Subscribe
    public void onApiError(final MomirService.ApiErrorEvent event) {
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.activity_main_button)
    public void getData(){
        final String cmc = mCmc.getText().toString();
        mBus.post(new ChooseCardEvent(cmc));
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mCmc.getWindowToken(), 0);
    }
}
