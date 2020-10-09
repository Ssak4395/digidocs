package com.example.digitaldocs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.example.digitaldocs.R;

public class dashboard extends AppCompatActivity {
    private ImageButton camera;
    private ImageButton profile;
    private ImageButton settings;
    private ImageButton receipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_page);

        setScene();
        linkCamera();
        linkProfile();
        linkReceipt();
        linkSetting();
    }

    private void setScene() {
        camera = findViewById(R.id.camera_widget);
        profile = findViewById(R.id.profile_widget);
        settings = findViewById(R.id.setting_one);
        receipt = findViewById(R.id.receipt_widget);
    }

    private void linkCamera() {
        final Intent intent = new Intent(this, Camera.class);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dashboard.this.startActivity(intent);
            }
        });
    }

    private void linkProfile() {
        final Intent intent = new Intent(this, Profile.class);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dashboard.this.startActivity(intent);
            }
        });
    }

    private void linkSetting() {
        final Intent intent = new Intent(this, Settings.class);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dashboard.this.startActivity(intent);
            }
        });
    }

    private void linkReceipt() {
        final Intent intent = new Intent(this, Receipt.class);

        receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dashboard.this.startActivity(intent);
            }
        });
    }







}
