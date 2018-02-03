package com.example.booktracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

/*
        ImageView imageView = (ImageView) view.findViewById(R.id.test);

        Glide.with(getContext())
                .load("http://covers.openlibrary.org/b/id/1861101-M.jpg")
                .centerCrop()
                .into(imageView);
*/
        return view;
    }
}
