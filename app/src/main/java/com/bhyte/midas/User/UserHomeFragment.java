package com.bhyte.midas.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bhyte.midas.AccountCreation.SignUpVerifyIdentity;
import com.bhyte.midas.R;
import com.bhyte.midas.Store.Store;
import com.bhyte.midas.Transactions.AddMoneyChooseMethod;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserHomeFragment extends Fragment {

    public static String key, usernameS, amountEntered;
    public int lengthOfVal;
    private AdLoader adLoader;

    FirebaseDatabase database;
    ShimmerFrameLayout shimmerFrameLayout;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    SharedPreferences selectedCurrency;
    LinearLayout adView;
    View viewHolder;
    String val = "visible";
    EditText enterAmount;
    CircleImageView profilePicture;
    String account_balance, fullName;
    BottomSheetDialog bottomSheetDialog;
    RelativeLayout currencyView, verificationStatus, virtualCard, midasStore, usdLayout, ghcLayout;
    MaterialButton addMoney, continueButton;
    ImageView toggleIcon, check1, check2;
    Animation animation, animation2;
    TextView currency, username, totalAssets, accountBalance, greetingText, recommendedText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_home, container, false);

        // Instance of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();

        // Hooks
        adView = root.findViewById(R.id.recommended_layout);
        recommendedText = root.findViewById(R.id.recommended_text);
        viewHolder = root.findViewById(R.id.view_holder);
        username = root.findViewById(R.id.username);
        currencyView = root.findViewById(R.id.currency_view);
        currency = root.findViewById(R.id.currency);
        addMoney = root.findViewById(R.id.add_money_button);
        accountBalance = root.findViewById(R.id.account_balance);
        toggleIcon = root.findViewById(R.id.toggle_icon);
        verificationStatus = root.findViewById(R.id.verification_status);
        totalAssets = root.findViewById(R.id.total_assets);
        profilePicture = root.findViewById(R.id.profile_picture);
        greetingText = root.findViewById(R.id.greetings);
        virtualCard = root.findViewById(R.id.virtual_card);
        midasStore = root.findViewById(R.id.store);
        shimmerFrameLayout = root.findViewById(R.id.shimmer_layout);

        // Save Account Balance in variable
        account_balance = accountBalance.getText().toString();

        selectedCurrency = getContext().getSharedPreferences("selectedCurrency", Context.MODE_PRIVATE);
        boolean currencyBoolean = selectedCurrency.getBoolean("Currency", true);

        if (currencyBoolean) {
            updateToCedis();
        } else {
            updateToDollar();
        }

        profilePicture.setOnClickListener(v -> startActivity(new Intent(getContext(), Profile.class)));

        verificationStatus.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SignUpVerifyIdentity.class));
            key = "no skip";
        });

        currencyView.setOnClickListener(v -> { bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetTheme);

            View sheetView = LayoutInflater.from(getActivity()).inflate(R.layout.currency_bottom_sheet, root.findViewById(R.id.currency_sheet));

            bottomSheetDialog.setContentView(sheetView);
            bottomSheetDialog.show();

            // Hooks
            usdLayout = bottomSheetDialog.findViewById(R.id.united_states_dollar);
            ghcLayout = bottomSheetDialog.findViewById(R.id.ghana_cedi);
            check1 = bottomSheetDialog.findViewById(R.id.check1);
            check2 = bottomSheetDialog.findViewById(R.id.check2);

            if (currencyBoolean) {
                check1.setVisibility(View.VISIBLE);
                check2.setVisibility(View.INVISIBLE);
            } else {
                check1.setVisibility(View.INVISIBLE);
                check2.setVisibility(View.VISIBLE);
            }

            ghcLayout.setOnClickListener(v12 -> {
                // Save
                SharedPreferences.Editor editor = selectedCurrency.edit();
                editor.putBoolean("Currency", true);
                editor.apply();

                // Activate check
                check1.setVisibility(View.VISIBLE);
                check2.setVisibility(View.INVISIBLE);

                // Close Bottom Sheet
                bottomSheetDialog.dismiss();

                // Change Currency
                updateToCedis();
            });

            usdLayout.setOnClickListener(v1 -> {
                // Save
                SharedPreferences.Editor editor = selectedCurrency.edit();
                editor.putBoolean("Currency", false);
                editor.apply();

                // Activate check
                check1.setVisibility(View.INVISIBLE);
                check2.setVisibility(View.VISIBLE);

                // Close Bottom Sheet
                bottomSheetDialog.dismiss();

                // Change Currency
                updateToDollar();
            });

        });

        midasStore.setOnClickListener(v -> startActivity(new Intent(getActivity(), Store.class)));

        virtualCard.setOnClickListener(v -> startActivity(new Intent(getActivity(), VirtualCardChooseDesign.class)));

        addMoney.setOnClickListener(v -> {
            bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetTheme);

            View sheetView = LayoutInflater.from(getActivity()).inflate(R.layout.enter_amount_bottom_sheet, root.findViewById(R.id.amount_sheet));

            bottomSheetDialog.setContentView(sheetView);
            bottomSheetDialog.show();

            // Hooks
            continueButton = bottomSheetDialog.findViewById(R.id.continue_button);
            enterAmount = bottomSheetDialog.findViewById(R.id.amount_input_layout);

            // Click Listeners
            assert continueButton != null;
            continueButton.setOnClickListener(v13 -> checkInput());
        });

        toggleIcon.setOnClickListener(v -> {
            if (val.equals("visible")) {
                toggleIcon.setImageResource(R.drawable.toggle_balance_);
                accountBalance.setText("****");
                val = "invisible";
            } else if (val.equals("invisible")) {
                toggleIcon.setImageResource(R.drawable.toggle_balance);
                accountBalance.setText(account_balance);
                val = "visible";
            }
        });

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Set Profile Picture
        assert firebaseUser != null;
        if (firebaseUser.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(firebaseUser.getPhotoUrl())
                    .into(profilePicture);
        }

        // Greeting
        greetUser();
        getName();

        // Initialize Ads
        MobileAds.initialize(getContext(), initializationStatus -> {
        });

        // Load Native Ads
        adLoader = new AdLoader.Builder(getContext(), "ca-app-pub-3862971524430784/7025923215")
                .forNativeAd(NativeAd -> {
                    // Show the ad
                    if(adLoader.isLoading()){
                        //Show Shimmer
                        shimmerFrameLayout.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.startShimmer();
                    }

                    // Destroy Ads
                    if (getActivity() == null) {
                        NativeAd.destroy();
                    }

                    NativeTemplateStyle styles = new NativeTemplateStyle.Builder().withMainBackgroundColor(new ColorDrawable(Color.TRANSPARENT)).build();
                    TemplateView template = view.findViewById(R.id.recommended);
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
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                        // Make Ad View Invisible
                        adView.setVisibility(View.GONE);
                        recommendedText.setVisibility(View.GONE);
                        viewHolder.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onAdLoaded() {
                        // Stop and hide shimmer
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        // Make Ad View Visible
                        adView.setVisibility(View.VISIBLE);
                        recommendedText.setVisibility(View.VISIBLE);
                        viewHolder.setVisibility(View.GONE);
                        // Animate
                        animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_animation);
                        adView.setAnimation(animation2);
                        recommendedText.setAnimation(animation2);
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

    private void updateToDollar() {
        totalAssets.setText(R.string.usd);
        currency.setText(R.string.dollar_usd);

        // TODO Convert currency to dollars

    }

    private void updateToCedis() {
        totalAssets.setText(R.string.total_assets_ghc);
        currency.setText(R.string.cedi);

        // TODO Convert currency to cedis

    }

    private void checkInput() {
        amountEntered = enterAmount.getText().toString().trim();
        lengthOfVal = amountEntered.length();

        if (lengthOfVal >= 2) {
            startActivity(new Intent(getActivity(), AddMoneyChooseMethod.class));
        } else if (lengthOfVal == 0) {
            enterAmount.setError("Please enter amount");
        } else {
            enterAmount.setError("Amount should be 10 or more");
        }
    }

    private void getName() {
        firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference databaseReference = database.getReference("Users").child(firebaseUser.getUid()).child("name");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fullName = snapshot.getValue(String.class);
                setName();
                if (!(fullName == null)) {
                    usernameS = fullName;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setName() {
        username.setText(fullName + " 👋");
        if (username != null) {
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_animation);
            username.setAnimation(animation);
        }
    }

    private void greetUser() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay;
        timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay < 12) {
            greetingText.setText(R.string.morning);
            // Set animation
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_animation);
            greetingText.setAnimation(animation);
        } else if (timeOfDay < 16) {
            greetingText.setText(R.string.afternoon);
            // Set Animation
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_animation);
            greetingText.setAnimation(animation);
        } else if (timeOfDay < 21) {
            greetingText.setText(R.string.evening);
            // Set Animation
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_animation);
            greetingText.setAnimation(animation);
        } else {
            greetingText.setText(R.string.night);
            // Set Animation
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_animation);
            greetingText.setAnimation(animation);
        }
    }

}