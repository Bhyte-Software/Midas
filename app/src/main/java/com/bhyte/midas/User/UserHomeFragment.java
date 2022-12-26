package com.bhyte.midas.User;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhyte.midas.AccountCreation.GetStarted;
import com.bhyte.midas.AccountCreation.SignUpVerifyIdentity;
import com.bhyte.midas.R;
import com.bhyte.midas.Recycler.PlatformsAdapter;
import com.bhyte.midas.Recycler.PlatformsHelperClass;
import com.bhyte.midas.Recycler.QuickActionsAdapter;
import com.bhyte.midas.Recycler.QuickActionsHelperClass;
import com.bhyte.midas.Store.Store;
import com.bhyte.midas.Transactions.AddMoneyChooseMethod;
import com.bhyte.midas.Transactions.DepositSuccessPage;
import com.bhyte.midas.Transactions.WithdrawMoney;
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
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserHomeFragment extends Fragment implements QuickActionsAdapter.OnNoteListener, View.OnTouchListener, ViewTreeObserver.OnScrollChangedListener {

    public static String key, usernameS;
    public int lengthOfAmount;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    RecyclerView.Adapter<?> adapter;
    RecyclerView.Adapter<?> platformsAdapter;
    ArrayList<QuickActionsHelperClass> viewQuickActions = new ArrayList<>();
    ArrayList<PlatformsHelperClass> viewPlatforms = new ArrayList<>();
    ScrollView scrollView;
    RecyclerView quickActionsRecycler, platformsRecycler;
    Context context;
    FirebaseDatabase database;
    ShimmerFrameLayout shimmerFrameLayout;
    SharedPreferences selectedCurrency;
    LinearLayout adView;
    String val = "visible";
    CircleImageView profilePicture;
    String account_balance, fullName;
    BottomSheetDialog bottomSheetDialog, logoutDialog;
    RelativeLayout currencyView, verificationStatus, usdLayout, ghcLayout, gradientLayout, roundRec, goUp, viewAllPlatforms;
    MaterialButton addMoney, withdraw, positive, negative;
    ImageView toggleIcon, check1, check2;
    Animation animation, animation2, shakeAnimation;
    TextView currency, username, totalAssets, accountBalance, greetingText, recommendedText, text1, text2, text3;
    private AdLoader adLoader;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Instance of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        View root = inflater.inflate(R.layout.fragment_user_home, container, false);

        // Context
        this.context = getContext();

        // Hooks
        viewAllPlatforms = root.findViewById(R.id.top_platforms);
        goUp = root.findViewById(R.id.go_up);
        scrollView = root.findViewById(R.id.scroll_layout);
        quickActionsRecycler = root.findViewById(R.id.quick_actions_recycler);
        platformsRecycler = root.findViewById(R.id.platforms_recycler);
        text1 = root.findViewById(R.id.text1);
        text2 = root.findViewById(R.id.text2);
        text3 = root.findViewById(R.id.text3);
        roundRec = root.findViewById(R.id.round_rec);
        gradientLayout = root.findViewById(R.id.gradient_layout);
        adView = root.findViewById(R.id.recommended_layout);
        recommendedText = root.findViewById(R.id.recommended_text);
        username = root.findViewById(R.id.username);
        currencyView = root.findViewById(R.id.currency_view);
        currency = root.findViewById(R.id.currency);
        addMoney = root.findViewById(R.id.add_money_button);
        withdraw = root.findViewById(R.id.withdraw_button);
        accountBalance = root.findViewById(R.id.account_balance);
        toggleIcon = root.findViewById(R.id.toggle_icon);
        verificationStatus = root.findViewById(R.id.verification_status);
        totalAssets = root.findViewById(R.id.total_assets);
        profilePicture = root.findViewById(R.id.profile_picture);
        greetingText = root.findViewById(R.id.greetings);
        shimmerFrameLayout = root.findViewById(R.id.shimmer_layout);

        // Shake View
        shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.horizontal);
        verificationStatus.setAnimation(shakeAnimation);

        // Recycler
        quickActionsRecycler.setFocusable(false);
        platformsRecycler.setFocusable(false);

        // Recycler View Functions
        quickActionsRecycler();
        platformsRecycler();

        // Invoke touch listener to detect movement of ScrollView
        scrollView.setOnTouchListener(this);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(this);


        selectedCurrency = context.getSharedPreferences("selectedCurrency", Context.MODE_PRIVATE);
        boolean currencyBoolean = selectedCurrency.getBoolean("Currency", true);

        if (currencyBoolean) {
            updateToCedis();
        } else {
            updateToDollar();
        }

        viewAllPlatforms.setOnClickListener(v -> startActivity(new Intent(getActivity(), ViewAllPlatforms.class)));

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

        // Display main balance gotten
        getMainBalance();

        addMoney.setOnClickListener(v -> startActivity(new Intent(getActivity(), AddMoneyChooseMethod.class)));

        withdraw.setOnClickListener(v -> startActivity(new Intent(getActivity(), WithdrawMoney.class))); //Send user to Withdraw money page

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

    private void platformsRecycler() {
        platformsRecycler.setHasFixedSize(true);
        platformsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        viewPlatforms.add(new PlatformsHelperClass(R.drawable.amazon_logo, "Amazon", "Shopping"));
        viewPlatforms.add(new PlatformsHelperClass(R.drawable.spotify_logo, "Spotify", "Music & Podcast"));
        viewPlatforms.add(new PlatformsHelperClass(R.drawable.netflix, "Netflix", "Entertainment"));
        viewPlatforms.add(new PlatformsHelperClass(R.drawable.spotify_logo, "Spotify", "Music & Podcast"));

        platformsAdapter = new PlatformsAdapter(viewPlatforms);
        platformsRecycler.setAdapter(platformsAdapter);
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
        if (position == 0) {
            startActivity(new Intent(getActivity(), CreateCard.class));
        }
        if (position == 1) {
            startActivity(new Intent(getActivity(), AddMoneyChooseMethod.class));
        }
        if (position == 2) {
            // Cards Fragment
            Log.d(TAG, "onNoteClick: ");
        }
        if (position == 3) {
            startActivity(new Intent(getActivity(), Store.class));
        }
        if (position == 4) {
            // Sign Out
            logoutDialog = new BottomSheetDialog(context, R.style.BottomSheetTheme);

            View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.logout_popup,
                    requireView().findViewById(R.id.logout_popup));

            logoutDialog.setContentView(dialogView);
            logoutDialog.show();

            //Hooks
            positive = dialogView.findViewById(R.id.cancel);
            negative = dialogView.findViewById(R.id.logout);

            positive.setOnClickListener(v1 -> logoutDialog.dismiss());
            negative.setOnClickListener(v12 -> {
                logoutDialog.dismiss();
                firebaseAuth.signOut();
                startActivity(new Intent(getContext(), GetStarted.class));
            });
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
        adLoader = new AdLoader.Builder(context, "ca-app-pub-3862971524430784/7025923215").forNativeAd(NativeAd -> {
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
                    final NativeAd.Image getIcon = NativeAd.getIcon();
                    final String getHeadline = NativeAd.getHeadline();
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
                        recommendedText.setVisibility(View.VISIBLE);
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




    // ----- FUNCTIONS ----- //
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

    private void getMainBalance() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        assert firebaseUser != null;
        databaseReference.child(firebaseUser.getUid()).child("userMainBalance").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String currentBalance = snapshot.getValue(String.class);
                assert currentBalance != null;

                // Save Account Balance in variable
                account_balance = currentBalance;
                accountBalance.setText(account_balance);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Print an error message
                System.out.println("Error retrieving user main balance: " + error.getMessage());
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onScrollChanged() {
        if (scrollView.getChildAt(0).getBottom() == (scrollView.getHeight() + scrollView.getScrollY())) {
            //scroll view is at bottom
            goUp.setVisibility(View.VISIBLE);
            ScaleAnimation anim = new ScaleAnimation(1, 1, 0, 1);
            goUp.setAnimation(anim);
            goUp.setOnClickListener(v -> goToTop());
        } else {
            ScaleAnimation anim = new ScaleAnimation(1, 1, 1, 0);
            goUp.setAnimation(anim);
            goUp.setVisibility(View.INVISIBLE);
            //scroll view is not at bottom
        }
    }

    private void goToTop() {
        scrollView.smoothScrollTo(0, 0);
    }
}