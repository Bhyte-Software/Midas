package com.bhyte.midas.User;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhyte.midas.AccountCreation.GetStarted;
import com.bhyte.midas.AccountCreation.SignUpVerifyIdentity;
import com.bhyte.midas.R;
import com.bhyte.midas.Recycler.QuickActionsAdapter;
import com.bhyte.midas.Recycler.QuickActionsHelperClass;
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

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserHomeFragment extends Fragment implements QuickActionsAdapter.OnNoteListener {

    ScrollView scrollView;
    RecyclerView quickActionsRecycler;
    RecyclerView.Adapter adapter;
    ArrayList<QuickActionsHelperClass> viewQuickActions = new ArrayList<>();

    public static String key, usernameS;
    public int lengthOfAmount;
    Context context;
    FirebaseDatabase database;
    ShimmerFrameLayout shimmerFrameLayout;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    SharedPreferences selectedCurrency;
    LinearLayout adView;
    View viewHolder;
    String val = "visible";
    CircleImageView profilePicture;
    String account_balance, fullName;
    BottomSheetDialog bottomSheetDialog;
    RelativeLayout currencyView, verificationStatus, usdLayout, ghcLayout, gradientLayout, roundRec;
    MaterialButton addMoney;
    ImageView toggleIcon, check1, check2;
    Animation animation, animation2;
    TextView currency, username, totalAssets, accountBalance, greetingText, recommendedText, text1, text2, text3;
    private AdLoader adLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_home, container, false);

        // Context
        this.context = getContext();

        // Instance of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();

        // Hooks
        scrollView = root.findViewById(R.id.scroll_layout);
        quickActionsRecycler = root.findViewById(R.id.quick_actions_recycler);
        text1 = root.findViewById(R.id.text1);
        text2 = root.findViewById(R.id.text2);
        text3 = root.findViewById(R.id.text3);
        roundRec = root.findViewById(R.id.round_rec);
        gradientLayout = root.findViewById(R.id.gradient_layout);
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
        shimmerFrameLayout = root.findViewById(R.id.shimmer_layout);

        // Clipping
        scrollView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        scrollView.setClipToOutline(true);

        // Recycler
        quickActionsRecycler.setFocusable(false);
        quickActionsRecycler();

        // Save Account Balance in variable
        account_balance = accountBalance.getText().toString();

        selectedCurrency = context.getSharedPreferences("selectedCurrency", Context.MODE_PRIVATE);
        boolean currencyBoolean = selectedCurrency.getBoolean("Currency", true);

        if (currencyBoolean) {
            updateToCedis();
        } else {
            updateToDollar();
        }

        profilePicture.setOnClickListener(v -> startActivity(new Intent(getContext(), Profile.class)));

        verificationStatus.setOnClickListener(v -> {
            startActivity(new Intent(context, SignUpVerifyIdentity.class));
            key = "no skip";
        });

        currencyView.setOnClickListener(v -> {
            bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.BottomSheetTheme);

            View sheetView = LayoutInflater.from(context).inflate(R.layout.currency_bottom_sheet, root.findViewById(R.id.currency_sheet));

            bottomSheetDialog.setContentView(sheetView);
            bottomSheetDialog.show();

            // Hooks
            usdLayout = bottomSheetDialog.findViewById(R.id.united_states_dollar);
            ghcLayout = bottomSheetDialog.findViewById(R.id.ghana_cedi);
            check1 = bottomSheetDialog.findViewById(R.id.check1);
            check2 = bottomSheetDialog.findViewById(R.id.check2);

            if (currencyBoolean) {
                check1.setVisibility(View.VISIBLE);
                check2.setVisibility(View.GONE);
            } else {
                check1.setVisibility(View.GONE);
                check2.setVisibility(View.VISIBLE);
            }
            check2.setVisibility(View.GONE);

            ghcLayout.setOnClickListener(v12 -> {
                // Save
                SharedPreferences.Editor editor = selectedCurrency.edit();
                editor.putBoolean("Currency", true);
                editor.apply();

                // Activate check
                check1.setVisibility(View.VISIBLE);
                check2.setVisibility(View.GONE);

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
                check1.setVisibility(View.GONE);
                check2.setVisibility(View.VISIBLE);

                // Close Bottom Sheet
                bottomSheetDialog.dismiss();

                // Change Currency
                updateToDollar();
            });

        });

        addMoney.setOnClickListener(v -> startActivity(new Intent(getActivity(), AddMoneyChooseMethod.class)));

        toggleIcon.setOnClickListener(v -> {
            if (val.equals("visible")) {
                toggleIcon.setImageResource(R.drawable.toggle_balance_);
                lengthOfAmount = accountBalance.getText().length();
                String repeated = new String(new char[lengthOfAmount]).replace("\0", "*");
                accountBalance.setText(repeated);
                val = "invisible";
            } else if (val.equals("invisible")) {
                toggleIcon.setImageResource(R.drawable.toggle_balance);
                accountBalance.setText(account_balance);
                val = "visible";
            }
        });
        return root;
    }

    private void quickActionsRecycler() {
        quickActionsRecycler.setHasFixedSize(true);
        quickActionsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        viewQuickActions.add(new QuickActionsHelperClass(R.drawable.add, "Create Card"));
        viewQuickActions.add(new QuickActionsHelperClass(R.drawable.check, "Deposit"));
        viewQuickActions.add(new QuickActionsHelperClass(R.drawable.card, "Cards"));
        viewQuickActions.add(new QuickActionsHelperClass(R.drawable.store, "Store"));
        viewQuickActions.add(new QuickActionsHelperClass(R.drawable.logout, "Sign Out"));

        adapter = new QuickActionsAdapter(viewQuickActions, this);
        quickActionsRecycler.setAdapter(adapter);
    }

    @Override
    public void onNoteClick(int position) {
        if(position == 0){
            startActivity(new Intent(getActivity(), CreateCard.class));
        }
        if(position == 1){
            startActivity(new Intent(getActivity(), AddMoneyChooseMethod.class));
        }
        if(position == 2){
            // Cards Fragment
            Log.d(TAG, "onNoteClick: ");
        }
        if(position == 3){
            startActivity(new Intent(getActivity(), Store.class));
        }
        if(position == 4){
            // Sign Out
            firebaseAuth.signOut();
            startActivity(new Intent(getActivity(), GetStarted.class));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Set Profile Picture
        if (firebaseUser.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(firebaseUser.getPhotoUrl())
                    .into(profilePicture);
        }

        // Greeting
        greetUser();
        getName();

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

                    // Destroy Ads
                    if (context == null) {
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
                        animation2 = AnimationUtils.loadAnimation(context, R.anim.fade_animation);
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
        if (context != null) {
            animation = AnimationUtils.loadAnimation(context, R.anim.fade_animation);
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
            animation = AnimationUtils.loadAnimation(context, R.anim.fade_animation);
            greetingText.setAnimation(animation);
        } else if (timeOfDay < 16) {
            greetingText.setText(R.string.afternoon);
            // Set Animation
            animation = AnimationUtils.loadAnimation(context, R.anim.fade_animation);
            greetingText.setAnimation(animation);
        } else if (timeOfDay < 21) {
            greetingText.setText(R.string.evening);
            // Set Animation
            animation = AnimationUtils.loadAnimation(context, R.anim.fade_animation);
            greetingText.setAnimation(animation);
        } else {
            greetingText.setText(R.string.night);
            // Set Animation
            animation = AnimationUtils.loadAnimation(context, R.anim.fade_animation);
            greetingText.setAnimation(animation);
        }
    }
}