package com.vartyr.dfpimabandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

public class InterstitialActivity extends Activity {

    private PublisherInterstitialAd mPublisherInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);
        configureInterstitial();

    }



    public void buttonPreloadInterstitial(View view) {
        preloadInterstitial();
    }

    public void buttonShowInterstitial(View view) {
        showInterstitial();
    }

    public void configureInterstitial() {

        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId("/6499/example/interstitial");

        mPublisherInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Toast.makeText(InterstitialActivity.this,"Interstitial preload ready", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

                Toast.makeText(InterstitialActivity.this,"Interstitial failed", Toast.LENGTH_LONG).show();

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
                mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());

            }
        });

    }

    public void preloadInterstitial() {

        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());

    }

    public void showInterstitial() {

        if (mPublisherInterstitialAd.isLoaded()) {
            mPublisherInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }


}
