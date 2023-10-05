package com.example.slide10;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class ContactProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.slide10.ContactProvider";
    private static final String CONTACTS_PATH = "contacts";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTACTS_PATH);

    // Mã trận khớp URI
    private static final int CONTACTS = 1;
    private static final int CONTACT_ID = 2;

    private SQLiteOpenHelper dbHelper;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, CONTACTS_PATH, CONTACTS);
        uriMatcher.addURI(AUTHORITY, CONTACTS_PATH + "/#", CONTACT_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.query(DatabaseHelper.TABLE_CONTACTS, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.insert(DatabaseHelper.TABLE_CONTACTS, null, values);
        if (rowId > 0) {
            return Uri.withAppendedPath(uri, String.valueOf(rowId));
        } else {
            throw new SQLException("Failed to insert row into " + uri);
        }
    }

    //Bulk insert
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = 0;
        db.beginTransaction();
        try {
            for (ContentValues value : values) {
                long rowId = db.insert(DatabaseHelper.TABLE_CONTACTS, null, value);
                if (rowId > 0) {
                    count++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(DatabaseHelper.TABLE_CONTACTS, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.update(DatabaseHelper.TABLE_CONTACTS, values, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // Đây có thể là loại MIME của dữ liệu bạn hỗ trợ, ví dụ: "vnd.android.cursor.dir/contacts"
        return null;
    }
}
