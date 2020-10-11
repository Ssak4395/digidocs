package com.example.digitaldocs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.example.digitaldocs.R;

public class DashboardActivity extends AppCompatActivity {
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
        final Intent intent = new Intent(this, CameraActivity.class);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DashboardActivity.this.startActivity(intent);
            }
        });
    }

    private void linkProfile() {
        final Intent intent = new Intent(this, ProfileActivity.class);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DashboardActivity.this.startActivity(intent);
            }
        });
    }

    private void linkSetting() {
        final Intent intent = new Intent(this, SettingsActivity.class);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DashboardActivity.this.startActivity(intent);
            }
        });
    }

    private void linkReceipt() {
        final Intent intent = new Intent(this, ReceiptActivity.class);

        receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DashboardActivity.this.startActivity(intent);
            }
        });
    }







}
