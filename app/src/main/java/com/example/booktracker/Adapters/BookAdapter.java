package com.example.booktracker.Adapters;

import android.content.Context;
import android.graphics.Typeface;
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
 * Created by Hp on 1/8/2018.
 */

public class BookAdapter extends
        RecyclerView.Adapter<BookAdapter.myViewHolder> {

    private ArrayList<Book> BookList;
    private LayoutInflater inflater;
    private Context context;
    private AdapterListener mAdapterListener;
    Typeface type;


    public interface AdapterListener {

        public void handler(int position);
    }


    public BookAdapter() {

    }

    public BookAdapter(ArrayList<Book> aBookList, Context context, AdapterListener mAdapterListener) {

        this.mAdapterListener = mAdapterListener;
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
    public void onBindViewHolder(myViewHolder holder, final int position) {

        for (int i = 0; i < BookList.size(); i++) {
            holder.title.setText(BookList.get(position).getTitle());
            holder.author.setText(BookList.get(position).getAuthor());

            //Loading images using Glide Library
            //we pass the book_cover_id to do so
            Glide.with(context)
                    .load("http://covers.openlibrary.org/b/id/" + BookList.get(position).getCoverImgUrl() + "-M.jpg")
                    .centerCrop()
                    .into(holder.coverImage);
        }
    }

    @Override
    public int getItemCount() {
        return BookList.size();
    }
            /*

      holder.addToBookshelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BookDatabase db = new BookDatabase(context);

                db.insertBookToDb(BookList.get(position));

                Toast.makeText(context, BookList.get(position).getTitle() + " " +
                        "has been added to bookshelf successfully", Toast.LENGTH_SHORT).show();
                db.close();

                // Toast.makeText(context, db.selectBook()[5], Toast.LENGTH_SHORT).show();
            }
        });


        holder.coverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db = new Database(context);

                if (db.selectBook() != null) {

                    Log.d("total", String.valueOf(BookList.size()));
                    db.deleteBook(BookList.get(position).getTitle());

                    db.close();
                }
            }
        });

    }
*/


    public class myViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView author;
        private ImageView coverImage;

        public myViewHolder(View itemView) {

            super(itemView);
            title = (TextView) itemView.findViewById(R.id.book_title_tv);
            author = (TextView) itemView.findViewById(R.id.book_author_tv);
            coverImage = (ImageView) itemView.findViewById(R.id.book_cover_iv);

            //set the listener fo each viewHolder
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getPosition();
                    mAdapterListener.handler(position);
                }
            });
            //title.setTypeface(type);
        }
    }
}
