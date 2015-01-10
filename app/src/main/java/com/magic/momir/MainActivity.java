package com.magic.momir;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


public class MainActivity extends ActionBarActivity {

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
                new MomirTask().execute(cmc);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
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

    private class MomirTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... cmc){
            HttpURLConnection urlConnection = null;
            try {
                final URL url = new URL(NetworkUtils.getCardEndpoint(cmc[0]));
                urlConnection = (HttpURLConnection) url.openConnection();
                final InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                final Card[] cards = new Gson().fromJson(new InputStreamReader(in), Card[].class);
                final Random random = new Random();
                final int i = random.nextInt(cards.length);
                return NetworkUtils.getImageEndpoint(cards[i].mMultiverseId);
            } catch(Exception exc){
                exc.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String image) {
            Picasso.with(MainActivity.this).load(image).into(mImageView);
        }
    }
}
