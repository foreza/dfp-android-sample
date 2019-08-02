package com.vartyr.dfpimabandroid;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.inmobi.plugin.dfp.IMAudienceBidder;

public class InterstitalIMABKWActivity extends Activity {

    private PublisherInterstitialAd mPublisherIMABInterstitialAd;
    private Boolean interstitialReady = false;
    private IMAudienceBidder.BidToken interstitialBidToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstital_imab);

        configureIMABInterstitial();

    }


    // Touch events

    public void buttonPreloadIMABInterstitial(View view) {
        preloadIMABInterstitial();
    }

    public void buttonShowIMABInterstitial(View view) {
        showIMABInterstitial();
    }

    public void configureIMABInterstitial() {

        mPublisherIMABInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherIMABInterstitialAd.setAdUnitId("/82109981/ca-mb-app-pub-4731346788446294-tag/ABIM1");

        mPublisherIMABInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Toast.makeText(InterstitalIMABKWActivity.this,"IMAB Interstitial preload ready", Toast.LENGTH_LONG).show();
                interstitialReady = true;
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

                Toast.makeText(InterstitalIMABKWActivity.this,"Interstitial failed", Toast.LENGTH_LONG).show();

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

                // Make a new request on close!
                preloadIMABInterstitial();

            }
        });

    }





    public void preloadIMABInterstitial() {

        PublisherAdRequest.Builder IMABadRequest = new PublisherAdRequest.Builder().addTestDevice("DBA220EA435766BFB9DFB4CC7B7673A8");

        // Get singleton instance
        IMAudienceBidder inMobiAudienceBidder = IMAudienceBidder.getInstance();

        interstitialBidToken = inMobiAudienceBidder.createBidToken(this, "1064877", IMABadRequest, new IMAudienceBidder.IMAudienceBidderInterstitialListener() {
            @Override
            public void onBidReceived(@NonNull PublisherAdRequest.Builder builder) {

                Toast.makeText(InterstitalIMABKWActivity.this,"IMAb Interstitial onBidReceived", Toast.LENGTH_LONG).show();

                PublisherAdRequest publisherAdRequest = builder.build();

                mPublisherIMABInterstitialAd.loadAd(publisherAdRequest);
            }

            @Override
            public void onBidFailed(@NonNull PublisherAdRequest.Builder builder, @NonNull Error error) {

                Toast.makeText(InterstitalIMABKWActivity.this,"IMAb Banner onBidFailed", Toast.LENGTH_LONG).show();

                mPublisherIMABInterstitialAd.loadAd(builder.build());

            }
        });

        interstitialBidToken.updateBid();

    }

    public void showIMABInterstitial() {

        if (mPublisherIMABInterstitialAd.isLoaded() && interstitialReady) {
            mPublisherIMABInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }


}
