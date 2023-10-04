package com.example.slide10;

import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText;
    private Button addButton, deleteButton;
    private ListView contactListView;
    private ArrayAdapter<String> adapter;
    private static final Uri CONTENT_URI = ContactProvider.CONTENT_URI; // Sử dụng CONTENT_URI từ ContactProvider

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(
                this,
                new String[]{android.Manifest.permission.WRITE_CONTACTS},
                PackageManager.PERMISSION_GRANTED);

        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);
        contactListView = findViewById(R.id.contactListView);

        // Thiết lập adapter cho ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        contactListView.setAdapter(adapter);

        // Hiển thị danh bạ khi ứng dụng khởi động
        displayContacts();

        // Xử lý sự kiện khi nhấn nút "Thêm"
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();

                if (!name.isEmpty() && !phone.isEmpty()) {
                    addContact(name, phone);
                    displayContacts();
                    clearFields();
                } else {
                    Toast.makeText(MainActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xử lý sự kiện khi nhấn nút "Xóa"
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                deleteContact(name);
                displayContacts();
                clearFields();
            }
        });

        // Xử lý sự kiện khi chọn một liên hệ trong danh bạ
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedContact = adapter.getItem(position);
                String[] parts = selectedContact.split(": ");
                if (parts.length == 2) {
                    nameEditText.setText(parts[0]);
                    phoneEditText.setText(parts[1]);
                }
            }
        });
    }

    // Hiển thị danh bạ trong ListView
    private void displayContacts() {
        adapter.clear();

        // Truy vấn danh bạ từ Content Provider
        Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                adapter.add(name + ": " + phone);
            }
            cursor.close();
        }
    }

    // Thêm một liên hệ mới vào danh bạ
    private void addContact(String name, String phone) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_PHONE, phone);


        getContentResolver().insert(CONTENT_URI, values);
    }

    // Xóa một liên hệ khỏi danh bạ
    private void deleteContact(String name) {
        getContentResolver().delete(CONTENT_URI, DatabaseHelper.COLUMN_NAME + "=?", new String[]{name});
    }

    // Xóa nội dung trong các trường EditText
    private void clearFields() {
        nameEditText.setText("");
        phoneEditText.setText("");
    }
}



