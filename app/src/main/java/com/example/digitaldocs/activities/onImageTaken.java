package com.example.digitaldocs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.digitaldocs.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class onImageTaken extends AppCompatActivity {

    private View mContentView;
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.

    private View mControlsView;


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */

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

        if(intent1.getExtras().get("abn").equals("Algorithm could not detect a valid ABN"))
        {
            String reciept = "This reciept has no ABN";
            companyName.setText(reciept);
        }
        else {

            companyName.setText("Products have been purchased from: " + intent1.getExtras().getString("company"));


        }




        //mControlsView = findViewById(R.id.fullscreen_content_controls);
     //   mContentView = findViewById(R.id.fullscreen_content);
    }


}