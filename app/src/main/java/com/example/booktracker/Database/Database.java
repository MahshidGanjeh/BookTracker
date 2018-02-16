package com.example.booktracker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.booktracker.BookAdapter;
import com.example.booktracker.Networking.Book;

import java.util.ArrayList;

/**
 * Created by Hp on 2/6/2018.
 */

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "book.db";
    public static final int DATABASE_VERSION = 4;

    public Database(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_BOOK_TABLE =
                " create table " + BookContract.BookContent.TABLE1_NAME + " ("

                        + BookContract.BookContent.COLUMN_BID + " integer primary key autoincrement, "
                        + BookContract.BookContent.COLUMN_TITLE + " nvarchar(80), "
                        + BookContract.BookContent.COLUMN_AUTHOR + " nvarchar(80), "
                        + BookContract.BookContent.COLUMN_COVERID + " nvarchar(20), "
                        + BookContract.BookContent.COLUMN_NUMBEROFPAGE + " nvarchar(5), "
                        + BookContract.BookContent.COLUMN_ISBN + " nvarchar(10), "
                        + BookContract.BookContent.COLUMN_DETAIL + " nvarchar(3000), "
                        + BookContract.BookContent.COLUMN_FIRSTPUBLICATIONYEAR + " nvarchar(5) "
                        + ") ;";

        String SQL_CREATE_USER_TABLE =
                " create table " + BookContract.BookContent.TABlE2_NAME + " ("

                        + BookContract.BookContent.COLUMN_UID + " integer primary key autoincrement, "
                        + BookContract.BookContent.COLUMN_NAME + " nvarchar(20), "
                        + BookContract.BookContent.COLUMN_EMAIL + " nvarchar(40), "
                        + BookContract.BookContent.COLUMN_TOTALHOURS + " nvarchar(8), "
                        + BookContract.BookContent.COLUMN_TOTALBOOKS + " nvarchar(8)"
                        + ");";

        String SQL_CREATE_READ_TABLE =
                " create table " + BookContract.BookContent.TABlE3_NAME + " ("

                        + BookContract.BookContent.COLUMN_RBID + " integer, "
                        + BookContract.BookContent.COLUMN_RCID + " integer, "
                        + BookContract.BookContent.COLUMN_STATUS + " nvarchar(20), "
                        + BookContract.BookContent.COLUMN_STARTDATE + " nvarchar(20), "
                        + " foreign key ( " + BookContract.BookContent.COLUMN_RBID + " ) references " +
                        BookContract.BookContent.TABLE1_NAME + "(" + BookContract.BookContent.COLUMN_BID + " ), "
                        + " foreign key ( " + BookContract.BookContent.COLUMN_RCID + " ) references " +
                        BookContract.BookContent.TABlE2_NAME + "(" + BookContract.BookContent.COLUMN_UID + " ), "
                        + " primary key ( " + BookContract.BookContent.COLUMN_RBID + "," + BookContract.BookContent.COLUMN_RCID + " ) "
                        + ");";


        db.execSQL(SQL_CREATE_BOOK_TABLE);
        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL(SQL_CREATE_READ_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + BookContract.BookContent.TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BookContract.BookContent.TABlE2_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BookContract.BookContent.TABlE3_NAME);
        onCreate(db);
    }

    //when user clicks the add button ,whole information of the book
    //will be added to book table and
    //also it will be added to read table with the status of "ToRead"
    public boolean insertBookToDb(Book book) {

        SQLiteDatabase db = this.getWritableDatabase();

        boolean successfulInsert;

        ContentValues contentValues = new ContentValues();

        contentValues.put(BookContract.BookContent.COLUMN_TITLE, book.getTitle());
        contentValues.put(BookContract.BookContent.COLUMN_AUTHOR, book.getAuthor());
        contentValues.put(BookContract.BookContent.COLUMN_COVERID, book.getCoverImgUrl());


        db.insert(BookContract.BookContent.TABLE1_NAME, null, contentValues);

        ContentValues cv = new ContentValues();
        //user id
        cv.put(BookContract.BookContent.COLUMN_RCID, 1);
        cv.put(BookContract.BookContent.COLUMN_STATUS, "ToRead");

        successfulInsert = db.insert(BookContract.BookContent.TABlE3_NAME, null, cv) > 0;
        Log.d("hhhh", String.valueOf(successfulInsert));

        db.close();

        return successfulInsert;
    }

    //give the title of the book and it will return id of that
    public int selectBookId(String bookTitle) {

        int id = 0;

        SQLiteDatabase database = this.getWritableDatabase();

        String rawQuery2 = " select " + BookContract.BookContent.COLUMN_BID +
                " from " + BookContract.BookContent.TABLE1_NAME + " where "
                + BookContract.BookContent.COLUMN_TITLE + " = " + bookTitle;

        Cursor cursor = database.rawQuery(rawQuery2, null);

        if (cursor.moveToFirst()) {
            id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(BookContract.BookContent.COLUMN_BID)));
        }
        return id;
    }


    public boolean deleteBook(String title) {

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(BookContract.BookContent.TABLE1_NAME,
                BookContract.BookContent.COLUMN_TITLE + " = " + title, null) > 0;
        //return db.delete(BookContract.BookContent.TABLE1_NAME,BookContract.BookContent.COLUMN_TITLE  + "=" + title , null) > 0;

    }

    public void selectToReadBooks() {

        SQLiteDatabase db = this.getWritableDatabase();

        String rawQuery = " select " + BookContract.BookContent.COLUMN_RCID +
                " from " + BookContract.BookContent.TABlE3_NAME;

        Cursor cursor = db.rawQuery(rawQuery, null);

        int rowCount = cursor.getCount();

        if (cursor.moveToFirst()) {
            String n = cursor.getString(cursor.getColumnIndex(BookContract.BookContent.COLUMN_RCID));
            if (n != null) {
                Log.d("row", n);
            }
        }

    }

    public ArrayList<Book> selectToReadBook() {

        SQLiteDatabase db = this.getWritableDatabase();

        String rawQuery2 = " select " + BookContract.BookContent.COLUMN_TITLE + ", " +
                BookContract.BookContent.COLUMN_AUTHOR + ", " + BookContract.BookContent.COLUMN_COVERID + ", " +
                BookContract.BookContent.COLUMN_BID +
                " from " + BookContract.BookContent.TABLE1_NAME;

        //select title, author, coverId from book inner join read on book.id = read.bid where status = ToRead
        String rawQuery = " select " + BookContract.BookContent.COLUMN_TITLE + ", " +
                BookContract.BookContent.COLUMN_AUTHOR + ", " + BookContract.BookContent.COLUMN_COVERID +
                " from " + BookContract.BookContent.TABLE1_NAME + " inner join " + BookContract.BookContent.TABlE3_NAME
                + " on " + BookContract.BookContent.TABLE1_NAME + "." + BookContract.BookContent.COLUMN_BID +
                " = " + BookContract.BookContent.TABlE3_NAME + "." + BookContract.BookContent.COLUMN_RBID
                + " where " + BookContract.BookContent.COLUMN_STATUS + " = ToRead ";

        Cursor cursor = db.rawQuery(rawQuery2, null);

        int columnCount = cursor.getCount();

        Log.d("columnCount", String.valueOf(columnCount));

        Book[] books = new Book[columnCount];

        ArrayList<Book> b = new ArrayList<>();

        if (cursor.moveToFirst()) {

            int i = 0;
            do {
                if (cursor.getString(cursor.getColumnIndex(BookContract.BookContent.COLUMN_COVERID))
                        == "null") {
                    continue;
                } else {
                    books[i] = new Book(cursor.getString(cursor.getColumnIndex(BookContract.BookContent.COLUMN_TITLE)),
                            cursor.getString(cursor.getColumnIndex(BookContract.BookContent.COLUMN_AUTHOR)),
                            Integer.valueOf(cursor.getString(
                                    cursor.getColumnIndex(BookContract.BookContent.COLUMN_COVERID))));

                    // Log.d("ID", cursor.getString(cursor.getColumnIndex(BookContract.BookContent.COLUMN_BID)));
                }
                b.add(books[i]);
                i++;
            }
            while (cursor.moveToNext() && i != cursor.getCount());
        }

        cursor.close();
        db.close();

        return b;
    }
}

