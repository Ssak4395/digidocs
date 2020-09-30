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

    private ImageButton legal;
    private ImageButton help;



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
        link_help();
        link_legal();


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
    private void link_legal()
    {
        final Intent intent = new Intent(this, LEGAL_UPDATE.class);

        legal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.this.startActivity(intent);

            }
        });
    }
    private void link_help()
    {
        final Intent intent = new Intent(this, HELP_PAGE.class);

        help.setOnClickListener(new View.OnClickListener() {
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
        legal = findViewById(R.id.legal_widget);
        help = findViewById(R.id.help_widget);


        camera = findViewById(R.id.camera_setting);
        profile = findViewById(R.id.profile_setting);
        settings = findViewById(R.id.search_setting);
        receipt = findViewById(R.id.receipt_setting);
    }
}
