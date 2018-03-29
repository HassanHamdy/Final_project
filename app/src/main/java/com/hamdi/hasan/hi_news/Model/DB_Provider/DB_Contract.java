package com.hamdi.hasan.hi_news.Model.DB_Provider;

import android.net.Uri;
import android.provider.BaseColumns;



public class DB_Contract {

    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "news_db";

    // Database table & columns
    public static final String TABLE_NAME = "favouriteNews";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NEWS_TITLE = "title";
    public static final String COLUMN_NEWS_DESCRIPTION = "description";
    public static final String COLUMN_NEWS_IMAGE = "image";
    public static final String COLUMN_NEWS_SOURCE = "source";
    public static final String COLUMN_NEWS_PUBLISH_AT = "publish_at";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NEWS_TITLE + " TEXT,"
                    + COLUMN_NEWS_SOURCE + " TEXT,"
                    + COLUMN_NEWS_PUBLISH_AT + " TEXT,"
                    + COLUMN_NEWS_DESCRIPTION + " TEXT,"
                    + COLUMN_NEWS_IMAGE + " TEXT"
                    + ")";

    /* Add content provider constants to the Contract
     Clients need to know how to access the news data:
        1) Content authority,
        2) Base content URI,
        3) Path(s) to the news directory
        4) Content URI for data in the table
      */

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.hamdi.hasan.hi_news";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_TASKS = "news";

    // TaskEntry content URI = base content URI + path
    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

}
