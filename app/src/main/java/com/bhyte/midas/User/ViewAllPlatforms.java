package com.bhyte.midas.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.bhyte.midas.R;
import com.bhyte.midas.Recycler.PlatformsAdapter;
import com.bhyte.midas.Recycler.PlatformsAdapterVertical;
import com.bhyte.midas.Recycler.PlatformsHelperClass;

import java.util.ArrayList;

public class ViewAllPlatforms extends AppCompatActivity {

    ArrayList<PlatformsHelperClass> viewPlatforms = new ArrayList<>();
    RecyclerView.Adapter<?> platformsAdapter;

    Context context;
    RecyclerView topPlatforms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_platforms);

        this.context = getApplicationContext();

        // Hooks
        topPlatforms = findViewById(R.id.top_platforms_recycler);

        // Recycler
        topPlatforms.setFocusable(false);

        // Recycler View Functions
        platformsRecycler();

    }

    private void platformsRecycler() {
        topPlatforms.setHasFixedSize(true);
        topPlatforms.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        viewPlatforms.add(new PlatformsHelperClass(R.drawable.amazon_logo, "Amazon", "Shopping"));
        viewPlatforms.add(new PlatformsHelperClass(R.drawable.spotify_logo, "Spotify", "Music & Podcast"));
        viewPlatforms.add(new PlatformsHelperClass(R.drawable.netflix, "Netflix", "Entertainment"));
        viewPlatforms.add(new PlatformsHelperClass(R.drawable.amazon_logo, "Amazon", "Shopping"));
        viewPlatforms.add(new PlatformsHelperClass(R.drawable.spotify_logo, "Spotify", "Music & Podcast"));
        viewPlatforms.add(new PlatformsHelperClass(R.drawable.netflix, "Netflix", "Entertainment"));

        platformsAdapter = new PlatformsAdapterVertical(viewPlatforms);
        topPlatforms.setAdapter(platformsAdapter);

    }

    public void callBack(View view) {
        finish();
    }
}