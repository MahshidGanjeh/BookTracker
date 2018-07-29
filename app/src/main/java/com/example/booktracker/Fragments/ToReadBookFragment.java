package com.example.booktracker.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.booktracker.Adapters.ToReadBookAdapter;
import com.example.booktracker.Database.BookDatabase;
import com.example.booktracker.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToReadBookFragment extends Fragment {

    public ToReadBookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View resultView = inflater.inflate(R.layout.fragment_pager, container, false);

        BookDatabase db = new BookDatabase(getContext());

        if (db.selectToReadBooks() != null) {

            ToReadBookAdapter readingBookAdapter = new ToReadBookAdapter(db.selectToReadBooks(), getContext());
            RecyclerView recyclerView = (RecyclerView) resultView.findViewById(R.id.reading_book_recycler_view);
            recyclerView.setAdapter(readingBookAdapter);
            recyclerView.setLayoutManager(
                    new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        }
        return resultView;
    }

}
