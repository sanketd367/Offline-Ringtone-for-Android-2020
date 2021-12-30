package com.invariablestudio.nokiaringtone;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class ActivityHome extends AppCompatActivity {

    LinearLayout adContainer;
    private AdView bannerAdView;
    private UnifiedNativeAd nativeAd;
    Preference prefs;
    ImageView shareImage;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.home);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle((CharSequence) "");
            toolbar.setSubtitle(getString(R.string.app_name));
            setSupportActionBar(toolbar);
        }*/
        shareImage=findViewById(R.id.shareImage);
        this.bannerAdView = new AdView(this);
        bannerAdView.setAdSize(AdSize.BANNER);
        bannerAdView.setAdUnitId(getString(R.string.bannerAdId));
        this.adContainer = (LinearLayout) findViewById(R.id.adView);
        this.adContainer.addView(this.bannerAdView);

        AdRequest adRequest = new AdRequest.Builder().build();
        this.bannerAdView.loadAd(adRequest);


        shareImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityMain.shareApp(ActivityHome.this);
            }
        });


        this.bannerAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                ActivityHome.this.adContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                Log.e("nikk",""+adError);
                ActivityHome.this.adContainer.setVisibility(View.INVISIBLE);
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

//        this.bannerAdView.setAdListener(new AdListener() {
//
//            public void onAdFailedToLoad(int adError) {
//
//                Log.e("nikk",""+adError);
//                ActivityHome.this.adContainer.setVisibility(View.INVISIBLE);
//            }
//
//            public void onAdLoaded() {
//                ActivityHome.this.adContainer.setVisibility(View.VISIBLE);
//            }
//        });

        final ImageView imageView = (ImageView) findViewById(R.id.nativeImageView);


        refreshAd();

        ((RelativeLayout) findViewById(R.id.goMain1)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActivityHome.this.startActivity(new Intent(ActivityHome.this.getApplication(), ActivityMain.class));
                ActivityHome.this.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        });
        ((RelativeLayout) findViewById(R.id.goMain2)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ActivityHome.this.startActivity(new Intent(ActivityHome.this.getApplication(), FavouriteActivity.class));
                ActivityHome.this.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        });
        this.prefs = new Preference(this);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share_only, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.share_app) {
            return super.onOptionsItemSelected(menuItem);
        }
        ActivityMain.shareApp(this);
        return true;
    }*/

    private void refreshAd() {
        AdLoader.Builder builder = new AdLoader.Builder((Context) this, getResources().getString(R.string.nativeAdId));
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                if (ActivityHome.this.nativeAd != null) {
                    ActivityHome.this.nativeAd.destroy();
                }
                ActivityHome.this.nativeAd = unifiedNativeAd;
                LinearLayout frameLayout = ((LinearLayout) ActivityHome.this.findViewById(R.id.nativeContainer));
                //FrameLayout frameLayout = (FrameLayout) ActivityHome.this.findViewById(R.id.fl_adplaceholder);
                UnifiedNativeAdView unifiedNativeAdView = (UnifiedNativeAdView) ActivityHome.this.getLayoutInflater().inflate(R.layout.ad_unified, null);
                ActivityHome.this.populateUnifiedNativeAdView(unifiedNativeAd, unifiedNativeAdView);
                frameLayout.removeAllViews();
                frameLayout.addView(unifiedNativeAdView);
            }
        });
        builder.withNativeAdOptions(new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().build()).build());
        builder.withAdListener(new AdListener() {
            public void onAdFailedToLoad(int i) {
            }
        }).build().loadAd(new AdRequest.Builder().build());
    }


    private void populateUnifiedNativeAdView(UnifiedNativeAd unifiedNativeAd, UnifiedNativeAdView unifiedNativeAdView) {
        unifiedNativeAdView.setMediaView((MediaView) unifiedNativeAdView.findViewById(R.id.ad_media));
        unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
        unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
        unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));
        unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.ad_app_icon));
        unifiedNativeAdView.setPriceView(unifiedNativeAdView.findViewById(R.id.ad_price));
        unifiedNativeAdView.setStarRatingView(unifiedNativeAdView.findViewById(R.id.ad_stars));
        unifiedNativeAdView.setStoreView(unifiedNativeAdView.findViewById(R.id.ad_store));
        unifiedNativeAdView.setAdvertiserView(unifiedNativeAdView.findViewById(R.id.ad_advertiser));
        ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
        if (unifiedNativeAd.getBody() == null) {
            unifiedNativeAdView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
        }
        if (unifiedNativeAd.getCallToAction() == null) {
            unifiedNativeAdView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
        }
        if (unifiedNativeAd.getIcon() == null) {
            unifiedNativeAdView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
            unifiedNativeAdView.getIconView().setVisibility(View.VISIBLE);
        }
        if (unifiedNativeAd.getPrice() == null) {
            unifiedNativeAdView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdView.getPriceView()).setText(unifiedNativeAd.getPrice());
        }
        if (unifiedNativeAd.getStore() == null) {
            unifiedNativeAdView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdView.getStoreView()).setText(unifiedNativeAd.getStore());
        }
        if (unifiedNativeAd.getStarRating() == null) {
            unifiedNativeAdView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) unifiedNativeAdView.getStarRatingView()).setRating(unifiedNativeAd.getStarRating().floatValue());
            unifiedNativeAdView.getStarRatingView().setVisibility(View.VISIBLE);
        }
        if (unifiedNativeAd.getAdvertiser() == null) {
            unifiedNativeAdView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) unifiedNativeAdView.getAdvertiserView()).setText(unifiedNativeAd.getAdvertiser());
            unifiedNativeAdView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        unifiedNativeAdView.setNativeAd(unifiedNativeAd);
    }

    @Override
    public void onBackPressed() {
        if (this.prefs.getBoolean(CONST.RATE)) {
            super.onBackPressed();
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            return;
        }
        requestForRatingReview();
    }

    void requestForRatingReview() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getResources().getString(R.string.rate_title));
        stringBuilder.append(" ");
        stringBuilder.append(getResources().getString(R.string.app_name));
        ActivityMain.showDialog(this, stringBuilder.toString(), getResources().getString(R.string.rate_msg), getResources().getString(R.string.rate_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    ActivityHome.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName())));
                } catch (ActivityNotFoundException unused) {
                    ActivityHome activityHome = ActivityHome.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(ActivityHome.this.getResources().getString(R.string.share_link));
                    stringBuilder.append(BuildConfig.APPLICATION_ID);
                    activityHome.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString())));
                }
                ActivityHome.this.prefs.setBoolean(CONST.RATE, Boolean.valueOf(true));
            }
        }, getResources().getString(R.string.rate_later), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityHome.this.finish();
                ActivityHome.this.overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            }
        });
    }
}
