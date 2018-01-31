package com.example.booktracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.booktracker.Networking.Book;

import java.util.ArrayList;

/**
 * Created by Hp on 1/8/2018.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.myViewHolder> {

    private ArrayList<Book> mBookList;
    private LayoutInflater inflater;

    public BookAdapter(ArrayList<Book> mBookList, Context context) {
        this.mBookList = mBookList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View resultView = inflater.inflate(R.layout.book_item, parent, false);
        myViewHolder myViewHolder = new myViewHolder(resultView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        for (int i = 0; i < mBookList.size(); i++) {
            holder.title.setText(mBookList.get(i).getTitle());
            holder.author.setText(mBookList.get(i).getAuthor());
            holder.coverImage.setImageResource(mBookList.get(i).getCoverImgUrl());
        }

    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView author;
        private ImageView coverImage;

        public myViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.book_title_tv);
            author = (TextView) itemView.findViewById(R.id.book_author_tv);
            coverImage = (ImageView) itemView.findViewById(R.id.book_cover_iv);
        }
    }
}
