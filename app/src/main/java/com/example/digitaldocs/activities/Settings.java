package com.example.digitaldocs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digitaldocs.R;

public class Settings extends AppCompatActivity {
    private ImageButton camera;
    private  ImageButton profile;
    private ImageButton settings;
    private ImageButton receipt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.setting_page);
        setContentView(R.layout.setting_page);
        setScene();
        link_camera();
        link_profile();
        link_receipt();
        link_setting();


    }
    private void link_camera()
    {
        final Intent intent = new Intent(this, Camera.class);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Settings.this.startActivity(intent);

            }
        });
    }

    private void link_profile()
    {
        final Intent intent = new Intent(this, Profile.class);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Settings.this.startActivity(intent);

            }
        });
    }
    private void link_setting()
    {
        final Intent intent = new Intent(this, Settings.class);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Settings.this.startActivity(intent);

            }
        });
    }
    private void link_receipt()
    {
        final Intent intent = new Intent(this, Receipt.class);

        receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Settings.this.startActivity(intent);

            }
        });
    }

    private void setScene()
    {
        camera = findViewById(R.id.ccc);
        profile = findViewById(R.id.ddd);
        settings = findViewById(R.id.eee);
        receipt = findViewById(R.id.aaa);
    }
}
