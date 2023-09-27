package com.example.slide6;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

// Define the MainActivity class and implement the AdapterView.OnItemSelectedListener interface
public class SecondActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    // Declare a TextView to display the selected item
    TextView selection;

    // Define an array of items for the Spinner
    String[] items = {"Android", "IPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X"};

    // onCreate method, called when the activity is created
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the layout for this activity based on the XML layout file
        setContentView(R.layout.activity_second);

        // Initialize the 'selection' TextView by finding it in the layout XML with its ID
        selection = (TextView) findViewById(R.id.selection);

        // Set the default selection of the 'selection' TextView
        selection.setText(items[0]);

        // Initialize the Spinner by finding it in the layout XML with its ID
        Spinner spin = (Spinner) findViewById(R.id.spinner);

        // Set an OnItemSelectedListener for the Spinner
        spin.setOnItemSelectedListener(this);

        // Create an ArrayAdapter to populate the Spinner with items
        ArrayAdapter<String> aa = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                items);

        // Set the dropdown view resource for the ArrayAdapter to use a simple dropdown layout
        aa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter as the adapter for the Spinner
        spin.setAdapter(aa);

        View mainLayout = findViewById(R.id.myLinearLayout);

        // Set the OnClickListener for the main layout
        mainLayout.setOnClickListener(this);

        // Unrepresented the Spinner when the main layout is clicked
        mainLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //set null selection
                spin.setSelection(0);
            }
        });


    }

    // onItemSelected method, called when an item in the Spinner is selected
    public void onItemSelected(AdapterView<?> parent,
                               View v, int position, long id) {
        // Update the 'selection' TextView with the selected item from the Spinner
        selection.setText(items[position]);
    }

    // onNothingSelected method, called when no item is selected in the Spinner
    public void onNothingSelected(AdapterView<?> parent) {
        // Clear the text in the 'selection' TextView
        selection.setText(items[0]);
    }

    // onClick method, called when the main layout is clicked
    public void onClick(View v) {
        // TODO Auto-generated method stub
        selection.setText(items[0]);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
