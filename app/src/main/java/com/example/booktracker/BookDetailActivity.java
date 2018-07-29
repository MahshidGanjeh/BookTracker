package com.example.booktracker;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.booktracker.Database.BookDatabase;
import com.example.booktracker.Networking.Book;
import com.example.booktracker.Networking.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class BookDetailActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    TextView title;
    TextView author;
    TextView t;
    ImageView coverImage;
    WebView webView;
    String aboutTheBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        //setting the actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        floatingActionButton = (FloatingActionButton)
                findViewById(R.id.add_to_bookshelf_floatingActionbButtton);
        title = (TextView) findViewById(R.id.book_title_txtv);
        author = (TextView) findViewById(R.id.book_author_txtv);
        coverImage = (ImageView) findViewById(R.id.book_cover_imgv);
        t = (TextView) findViewById(R.id.about_book_tv);
        //webView = (WebView) findViewById(R.id.webView);


        // webView.loadUrl("https://openlibrary.org/books/OL3418595M/Alice's_adventures_in_Wonderland");
        StringRequest stringRequest = new StringRequest("https://openlibrary.org/api/books?bibkeys=OLID:OL3418595M&jscmd=details&format=json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            aboutTheBook = new JSONObject(response).getJSONObject("OLID:OL3418595M")
                                    .getJSONObject("details").getJSONObject("description").getString("value");
                            t.setText(aboutTheBook);
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
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest, "gags");

        //to set the contents of the view we get the intent's bundle and get the info of the books
        title.setText(getIntent().getExtras().getString("T"));
        author.setText(getIntent().getExtras().getString("A"));
        Glide.with(getApplicationContext())
                .load("http://covers.openlibrary.org/b/id/" + getIntent().getExtras().getInt("I") + "-M.jpg")
                .centerCrop()
                .into(coverImage);

        Log.d("sss", aboutTheBook +"D");




        //Button
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDatabase db = new BookDatabase(getApplicationContext());
                db.insertBookToDb(new Book(getIntent().getExtras().getString("T"),
                        getIntent().getExtras().getString("A"),
                        getIntent().getExtras().getInt("I")));
                Toast.makeText(getApplicationContext(), getIntent().getExtras().getString("T") + " " +
                        getString(R.string.toast_of_add), Toast.LENGTH_SHORT).show();
                db.close();
            }
        });
    }
}


