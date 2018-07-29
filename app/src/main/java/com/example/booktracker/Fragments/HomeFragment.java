package com.example.booktracker.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.booktracker.Adapters.BookAdapter;
import com.example.booktracker.BookDetailActivity;
import com.example.booktracker.Networking.Book;
import com.example.booktracker.Networking.VolleySingleton;
import com.example.booktracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements
        SharedPreferences.OnSharedPreferenceChangeListener {

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
        URL = "http://openlibrary.org/subjects/" + category + ".json?limit=500";
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

                    for (int i = 0; i < 300; i++) {
                        if (works.getJSONObject(i).getString("cover_id") == "null") {
                            continue;
                        } else {
                            mBookList.add(new Book
                                    (works.getJSONObject(i).getString("title"),
                                            works.getJSONObject(i).getJSONArray("authors")
                                                    .getJSONObject(0).getString("name"),
                                            works.getJSONObject(i).getInt("cover_id"),
                                            works.getJSONObject(i).getString("cover_edition_key")
                                    )
                            );
                        }
                    }

                    //after selecting a book in the home activity we put the detail of that
                    //book to a bundle object to send them to detail activity
                    BookAdapter adapter = new BookAdapter(mBookList, getContext(), new BookAdapter.AdapterListener() {
                        @Override
                        public void handler(int position) {

                            String title = mBookList.get(position).getTitle();
                            String author = mBookList.get(position).getAuthor();
                            int coverId = mBookList.get(position).getCoverImgUrl();
                            Bundle bundle = new Bundle();
                            bundle.putString("T", title);

                            bundle.putString("A", author);

                            bundle.putInt("I", coverId);

                            Intent intentToBookDetailActivity = new Intent(getActivity(), BookDetailActivity.class);
                            intentToBookDetailActivity.putExtras(bundle);
                            startActivity(intentToBookDetailActivity);
                        }
                    });

                    RecyclerView recyclerView = (RecyclerView) resultView.findViewById(R.id.rv);
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
    public void onSharedPreferenceChanged
            (SharedPreferences sharedPreferences, String key) {

    }


}
