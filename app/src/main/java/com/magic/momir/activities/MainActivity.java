package com.magic.momir.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.magic.momir.R;
import com.magic.momir.models.Card;
import com.magic.momir.rest.MomirApiService;
import com.magic.momir.utils.EndpointUtil;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends MomirActivity {

    @Inject
    protected MomirApiService mApi;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = (EditText) findViewById(R.id.activity_main_cmc);
        mImageView = (ImageView) findViewById(R.id.activity_main_image);
        final Button button = (Button) findViewById(R.id.activity_main_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmc = editText.getText().toString();
                mApi.getCards(EndpointUtil.getCardQuery(cmc), new Callback<Card[]>() {
                    @Override
                    public void success(Card[] response, Response response2) {
                        Log.d("TEST", "LENGTH = " + response.length);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("TEST", "ERROR: " + error.getLocalizedMessage());
                    }
                });
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
