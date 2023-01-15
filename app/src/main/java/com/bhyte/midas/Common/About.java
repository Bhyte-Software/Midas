package com.bhyte.midas.Common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bhyte.midas.R;
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

    Context context;

    LinearLayout adView;
    ShimmerFrameLayout shimmerFrameLayout;
    AdLoader adLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        this.context = getApplicationContext();

        // Hooks
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        adView = findViewById(R.id.recommended_layout);

        // Initialize Ads
        MobileAds.initialize(context, initializationStatus -> {
        });

        // Load Native Ads
        adLoader = new AdLoader.Builder(context, "ca-app-pub-3862971524430784/7025923215")
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

                })
                .withAdListener(new AdListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                        // Make Ad View Invisible
                        adView.setVisibility(View.GONE);

                        // Failed to load ads
                        Toast toast = Toast.makeText(context, "Failed to load ads", Toast.LENGTH_SHORT);
                        View view1 = toast.getView();

                        //Gets the actual oval background of the Toast then sets the colour filter
                        view1.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.red), PorterDuff.Mode.SRC_IN);

                        //Gets the TextView from the Toast so it can be edited
                        TextView text = view1.findViewById(android.R.id.message);
                        text.setTextColor(ContextCompat.getColor(context, R.color.white));

                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                        toast.show();
                    }

                    @Override
                    public void onAdLoaded() {
                        // Get Image and Add Rounded Corners
                        ImageView adImage = adView.findViewById(R.id.icon);
                        BitmapDrawable drawable = (BitmapDrawable) adImage.getDrawable();
                        Bitmap adBitmap = drawable.getBitmap();
                        Bitmap imageRounded = Bitmap.createBitmap(adBitmap.getWidth(), adBitmap.getHeight(), adBitmap.getConfig());
                        Canvas canvas = new Canvas(imageRounded);
                        Paint paint = new Paint();
                        paint.setAntiAlias(true);
                        paint.setShader(new BitmapShader(adBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                        canvas.drawRoundRect((new RectF(0, 0, adBitmap.getWidth(), adBitmap.getHeight())), 15, 15, paint); // Round Image Corner 100 100 100 100
                        adImage.setImageBitmap(imageRounded);
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

    public void finish(View view) {
        finish();
    }
}