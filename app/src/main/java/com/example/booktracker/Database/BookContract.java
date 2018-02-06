package com.example.booktracker.Database;

import android.provider.BaseColumns;

/**
 * Created by Hp on 2/4/2018.
 */

public class BookContract {

    public static final class BookContent implements BaseColumns {

        //Declaring Tables
        public static final String TABLE1_NAME = "book";
        public static final String TABlE2_NAME = "user";
        public static final String TABlE3_NAME = "read";
        public static final String TABlE4_NAME = "note";

        //Book table columns
        public static final String COLUMN_BID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_COVERID = "coverId";
        public static final String COLUMN_NUMBEROFPAGE = "numberOfPage";
        public static final String COLUMN_ISBN = "isbn";
        public static final String COLUMN_DETAIL = "detail";
        public static final String COLUMN_FIRSTPUBLICATIONYEAR = "firstPublicationYear";

        //User table columns
        public static final String COLUMN_UID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_TOTALHOURS = "totalOurs";
        public static final String COLUMN_TOTALBOOKS = "totalBooks";

        //Read table columns
        public static final String COLUMN_RID = "id";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_STARTDATE = "startDate";


    }
}
