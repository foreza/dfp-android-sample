package com.vartyr.dfpimabandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

public class InterstitalIMABActivity extends Activity {

    private PublisherInterstitialAd mPublisherIMABInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstital_imab);
        configureIMABInterstitial();

    }


    public void buttonPreloadIMABInterstitial(View view) {
        preloadIMABInterstitial();
    }

    public void buttonShowIMABInterstitial(View view) {
        showIMABInterstitial();
    }

    public void configureIMABInterstitial() {

        mPublisherIMABInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherIMABInterstitialAd.setAdUnitId("/6499/example/interstitial");

        mPublisherIMABInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Toast.makeText(InterstitalIMABActivity.this,"Interstitial preload ready", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

                Toast.makeText(InterstitalIMABActivity.this,"Interstitial failed", Toast.LENGTH_LONG).show();

                // Code to be executed when an ad request fails.

//                ERROR_CODE_INTERNAL_ERROR - Something happened internally; for instance, an invalid response was received from the ad server.
//                ERROR_CODE_INVALID_REQUEST - The ad request was invalid; for instance, the ad unit ID was incorrect.
//                        ERROR_CODE_NETWORK_ERROR - The ad request was unsuccessful due to network connectivity.
//                ERROR_CODE_NO_FILL - The ad request was successful, but no ad was returned due to lack of ad inventory.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.

                // Make a new request on close
                mPublisherIMABInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());

            }
        });

    }

    public void preloadIMABInterstitial() {

        mPublisherIMABInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());

    }

    public void showIMABInterstitial() {

        if (mPublisherIMABInterstitialAd.isLoaded()) {
            mPublisherIMABInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }


}
