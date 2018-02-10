package com.example.booktracker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.booktracker.Networking.Book;

/**
 * Created by Hp on 2/6/2018.
 */

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "book.db";
    public static final int DATABASE_VERSION = 1;

    public Database(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_BOOK_TABLE =
                " create table if not exist " + BookContract.BookContent.TABLE1_NAME + " ("

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
                " create table if not exist" + BookContract.BookContent.TABlE2_NAME + " ("

                        + BookContract.BookContent.COLUMN_UID + " integer primary key autoincrement, "
                        + BookContract.BookContent.COLUMN_NAME + " nvarchar(20), "
                        + BookContract.BookContent.COLUMN_EMAIL + " nvarchar(40), "
                        + BookContract.BookContent.COLUMN_TOTALHOURS + " nvarchar(8), "
                        + BookContract.BookContent.COLUMN_TOTALBOOKS + " nvarchar(8)"
                        + ");";

        String SQL_CREATE_READ_TABLE =
                " create table if not exist" + BookContract.BookContent.TABlE3_NAME + " ("

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
        //db.execSQL("DROP TABLE IF EXISTS " + BookContract.BookContent.TABLE2_NAME);
        //db.execSQL("DROP TABLE IF EXISTS " + BookContract.BookContent.TABLE3_NAME);
        onCreate(db);
    }

    public boolean insertBookToDb(Book book) {

        Log.d("place", "in the insertBookMethod");
        boolean successfulInsert;
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", book.getTitle());
        contentValues.put("Author", book.getAuthor());
        contentValues.put("CoverId", book.getCoverImgUrl());
        //contentValues.put("NumberOfPage", book.getNumberOfPage());

        SQLiteDatabase db = this.getWritableDatabase();
        successfulInsert = db.insert(BookContract.BookContent.TABLE1_NAME, null, contentValues) > 0;

        db.close();

        return successfulInsert;
    }
}
