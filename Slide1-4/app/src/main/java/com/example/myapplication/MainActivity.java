package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;

import android.view.Menu;
import android.view.View;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {

    private EditText editAmount;

    private TextView amountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Relative Layout
//        setContentView(R.layout.activity_main_relative_layout);

        //Linear Layout
//        setContentView(R.layout.activity_main_linear_layout);

        //Constraint Layout
//        setContentView(R.layout.activity_main_constraint_layout);

        //Table Layout
        setContentView(R.layout.activity_main_table_layout);

        //"More" button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        // More icon click listener
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (item.getItemId() == R.id.action_settings) {
//                    showPopupMenu(toolbar);
//                    return true;
//                }
//                return false;
//            }
//        });



        // Hide the soft keyboard when the user taps outside an EditText
        View mainLayout = findViewById(R.id.mainLayout);
        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    View focusedView = getCurrentFocus();
                    if (focusedView != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
                    }
                }
                return false;
            }
        });

        // NumberPicker setup

        NumberPicker numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setEnabled(true);
        numberPicker.setFocusable(false);
        numberPicker.setClickable(false);
        numberPicker.setFocusableInTouchMode(false);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(1000);
        numberPicker.setValue(999);
        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {

        });

        editAmount = findViewById(R.id.editTextText);
        Button donateButton = findViewById(R.id.button);
        amountTextView = findViewById(R.id.textView2);



        AtomicReference<Integer> sum = new AtomicReference<>(0);

        //Update the amount donated when the user enters a new value
        editAmount.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Get the entered amount from the EditText
                String amountStr = editAmount.getText().toString().trim();

                if (!amountStr.isEmpty()) {
                    int amount = Integer.parseInt(amountStr);

                    // Update the sum and display it
                    sum.updateAndGet(currentSum -> currentSum + amount);
                    amountTextView.setText("Total so Far: $" + sum.get());

                    // Clear the EditText
                    editAmount.setText("");

                    // Close the soft keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                } else {
                    // Handle the case where the EditText is empty or contains invalid input
                    // You can show an error message or take appropriate action here
                }

                return true; // Consume the event
            }
            return false;
        });

        //Update the amount donated when the user clicks the donate button
        donateButton.setOnClickListener(v -> {
            Integer amount = editAmount.getText().toString().isEmpty() ? 0 : Integer.parseInt(editAmount.getText().toString());
            sum.updateAndGet(v1 -> v1 + amount);
            amountTextView.setText("Total so Far  $" + sum);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

//    private void showPopupMenu(View view) {
//        PopupMenu popupMenu = new PopupMenu(this, view);
//        popupMenu.getMenuInflater().inflate(R.menu.menu_settings, popupMenu.getMenu());
//
//        // Set a click listener for the menu items
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                // Handle the settings item click here
//                if (item.getItemId() == R.id.action_settings) {
//                    // Open your settings activity or fragment here
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        popupMenu.show();
//    }
}

