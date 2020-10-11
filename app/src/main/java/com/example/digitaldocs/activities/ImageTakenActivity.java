package com.example.digitaldocs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.digitaldocs.R;

public class ImageTakenActivity extends AppCompatActivity {

    private TextView totalPriceText;
    private TextView abn;
    private TextView companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_image_taken);
        totalPriceText = findViewById(R.id.totalPrice);
        abn = findViewById(R.id.abn);
        companyName = findViewById(R.id.companyName);
        Intent intent1 = getIntent();
        String totalPrice = intent1.getExtras().getString("total");
        totalPriceText.setText("Total Price of all Items "+"$"+totalPrice);
        abn.setText("The ABN of this reciept is: " +" " +intent1.getExtras().getString("abn"));

        String comp = intent1.getExtras().getString("company");

        if (intent1.getExtras().get("abn").equals("Algorithm could not detect a valid ABN")) {
            String reciept = "This reciept has no ABN";
            companyName.setText(reciept);
        } else {
            companyName.setText("Products have been purchased from: " + intent1.getExtras().getString("company"));
        }
    }

}