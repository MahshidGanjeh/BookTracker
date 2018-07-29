package com.example.booktracker.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.booktracker.Networking.Book;
import com.example.booktracker.R;

import java.util.ArrayList;

/**
 * Created by Hp on 3/6/2018.
 */

public class ReadingBookAdapter extends RecyclerView.Adapter<ReadingBookAdapter.myViewHolder> {

    ArrayList<Book> mBookList;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public ReadingBookAdapter(ArrayList<Book> mBookList, Context context) {
        this.mBookList = mBookList;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View resultView = mLayoutInflater.inflate(R.layout.reading_book_item, parent, false);
        myViewHolder myViewHolder = new myViewHolder(resultView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        for (int i = 0; i < mBookList.size(); i++) {
            holder.bookTitle.setText(mBookList.get(position).getTitle());
            holder.bookAuthor.setText(mBookList.get(position).getAuthor());
            //Loading images using Glide Library
            //we pass the book_cover_id to do so
            Glide.with(mContext)
                    .load("http://covers.openlibrary.org/b/id/" + mBookList.get(position).getCoverImgUrl() + "-M.jpg")
                    .centerCrop()
                    .into(holder.bookCoverImage);
        }
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView bookTitle;
        private TextView bookAuthor;
        private ImageView bookCoverImage;

        public myViewHolder(View itemView) {
            super(itemView);
            bookTitle = (TextView) itemView.findViewById(R.id.book_title_textview);
            bookAuthor = (TextView) itemView.findViewById(R.id.book_author_textview);
            bookCoverImage = (ImageView) itemView.findViewById(R.id.book_cover_imageview);

            //title.setTypeface(type);
        }
    }
}
