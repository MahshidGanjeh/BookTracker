package com.example.booktracker.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.booktracker.Database.BookDatabase;
import com.example.booktracker.Networking.Book;
import com.example.booktracker.R;

import java.util.ArrayList;

public class ToReadBookAdapter extends RecyclerView.Adapter<ToReadBookAdapter.mViewHolder> {

    private ArrayList<Book> mBookList;
    private LayoutInflater inflater;
    private Context mContext;

    public ToReadBookAdapter() {

    }

    public ToReadBookAdapter(ArrayList<Book> mBookArrayList, Context mContext) {

        this.mBookList = mBookArrayList;
        this.inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View resultView = inflater.inflate(R.layout.toread_book_item, parent, false);

        mViewHolder viewHolder = new mViewHolder(resultView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, final int position) {

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

        holder.addToReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDatabase db = new BookDatabase(mContext);
                db.updateStatus(mBookList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder {

        private TextView bookTitle;
        private TextView bookAuthor;
        private ImageView bookCoverImage;
        TextView addToReading;
        // private ImageView addToBookshelf;

        public mViewHolder(View itemView) {
            super(itemView);
            bookTitle = (TextView) itemView.findViewById(R.id.book_title_textview);
            bookAuthor = (TextView) itemView.findViewById(R.id.book_author_textview);
            bookCoverImage = (ImageView) itemView.findViewById(R.id.book_cover_imageview);
            addToReading = (TextView) itemView.findViewById(R.id.add_to_reading_textview);

            //title.setTypeface(type);

        }
    }
}


