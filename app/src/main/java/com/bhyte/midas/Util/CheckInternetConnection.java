package com.bhyte.midas.Util;

import android.content.Context;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.bhyte.midas.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class CheckInternetConnection {
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            try {
                URL url = new URL("http://www.google.com/");
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setRequestProperty("User-Agent", "test");
                urlc.setConnectTimeout(2000); // mTimeout is in seconds
                urlc.connect();
                return urlc.getResponseCode() == 200;
            } catch (IOException e) {
                try {
                    // Make Custom Toast Instead
                    Toast toast = Toast.makeText(context, "Error checking internet connection", Toast.LENGTH_SHORT);
                    View view = toast.getView();

                    //Gets the actual oval background of the Toast then sets the colour filter
                    view.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.red), PorterDuff.Mode.SRC_IN);

                    //Gets the TextView from the Toast so it can be edited
                    TextView text = view.findViewById(android.R.id.message);
                    text.setTextColor(ContextCompat.getColor(context, R.color.white));

                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 15);
                    toast.show();
                } catch (Exception exception) {
                    Network network = cm.getActiveNetwork();
                    if (network == null) return false;
                    NetworkCapabilities networkCapabilities = cm.getNetworkCapabilities(network);
                    return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
                }
                return false;
            }
        }
        return false;
    }
}
