package com.biodata.biodatamaker.Utils;

import android.app.Activity;
import android.app.Application;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.biodata.biodatamaker.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class App extends Application {
    AdView adView;
    InterstitialAd mInterstitialAd;
    private com.facebook.ads.AdView adView_fb;
    private com.facebook.ads.InterstitialAd interstitialAd_fb;
    AdRequest adRequest;
    Activity activity;

    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(getResources().getString(R.string.banner_google));
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

       /* mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.fullscreen_google));
        mInterstitialAd.loadAd(adRequest);*/
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                getApplicationContext(),
                getResources().getString(R.string.fullscreen_google),
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        //Log.i("TAG", "onAdLoaded");
                        //Toast.makeText(getApplicationContext(), "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        mInterstitialAd = null;
                                        // Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        mInterstitialAd = null;
                                        // Log.d("TAG", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        // Log.d("TAG", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        //Log.i("TAG", loadAdError.getMessage());
                        mInterstitialAd = null;

                        String error =
                                String.format(
                                        "domain: %s, code: %d, message: %s",
                                        loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());
                        //Toast.makeText(getApplicationContext(), "onAdFailedToLoad() with error: " + error, Toast.LENGTH_SHORT).show();
                    }
                });


        adView_fb = new com.facebook.ads.AdView(this, getResources().getString(R.string.banner_fb), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        // AdSettings.addTestDevice("20F8998463B0B709CD4D98F39174D2F6");
        adView_fb.loadAd();

        interstitialAd_fb = new com.facebook.ads.InterstitialAd(this, getResources().getString(R.string.fullscreen_fb));
        interstitialAd_fb.loadAd();
    }

    public void showInterstitialAdGoogle() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(activity);
        } else {
            interstitialAd_fb.loadAd();
            if (interstitialAd_fb.isAdLoaded()) {
                interstitialAd_fb.show();
            }
        }
    }

    public void showInterstitialAdFb() {
        interstitialAd_fb.loadAd();
        if (interstitialAd_fb.isAdLoaded()) {
            interstitialAd_fb.show();
        } else {
            if (mInterstitialAd != null) {
                mInterstitialAd.show(activity);
            }
        }
    }

    public void loadAd(LinearLayout layAd) {
        if (adView.getParent() != null) {
            ViewGroup tempVg = (ViewGroup) adView.getParent();
            tempVg.removeView(adView);
        }
        layAd.addView(adView);
    }

    public void loadAdFb(LinearLayout layAdFb) {
        if (adView_fb.getParent() != null) {
            ViewGroup tempVg = (ViewGroup) adView_fb.getParent();
            tempVg.removeView(adView_fb);
        }
        layAdFb.addView(adView_fb);
        interstitialAd_fb.loadAd();
    }
}
