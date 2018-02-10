package com.example.booktracker;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.booktracker.Database.Database;
import com.example.booktracker.Networking.Book;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by Hp on 1/8/2018.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.myViewHolder> {

    private ArrayList<Book> BookList;
    private LayoutInflater inflater;
    private Context context;
    Typeface type;

    public BookAdapter() {

    }

    public BookAdapter(ArrayList<Book> aBookList, Context context) {

        this.BookList = aBookList;
        inflater = LayoutInflater.from(context);
        this.context = context;

    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View resultView = inflater.inflate(R.layout.book_item, parent, false);
        myViewHolder myViewHolder = new myViewHolder(resultView);

        //fonts
       // type = Typeface.createFromAsset(context.getAssets(), "font/DancingScript-Regular.ttf");


        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        for (int i = 0; i < BookList.size(); i++) {
            holder.title.setText(BookList.get(position).getTitle());
            holder.author.setText(BookList.get(position).getAuthor());
            //Loading images using Glide Library
            //we pass the book_cover_id to do so
            Glide.with(context)
                    .load("http://covers.openlibrary.org/b/id/" + BookList.get(position).getCoverImgUrl() + "-M.jpg")
                    .centerCrop()
                    .into(holder.coverImage);
            //holder.coverImage.setImageResource(mBookList.get(i).getCoverImgUrl());
        }

    }

    @Override
    public int getItemCount() {
        return BookList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView author;
        private ImageView coverImage;
        private ImageView addToBookshelf;

        public myViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.book_title_tv);
            author = (TextView) itemView.findViewById(R.id.book_author_tv);
            coverImage = (ImageView) itemView.findViewById(R.id.book_cover_iv);
            addToBookshelf = (ImageView) itemView.findViewById(R.id.add_to_bookshelf_imgbtn);

            //title.setTypeface(type);
            addToBookshelf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Database db = new Database(context);
                    Log.d("insertt", String.valueOf(db.insertBookToDb(BookList.get(0))));
                    Toast.makeText(context, "imgbutton is clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
