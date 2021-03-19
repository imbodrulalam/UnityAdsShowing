package com.alkhalildevelopers.mytestapp;

import androidx.appcompat.app.AppCompatActivity;
import com.unity3d.ads.UnityAds;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.IUnityBannerListener;
import com.unity3d.services.banners.UnityBanners;

public class MainActivity extends AppCompatActivity {
    private String GameID = "4055061";
    private boolean testMode = false;
    private String bannerAdPlacement = "Banner_Android";
    private String interstitialAdPlacement = "Interstitial_Android";
    private String rewardedAdPlacement = "Rewarded_Android";

    private Button showInterstitialBtn,showBannerBtn,showRewardedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UnityAds.initialize(MainActivity.this,GameID,testMode);

        IUnityAdsListener unityAdsListener = new IUnityAdsListener() {
            @Override
            public void onUnityAdsReady(String s) {
                Toast.makeText(MainActivity.this, "Ad Ready. Click on the Button!" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnityAdsStart(String s) {
                Toast.makeText(MainActivity.this, "Ad is Playing....", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
                Toast.makeText(MainActivity.this, "Ad is Finished. Please wait 5 second!" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {
                Toast.makeText(MainActivity.this, unityAdsError.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        UnityAds.setListener(unityAdsListener);

        if (UnityAds.isInitialized()){
            UnityAds.load(interstitialAdPlacement);
            UnityAds.load(rewardedAdPlacement);
            UnityBanners.loadBanner(MainActivity.this,bannerAdPlacement);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DisplayInterstitialAd();
                }
            },5000);
        }else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    UnityAds.load(interstitialAdPlacement);
                    UnityAds.load(rewardedAdPlacement);


                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            DisplayInterstitialAd();
                            UnityBanners.loadBanner(MainActivity.this,bannerAdPlacement);
                        }
                    },5000);
                }
            },5000);
        }


        IUnityBannerListener iUnityBannerListener = new IUnityBannerListener() {
            @Override
            public void onUnityBannerLoaded(String s, View view) {
                ((ViewGroup)findViewById(R.id.bannerAdLayout)).removeView(view);
                ((ViewGroup)findViewById(R.id.bannerAdLayout)).addView(view);

            }

            @Override
            public void onUnityBannerUnloaded(String s) {

            }

            @Override
            public void onUnityBannerShow(String s) {

            }

            @Override
            public void onUnityBannerClick(String s) {

            }

            @Override
            public void onUnityBannerHide(String s) {

            }

            @Override
            public void onUnityBannerError(String s) {

            }
        };

        UnityBanners.setBannerListener(iUnityBannerListener);

        showInterstitialBtn = findViewById(R.id.showInterstitialAdBtn);
        showRewardedBtn = findViewById(R.id.showRewardedBtn);
        showBannerBtn = findViewById(R.id.showBannerAdBtn);

        showInterstitialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnityAds.load(interstitialAdPlacement);
                DisplayInterstitialAd();

            }
        });

        showRewardedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnityAds.load(rewardedAdPlacement);
                DisplayInterstitialAd();

            }
        });

        showBannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnityBanners.loadBanner(MainActivity.this,bannerAdPlacement);
            }
        });

    }


    private  void DisplayInterstitialAd (){
        if (UnityAds.isReady(interstitialAdPlacement)){
            UnityAds.show(MainActivity.this,interstitialAdPlacement);
        }  else if (UnityAds.isReady(rewardedAdPlacement)){
            UnityAds.show(MainActivity.this,rewardedAdPlacement);
        }
    }



}
