package com.hamdi.hasan.hi_news.Model.DB_Provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Owner on 3/25/2018.
 */

public class news_provider extends ContentProvider {

    DatabaseHelper DB_Helper;
    // Define final integer constants for the directory of tasks and a single item.
    // It's convention to use 100, 200, 300, etc for directories,
    // and related ints (101, 102, ..) for items in that directory.

    public static final int NEWS = 100;
    public static final int NEWS_WITH_ID = 101;

    // Declare a static variable for the Uri matcher that you construct
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {

        // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        /*
          All paths added to the UriMatcher have a corresponding int.
          For each kind of uri you may want to access, add the corresponding match with addURI.
          The two calls below add matches for the task directory and a single item by ID.
         */
        uriMatcher.addURI(DB_Contract.AUTHORITY, DB_Contract.PATH_TASKS, NEWS);
        uriMatcher.addURI(DB_Contract.AUTHORITY, DB_Contract.PATH_TASKS + "/#", NEWS_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {

        //initialize a TaskDbhelper on startup & getContext()
        Context context = getContext();
        DB_Helper = new DatabaseHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection
            , @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        // Get access to underlying database (read-only for query)
        final SQLiteDatabase db = DB_Helper.getReadableDatabase();

        // Write URI match code and set a variable to return a Cursor
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        // Query for the tasks directory and write a default case
        switch (match) {
            // Query for the tasks directory
            case NEWS:
                retCursor =  db.query(DB_Contract.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Set a notification URI on the Cursor and return that Cursor
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the desired Cursor
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        //Get access to the task database (to write new data to)
        final SQLiteDatabase db = DB_Helper.getWritableDatabase();

        //Write URI matching code to identify the match for the tasks directory
        int match = sUriMatcher.match(uri);
        Uri returnUri; // URI to be returned

        switch (match) {
            case NEWS:
                // Insert new values into the database
                // Inserting values into tasks table
                long id = db.insert(DB_Contract.TABLE_NAME, null, contentValues);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(DB_Contract.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            // Set the value for the returnedUri and write the default case for unknown URI's
            // Default case throws an UnsupportedOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        //  Notify the resolver if the uri has been changed, and return the newly inserted URI
        getContext().getContentResolver().notifyChange(uri, null);



        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        // COMPLETED (1) Get access to the database and write URI matching code to recognize a single item
        final SQLiteDatabase db = DB_Helper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        // Keep track of the number of deleted tasks
        int tasksDeleted; // starts as 0

        // Write the code to delete a single row of data
        switch (match) {
            // Handle the single item case, recognized by the ID included in the URI path
            case NEWS:
                // Use selections/selectionArgs to filter for this ID
                tasksDeleted = db.delete(DB_Contract.TABLE_NAME, s, strings);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver of a change and return the number of items deleted
        if (tasksDeleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of tasks deleted
        return tasksDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
