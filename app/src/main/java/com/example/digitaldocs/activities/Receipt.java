package com.example.digitaldocs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digitaldocs.R;

public class Receipt extends AppCompatActivity {

    private ImageButton camera;
    private  ImageButton profile;
    private ImageButton settings;
    private ImageButton receipt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.receipt_page);
        setContentView(R.layout.receipt_page);

        setScene();
        link_camera();
        link_profile();
        link_receipt();
        link_setting();
    }
    private void setScene()
    {
        camera = findViewById(R.id.camera_widget1);
        profile = findViewById(R.id.profile_widget1);
        settings = findViewById(R.id.setting_widget1);
        receipt = findViewById(R.id.receipt_widget1);
    }

    private void link_camera()
    {
        final Intent intent = new Intent(this, Camera.class);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Receipt.this.startActivity(intent);

            }
        });
    }

    private void link_profile()
    {
        final Intent intent = new Intent(this, Profile.class);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Receipt.this.startActivity(intent);

            }
        });
    }
    private void link_setting()
    {
        final Intent intent = new Intent(this, Settings.class);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Receipt.this.startActivity(intent);

            }
        });
    }
    private void link_receipt()
    {
        final Intent intent = new Intent(this, Receipt.class);

        receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Receipt.this.startActivity(intent);

            }
        });
    }


}
