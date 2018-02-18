package com.example.booktracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.booktracker.Networking.Book;
import com.example.booktracker.Networking.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements
        SharedPreferences.OnSharedPreferenceChangeListener, BookAdapter.AdapterLister {

    private ArrayList<Book> mBookList = new ArrayList<>();
    private ProgressBar mProgressBar;

    public static String category = "love";
    public static String URL = "http://openlibrary.org/subjects/" + category + ".json?limit=100";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View resultView = inflater.inflate(R.layout.fragment_home, container, false);

        mProgressBar = (ProgressBar) resultView.findViewById(R.id.progress_bar);


        //Setting Up SharedPreference in order to update list based on user favorite genre
        SharedPreferences sharedPreferences = PreferenceManager.
                getDefaultSharedPreferences(getContext());

        category = sharedPreferences.getString("favorite_category_listPref", "");
        URL = "http://openlibrary.org/subjects/" + category + ".json?limit=100";
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);


        mProgressBar.setVisibility(View.VISIBLE);

        //Fetching data using Volley
        StringRequest strReq = new StringRequest(URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //XmlToJson xmlToJson = new XmlToJson.Builder(response).build();
                //convert to a Json String
                //String jsonString = xmlToJson.toString();
                // OR convert to a formatted Json String (with indent & line breaks)
                //String formattedResultJson = ;

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray works = jsonObject.getJSONArray("works");

                    Book[] books = new Book[99];

                    for (int i = 0; i < 90; i++) {
                        if ((Object) works.getJSONObject(i).getString("cover_id") == "null") {
                            continue;
                        } else {
                            books[i] = new Book(works.getJSONObject(i).getString("title"),
                                    works.getJSONObject(i).getJSONArray("authors").getJSONObject(0).getString("name"),
                                    works.getJSONObject(i).getInt("cover_id"));
                            mBookList.add(books[i]);
                        }
                    }

                    BookAdapter adapter = new BookAdapter(mBookList, getContext(), new BookAdapter.AdapterLister() {
                        @Override
                        public void handler(int position) {
                            Intent intentToBookDetailActivity = new Intent(getActivity(), BookDetailActivity.class);
                            startActivity(intentToBookDetailActivity);
                        }
                    });

                    //BookAdapter adapter = new BookAdapter((mBookList, getContext() , );
                    RecyclerView recyclerView = (RecyclerView) resultView.findViewById(R.id.rv);
                    //recyclerView.addOnItemTouchListener(new BookAdapter());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(
                            new GridLayoutManager(getContext(), 2));

                    mProgressBar.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VolleyError is", "Error: " + error.getMessage());
            }
        });

        // Adding String request to request queue
        VolleySingleton.getInstance(getContext()).
                addToRequestQueue(strReq, "dd");

        return resultView;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    @Override
    public void handler(int position) {


    }
}
