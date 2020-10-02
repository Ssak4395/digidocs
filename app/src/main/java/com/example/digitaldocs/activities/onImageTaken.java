package com.example.digitaldocs.activities;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
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
    private TextView compName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_image_taken);
        totalPriceText = findViewById(R.id.totalPrice);
        compName = findViewById(R.id.compName);
        Intent intent1 = getIntent();
        String totalPrice = intent1.getExtras().getString("total");
        totalPriceText.setText("Total Price of all Items "+"$"+totalPrice);
        compName.setText("Company Name: "+"Woolworths");




        //mControlsView = findViewById(R.id.fullscreen_content_controls);
     //   mContentView = findViewById(R.id.fullscreen_content);
    }


}