package com.vartyr.dfpimabandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import com.inmobi.ads.InMobiAudienceBidder;
import com.inmobi.plugin.dfp.IMABCustomEventBanner;
import com.inmobi.plugin.dfp.IMAudienceBidder;

public class MainActivity extends Activity {

    private PublisherAdView mPublisherAdView;                       // Keep a reference to the adView
    private Boolean bannerLoaded = false;                           // for banner refresh


    private IMAudienceBidder.BidToken bannerBidToken;

    private PublisherAdRequest.Builder IMAKWBadRequest;               // Used primarily by KW route




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initSDK();
        // configureAndLoadAdView();

//         imabBannerRequest();

    }






    public void initSDK() {
        MobileAds.initialize(this);                             // Init DFP SDK
        InMobiAudienceBidder.initialize(this, "1017084");   // Init IMAB SDK
    }



    public void imabBannerRequest() {

        mPublisherAdView = findViewById(R.id.publisherAdView);
        PublisherAdRequest.Builder IMABadRequest = new PublisherAdRequest.Builder();
        IMABadRequest.addTestDevice("DBA220EA435766BFB9DFB4CC7B7673A8");                    // Test Device ID

        IMAudienceBidder inMobiAudienceBidder = IMAudienceBidder.getInstance();             // Get singleton instance

        bannerBidToken = inMobiAudienceBidder.createBidToken(this, "1055520", IMABadRequest, 320, 50, new IMAudienceBidder.IMAudienceBidderBannerListener() {
            @Override
            public void onBidReceived(@NonNull  PublisherAdRequest.Builder builder) {

                Toast.makeText(MainActivity.this,"IMAb Banner onBidReceived", Toast.LENGTH_LONG).show();


                if (!bannerLoaded) {

                    Toast.makeText(MainActivity.this,"IMAb Banner not yet loaded, loading into view", Toast.LENGTH_LONG).show();

                    PublisherAdRequest publisherAdRequest = builder.build();

                    mPublisherAdView.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            // Code to be executed when an ad finishes loading.

                            Toast.makeText(MainActivity.this,"IMAb Banner onAdLoaded", Toast.LENGTH_LONG).show();


                        }

                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            // Code to be executed when an ad request fails.

                            Toast.makeText(MainActivity.this,"IMAb Banner onAdFailedToLoad with error: " + errorCode, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onAdOpened() { }

                        @Override
                        public void onAdClicked() {
                            // Code to be executed when the user clicks on an ad.

                            Toast.makeText(MainActivity.this,"IMAb Banner onAdClicked", Toast.LENGTH_LONG).show();


                        }

                        @Override
                        public void onAdLeftApplication() { }

                        @Override
                        public void onAdClosed() { }
                    });

                    mPublisherAdView.loadAd(publisherAdRequest);

                }

            }

            @Override
            public void onBidFailed(PublisherAdRequest.Builder builder, Error error) {

                Toast.makeText(MainActivity.this,"IMAb Banner onBidFailed", Toast.LENGTH_LONG).show();

                mPublisherAdView.loadAd(builder.build());



            }
        });

        bannerBidToken.updateBid();

    }


    public void imabBannerKWRequest() {

        mPublisherAdView = findViewById(R.id.publisherAdView);
        IMAKWBadRequest = new PublisherAdRequest.Builder();

        IMAudienceBidder inMobiAudienceBidder = IMAudienceBidder.getInstance();             // Get singleton instance


        bannerBidToken = inMobiAudienceBidder.createBidToken(this, "1055520", 320, 50, new IMAudienceBidder.IMAudienceBidderBannerKeywordListener() {
            @Override
            public void onBidReceived(IMAudienceBidder.IMABBidResponse imabBidResponse) {

                // Get other bid information here
                String bidKeyword = imabBidResponse.getBidKeyword();
                String bidPrice = String.valueOf(imabBidResponse.getPrice());

                Toast.makeText(MainActivity.this, "IMAb Banner KW onBidReceived with kw: " + bidKeyword + " and: " + bidPrice, Toast.LENGTH_LONG).show();

                Bundle metadata = new Bundle();
                metadata.putString(IMAudienceBidder.IMAB_PLACEMENT, "1055520");
                IMAKWBadRequest.addCustomEventExtrasBundle(IMABCustomEventBanner.class, metadata);

                IMAKWBadRequest.addTestDevice("DBA220EA435766BFB9DFB4CC7B7673A8");                    // Test Device ID
                IMAKWBadRequest.addCustomTargeting(IMAudienceBidder.IMAB_KEY, imabBidResponse.getBidKeyword());


                if (!bannerLoaded) {
                    Toast.makeText(MainActivity.this, "IMAb KW Banner not yet loaded, loading into view", Toast.LENGTH_LONG).show();

                    mPublisherAdView.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            // Code to be executed when an ad finishes loading.

                            Toast.makeText(MainActivity.this,"IMAb KW Banner onAdLoaded", Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            // Code to be executed when an ad request fails.

                            Toast.makeText(MainActivity.this,"IMAb KW Banner onAdFailedToLoad with error: " + errorCode, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onAdOpened() { }

                        @Override
                        public void onAdClicked() {
                            // Code to be executed when the user clicks on an ad.

                            Toast.makeText(MainActivity.this,"IMAb KW Banner onAdClicked", Toast.LENGTH_LONG).show();


                        }

                        @Override
                        public void onAdLeftApplication() { }

                        @Override
                        public void onAdClosed() { }
                    });

                    mPublisherAdView.loadAd(IMAKWBadRequest.build());
                }

            }

            @Override
            public void onBidFailed(Error error) {
                IMAKWBadRequest.addTestDevice("DBA220EA435766BFB9DFB4CC7B7673A8");                    // Test Device ID
                mPublisherAdView.loadAd(IMAKWBadRequest.build());
            }
        });

        bannerBidToken.updateBid();

    }




    public void configureAndLoadAdView(){

        mPublisherAdView = findViewById(R.id.publisherAdView);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder()
                .addTestDevice("DBA220EA435766BFB9DFB4CC7B7673A8")
                .build();
        mPublisherAdView.loadAd(adRequest);

        // Attach Listeners
        mPublisherAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
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
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

    }




    // Example on how to load the DFP banner right into the view (uses XML)
    public void loadDFPBanner(View view){

//        mPublisherAdView.setAdUnitId("/6499/example/banner");

        if (mPublisherAdView != null) {
            mPublisherAdView.destroy();
        }
        configureAndLoadAdView();
    }

    public void loadDFPIMABBanner(View view){
        if (mPublisherAdView != null) {
            mPublisherAdView.destroy();
        }

        imabBannerRequest();
    }


    public void loadDFPIMABKWBanner(View view){
        if (mPublisherAdView != null) {
            mPublisherAdView.destroy();
        }
        imabBannerKWRequest();
    }



    public void nav_Interstitial(View view){
        startActivity(new Intent(this, InterstitialActivity.class));
    }


    public void nav_IMABInterstitial(View view){
        startActivity(new Intent(this, InterstitalIMABActivity.class));
    }

    public void nav_IMABKWInterstitial(View view){
        startActivity(new Intent(this, InterstitalIMABKWActivity.class));
    }



}
