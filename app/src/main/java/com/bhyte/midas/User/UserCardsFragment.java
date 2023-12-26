package com.bhyte.midas.User;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhyte.midas.R;
import com.bhyte.midas.Recycler.CardsAdapter;
import com.bhyte.midas.Recycler.CardsHelperClass;
import com.bhyte.midas.Recycler.SwipeToDeleteCallback;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserCardsFragment extends Fragment implements CardsAdapter.OnNoteListener {

    RecyclerView.Adapter<?> cardsAdapter;
    ArrayList<CardsHelperClass> viewCards = new ArrayList<>();

    RelativeLayout pageTitle, bg, floatingActionButton;
    ImageView noCardsImg;
    TextView title, desc;
    RecyclerView cardsRecycler;
    MaterialButton createCardButton;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_cards, container, false);

        // Hooks
        floatingActionButton = root.findViewById(R.id.floating_action_button);
        bg = root.findViewById(R.id.bg);
        pageTitle = root.findViewById(R.id.page_title);
        noCardsImg = root.findViewById(R.id.no_cards_img);
        title = root.findViewById(R.id.title);
        desc = root.findViewById(R.id.description);
        cardsRecycler = root.findViewById(R.id.cards_recycler);
        createCardButton = root.findViewById(R.id.create_card_button);
        this.context = getContext();

        // Initialize Recycler View
        cardsRecycler.setHasFixedSize(true);
        cardsRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


        // Click Listeners
        floatingActionButton.setOnClickListener(view -> createCard());

        // Button takes you back home
        createCardButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            UserHomeFragment userHomeFragment = new UserHomeFragment();
            fragmentTransaction.replace(R.id.container, userHomeFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });


        enableSwipeToDeleteAndUndo();

        return root;
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAbsoluteAdapterPosition();

                viewCards.remove(position);


                Snackbar snackbar = Snackbar
                        .make(bg, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", view -> {

                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(cardsRecycler);
    }

    private void createCard() {
        if(cardsRecycler.getVisibility() == View.GONE){
            pageTitle.setVisibility(View.VISIBLE);
            cardsRecycler.setVisibility(View.VISIBLE);
            floatingActionButton.setVisibility(View.VISIBLE);

            // Hide Other items
            createCardButton.setVisibility(View.GONE);
            noCardsImg.setVisibility(View.GONE);
            title.setVisibility(View.GONE);
            desc.setVisibility(View.GONE);
        }

        viewCards.add(new CardsHelperClass(R.drawable.red_card, "John Doe", "2142 2131 1423", "21/04/2022"));
        viewCards.add(new CardsHelperClass(R.drawable.blue_card, "Duke Opoku", "9802 5689 2346", "16/10/2022"));

        cardsAdapter = new CardsAdapter(viewCards, this);
        cardsRecycler.setAdapter(cardsAdapter);

        /* FLUTTERWAVE API
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        JSONObject actualData = new JSONObject();
        try {
            actualData.put("currency", "USD");
            actualData.put("amount", 100);
            actualData.put("first_name", "Charles");
            actualData.put("last_name", "Xavier");
            actualData.put("date_of_birth", "1996/12/30");
            actualData.put("email", "dukeopoku@gmail.com");
            actualData.put("phone", "0240369071");
            actualData.put("gender", "M");
            actualData.put("title", "Mr");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(actualData.toString(), mediaType);
        Request request = new Request.Builder()
                .url("https://api.flutterwave.com/v3/virtual-cards")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("Authorization", "Bearer FLWSECK_TEST-a6281194ef4ca095e794a1681fe32d69-X")
                .build();
        try {
            Response response = client.newCall(request).execute();

            TextView tv = new TextView(getActivity());
            //Create and pass the response
            tv.setText(Objects.requireNonNull(response.body()).string());

            //bg.addView(tv);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onNoteClick(int position) {
        startActivity(new Intent(getActivity(), VirtualCardDetails.class));
    }
}