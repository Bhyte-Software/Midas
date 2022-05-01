package com.bhyte.midas.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bhyte.midas.R;
import com.bhyte.midas.User.Profile;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.nativead.NativeAdOptions;

public class About extends AppCompatActivity {

    LinearLayout adView;
    ShimmerFrameLayout shimmerFrameLayout;
    AdLoader adLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Hooks
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        adView = findViewById(R.id.recommended_layout);

        // Initialize Ads
        MobileAds.initialize(About.this, initializationStatus -> {
        });

        // Load Native Ads
        adLoader = new AdLoader.Builder(About.this, "ca-app-pub-3862971524430784/7025923215")
                .forNativeAd(NativeAd -> {
                    // Show the ad
                    if (adLoader.isLoading()) {
                        //Show Shimmer
                        shimmerFrameLayout.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.startShimmer();
                    }

                    NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(new ColorDrawable(Color.TRANSPARENT)).build();
                    TemplateView template = findViewById(R.id.recommended);
                    template.setStyles(styles);
                    template.setNativeAd(NativeAd);

                    /* Show Ads in custom Layout
                    final String getHeadline = NativeAd.getHeadline();
                    final NativeAd.Image getIcon = NativeAd.getIcon();
                    final String getDetails = NativeAd.getBody();
                    final String getPrice = NativeAd.getPrice();
                    final String getAdvertiserName = NativeAd.getAdvertiser();
                    final double getRating = NativeAd.getStarRating();
                    final List<NativeAd.Image> images = NativeAd.getImages();
                    */
                })
                .withAdListener(new AdListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        // Make Ad View Invisible
                        adView.setVisibility(View.GONE);

                        // Failed to load ads
                        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.R){
                            Toast toast = Toast.makeText(About.this, "Failed to load ads", Toast.LENGTH_SHORT);
                            View view1 = toast.getView();

                            //Gets the actual oval background of the Toast then sets the colour filter
                            view1.getBackground().setColorFilter(getResources().getColor(R.color.light_green), PorterDuff.Mode.SRC_IN);

                            //Gets the TextView from the Toast so it can be edited
                            TextView text = view1.findViewById(android.R.id.message);
                            text.setTextColor(getResources().getColor(R.color.white));

                            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                            toast.show();
                        }
                        else {
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_container));
                            TextView textView = (TextView) layout.findViewById(R.id.text);
                            textView.setText("Failed to load ads");

                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 20);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();
                        }
                    }

                    @Override
                    public void onAdLoaded() {
                        // Stop and hide shimmer
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        // Make Ad View Visible
                        adView.setVisibility(View.VISIBLE);
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();
        // Make Single  Ad Request
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public void callBack(View view) {
        finish();
    }
}