package com.example.booktracker;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.booktracker.Networking.VolleySingleton;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "https://www.goodreads.com/search.xml" +
            "?key=GWMAmEgkrVLUHvbMS1oTpQ&q=City%27s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fetching data using Volley
        StringRequest strReq = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response is", response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error is", "Error: " + error.getMessage());
            }
        });
        // Adding String request to request queue
        VolleySingleton.getInstance(getApplicationContext()).
                addToRequestQueue(strReq, "dd");

        //Setting Up Bottom Navigation
        BottomNavigationView bottomNavigationView =
                (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_home:
                        Toast.makeText(getApplicationContext(), "This is Home", Toast.LENGTH_LONG).show();
                        break;
                }
                return true;
            }
        });
    }
}
