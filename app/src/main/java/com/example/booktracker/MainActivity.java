package com.example.booktracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

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
import org.json.XML;

import java.lang.reflect.Field;
import java.util.ArrayList;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

//we implement onSharedPref because we want whenever a pref value changes ,
//the booklist automatically be updated
public class MainActivity extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static String category = "";
    public static String URL = "http://openlibrary.org/subjects/" + category + ".json?limit=100";
    public static String imageUrl = "http://covers.openlibrary.org/b/id/1861101-S.jpg";

    //https://www.goodreads.com/search.xml?" +
    //"key=GWMAmEgkrVLUHvbMS1oTpQ&q=city%27s

    private ArrayList<Book> mBookList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Home"); //this will set the title of the action bar
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, homeFragment, "Home");
        fragmentTransaction.commit();


        //Setting Up Bottom Navigation
        BottomNavigationView bottomNavigationView =
                (BottomNavigationView) findViewById(R.id.bottom_navigation);

        removeShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.item_home:
                                setTitle("Home"); //this will set the title of the action bar
                                HomeFragment homeFragment = new HomeFragment();
                                FragmentManager fragmentManager1 = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                                fragmentTransaction1.replace(R.id.fragment_container, homeFragment, "TAG");
                                fragmentTransaction1.commit();
                                break;
                            case R.id.item_bookshelf:
                                setTitle("Bookshelf"); //this will set the title of the action bar
                                BookShelfFragment bookShelfFragment = new BookShelfFragment();
                                FragmentManager fragmentManager2 = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                fragmentTransaction2.replace(R.id.fragment_container, bookShelfFragment, "TAG");
                                fragmentTransaction2.commit();
                                break;
                            case R.id.item_notebook:
                                setTitle("NoteBook"); //this will set the title of the action bar
                                NoteFragment noteFragment = new NoteFragment();
                                FragmentManager fragmentManager3 = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                                fragmentTransaction3.replace(R.id.fragment_container, noteFragment, "TAG");
                                fragmentTransaction3.commit();
                                break;
                            case R.id.item_dashboard:
                                setTitle("Dashboard"); //this will set the title of the action bar
                                DashBoardFragment dFragment = new DashBoardFragment();
                                FragmentManager fragmentManager4 = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction4 = fragmentManager4.beginTransaction();
                                fragmentTransaction4.replace(R.id.fragment_container, dFragment, "TAG");
                                fragmentTransaction4.commit();
                                break;
                        }
                        return true;
                    }
                }
        );

    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.setting_item) {
            Intent intentToSetting = new Intent(this, SettingActivity.class);
            startActivity(intentToSetting);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //A method that fix the size of each item in the bottom navigation view
    public static void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BottomNav", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BottomNav", "Unable to change value of shift mode", e);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        String value = sharedPreferences.getString(key, "");

        if (key == "favorite_category_listPref") {
            category = value;
            URL = "http://openlibrary.org/subjects/" + category + ".json?limit=100";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        Log.d("destroy", "onDestroy is called");
    }

    @Override
    protected void onStart() {
        Log.d("start", "onStart is called");
        super.onStart();
    }

    @Override
    protected void onPostResume() {
        Log.d("resume", "onResume is called");
        super.onPostResume();
    }
}

