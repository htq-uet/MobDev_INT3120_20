package com.example.slide9;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Shared Preferences

        SharedPreferences settings = getSharedPreferences("settings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("silentMode", true);
        editor.putInt("version", 24);
        editor.commit();

        Button sp_button = (Button)findViewById(R.id.shared_preferences_button);
        sp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getSharedPreferences("settings", 0);
                Boolean silentMode = sharedPref.getBoolean("silentMode", false);
                Integer version = sharedPref.getInt("version", 20);

                String message = "";
                if (silentMode) {
                    message = "Chế độ im lặng đã được bật.\n";
                }

                message += "Phiên bản ứng dụng: " + version.toString();

                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        //Add new file
        Button add_button = (Button) findViewById(R.id.add_file_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo Dialog để nhập tên file và nội dung
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Nhập tên file và nội dung");

                // Sử dụng LayoutInflater để nạp giao diện từ file XML
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View dialogView = inflater.inflate(R.layout.file_dialog, null);
                builder.setView(dialogView);

                final EditText fileNameEditText = dialogView.findViewById(R.id.fileNameEditText);
                final EditText fileContentEditText = dialogView.findViewById(R.id.fileContentEditText);

                builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String filename = fileNameEditText.getText().toString();
                        String fileContents = fileContentEditText.getText().toString();

                        // Kiểm tra xem filename và fileContents có giá trị hợp lệ
                        if (!filename.isEmpty() && !fileContents.isEmpty()) {
                            FileOutputStream outputStream;
                            try {
                                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                                outputStream.write(fileContents.getBytes());
                                outputStream.close();
                                Toast.makeText(MainActivity.this, "Đã thêm file", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, "Lỗi khi thêm file", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Tên file và nội dung không được để trống", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });



        //File Input Stream

        Button fis_button = (Button) findViewById(R.id.file_input_stream_button);
        fis_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(intent, 1); // Bắt đầu hoạt động để chọn tệp
            }
        });

        //SQL Lite
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        //Insert
        Button insert_button = (Button) findViewById(R.id.insert_button);

        insert_button.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "HTWuang";
                String subtitle = "wutos1vn";
                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title);
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, subtitle);

                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
            }
        }));

        //Show
        Button show_button = (Button) findViewById(R.id.show_button);

        show_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                String[] projection = {
                        FeedReaderContract.FeedEntry._ID,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE
                };

                String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " = ?";

                String[] selectionArgs = { "HTWuang" };

                String sortOrder =
                        FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

                Cursor cursor = db.query(
                        FeedReaderContract.FeedEntry.TABLE_NAME,   // tables
                        projection,             // columns
                        selection,              // columns for the WHERE clause
                        selectionArgs,          // values for the WHERE clause
                        null,                   // group by
                        null,                   // filter by row groups
                        sortOrder               // The sort order
                );

                String item = "";

                while(cursor.moveToNext()) {
                    long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
                    String itemTitle = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE));
                    String itemSubtitle = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE));
                    item += itemId + " - " + itemTitle + " - " + itemSubtitle + "\n";
                }

                Toast.makeText(MainActivity.this, item, Toast.LENGTH_LONG).show();

            }
        });

        //Delete

        Button delete_button = (Button) findViewById(R.id.delete_button);

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define 'where' part of query.
                String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
                // Specify arguments in placeholder order.
                String[] selectionArgs = { "HTWuang" };
                // Issue SQL statement.
                int deletedRows = db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
            }
        });



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri uri = resultData.getData();

                try {
                    // Sử dụng ContentResolver để đọc nội dung tệp
                    InputStream inputStream = getContentResolver().openInputStream(uri);

                    if (inputStream != null) {
                        InputStreamReader isr = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(isr);
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                        inputStream.close();

                        String fileContent = sb.toString();

                        // Hiển thị nội dung tệp tin trong Toast
                        Toast.makeText(getApplicationContext(), "Nội dung tệp tin: " + fileContent, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Không thể đọc tệp tin", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Lỗi khi đọc tệp tin", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}