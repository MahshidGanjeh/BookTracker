package com.example.booktracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.booktracker.Database.Database;


/**
 * A simple {@link Fragment} subclass.
 */
public class PagerFragment extends Fragment {


    public PagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View resultView = inflater.inflate(R.layout.fragment_pager, container, false);

        Database db = new Database(getContext());

        if (db.selectToReadBook() != null) {

            BookAdapter readingBookAdapter = new BookAdapter(db.selectToReadBook(), getContext());
            RecyclerView recyclerView = (RecyclerView) resultView.findViewById(R.id.reading_book_recycler_view);
            recyclerView.setAdapter(readingBookAdapter);
            recyclerView.setLayoutManager(
                    new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        }
        return resultView;
    }

}
