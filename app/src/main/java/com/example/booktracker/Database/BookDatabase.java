package com.example.booktracker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.booktracker.Networking.Book;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Hp on 2/6/2018.
 */

public class BookDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "book.db";
    public static final int DATABASE_VERSION = 6;

    public BookDatabase(Context context) {

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

    //when user clicks the add(floating action button) button ,whole information of the book
    //will be added to book table of database and
    //also it will be added to read table with the status of "ToRead"
    public boolean insertBookToDb(Book book) {

        SQLiteDatabase db = this.getWritableDatabase();

        boolean successfulInsert;
        int id = 0;
        ContentValues contentValues = new ContentValues();
        ContentValues cv = new ContentValues();

        contentValues.put(BookContract.BookContent.COLUMN_TITLE, book.getTitle());
        contentValues.put(BookContract.BookContent.COLUMN_AUTHOR, book.getAuthor());
        contentValues.put(BookContract.BookContent.COLUMN_COVERID, book.getCoverImgUrl());


        db.insert(BookContract.BookContent.TABLE1_NAME, null, contentValues);

        //get the id of the inserted book in order to add it to the read table
        String rawQuery2 = " select " + BookContract.BookContent.COLUMN_BID +
                " from " + BookContract.BookContent.TABLE1_NAME + " where "
                + BookContract.BookContent.TABLE1_NAME + "." + BookContract.BookContent.COLUMN_TITLE + " = " +
                " ' " + book.getTitle() + " ' ";

        Cursor cursor = db.rawQuery(rawQuery2, null);

        if (cursor.moveToFirst()) {
            id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(BookContract.BookContent.COLUMN_BID)));
        }

        //user id
        cv.put(BookContract.BookContent.COLUMN_RCID, 1);
        cv.put(BookContract.BookContent.COLUMN_RBID, id);
        cv.put(BookContract.BookContent.COLUMN_STATUS, "ToRead");

        successfulInsert = db.insert(BookContract.BookContent.TABlE3_NAME, null, cv) > 0;

        db.close();

        return successfulInsert;
    }

    //if user clicks "start reading" button , the book status will be changed to READING
    //and the start date of reading will be saved

    public void updateStatus(Book book) {

        int id = 0;
        SQLiteDatabase database = this.getWritableDatabase();

        //get the id of the inserted book in order to update it to the read table
        String rawQuery2 = " select " + BookContract.BookContent.COLUMN_BID +
                " from " + BookContract.BookContent.TABLE1_NAME + " where "
                + BookContract.BookContent.TABLE1_NAME + "." + BookContract.BookContent.COLUMN_TITLE + " = " +
                "'" + book.getTitle() + "'";

        Cursor cursor = database.rawQuery(rawQuery2, null);

        if (cursor.moveToFirst()) {
            id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(BookContract.BookContent.COLUMN_BID)));
        }

        ContentValues cv = new ContentValues();

        Date c = Calendar.getInstance().getTime();
       // Log.d("DATE", "Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        Log.d("DATE", "Current time => "+df.format(c));
        cv.put(BookContract.BookContent.COLUMN_STATUS, "Reading");
        cv.put(BookContract.BookContent.COLUMN_STARTDATE, formattedDate);

        //update table read set status = 'Reading' where id = folan
        database.update(BookContract.BookContent.TABlE3_NAME, cv,
                BookContract.BookContent.COLUMN_RBID + " = " + id, null);
    }

    public ArrayList<Book> selectReadingBooks() {

        SQLiteDatabase db = this.getWritableDatabase();

        //select title, author, coverId from book inner join read on book.id = read.bid where status = Reading
        String rawQuery = " select " + BookContract.BookContent.COLUMN_TITLE + ", " +
                BookContract.BookContent.COLUMN_AUTHOR + ", " + BookContract.BookContent.COLUMN_COVERID +
                " from " + BookContract.BookContent.TABLE1_NAME + " inner join " + BookContract.BookContent.TABlE3_NAME
                + " on " + BookContract.BookContent.TABLE1_NAME + "." + BookContract.BookContent.COLUMN_BID +
                " = " + BookContract.BookContent.TABlE3_NAME + "." + BookContract.BookContent.COLUMN_RBID
                + " where " + BookContract.BookContent.COLUMN_STATUS + " = 'Reading' ";

        Cursor cursor = db.rawQuery(rawQuery, null);

        int numberOfRows = cursor.getCount();

        Book[] books = new Book[numberOfRows];
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
            while (cursor.moveToNext() && i != numberOfRows);
        }

        cursor.close();
        db.close();

        return b;
    }

    public boolean deleteBook(String title) {

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(BookContract.BookContent.TABLE1_NAME,
                BookContract.BookContent.COLUMN_TITLE + " = " + title, null) > 0;
        //return db.delete(BookContract.BookContent.TABLE1_NAME,BookContract.BookContent.COLUMN_TITLE  + "=" + title , null) > 0;

    }

    //for showing books in the bookshelf (ToRead Tab) we (Query) select the database to get the books information
    //that have been inserted before

    public ArrayList<Book> selectToReadBooks() {

        SQLiteDatabase db = this.getWritableDatabase();

        //select title, author, coverId from book inner join read on book.id = read.bid where status = ToRead
        String rawQuery = " select " + BookContract.BookContent.COLUMN_TITLE + ", " +
                BookContract.BookContent.COLUMN_AUTHOR + ", " + BookContract.BookContent.COLUMN_COVERID +
                " from " + BookContract.BookContent.TABLE1_NAME + " inner join " + BookContract.BookContent.TABlE3_NAME
                + " on " + BookContract.BookContent.TABLE1_NAME + "." + BookContract.BookContent.COLUMN_BID +
                " = " + BookContract.BookContent.TABlE3_NAME + "." + BookContract.BookContent.COLUMN_RBID
                + " where " + BookContract.BookContent.COLUMN_STATUS + " = 'ToRead' ";

        Cursor cursor = db.rawQuery(rawQuery, null);

        int numberOfRows = cursor.getCount();

        Log.d("numberOfRows", String.valueOf(numberOfRows));

        Book[] books = new Book[numberOfRows];
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
            while (cursor.moveToNext() && i != numberOfRows);
        }

        cursor.close();
        db.close();

        return b;
    }
}

