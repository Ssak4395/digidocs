package com.example.digitaldocs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digitaldocs.R;

public class SettingsActivity extends AppCompatActivity {
    private ImageButton camera;
    private ImageButton profile;
    private ImageButton settings;
    private ImageButton receipt;
    private ImageButton legal;
    private ImageButton help;
    private ImageButton search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.setting_page);
        setContentView(R.layout.setting_page);

        setScene();
        linkCamera();
        linkProfile();
        linkReceipt();
        linkSetting();
        linkHelp();
        linkLegal();
        linkSearch();
    }

    /**
     * Linking to the camera page from settings page
     */

    private void linkCamera() {
        final Intent intent = new Intent(this, CameraActivity.class);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              SettingsActivity.this.startActivity(intent);
            }
        });
    }

    /**
     * Linking to the legal page from the settings page
     */

    private void linkLegal() {
        final Intent intent = new Intent(this, LegalActivity.class);

        legal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.this.startActivity(intent);
            }
        });
    }

    /**
     * Linking to the help page from the settings page
     */

    private void linkHelp() {
        final Intent intent = new Intent(this, HelpActivity.class);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.this.startActivity(intent);
            }
        });
    }

    /**
     * Links to the profile page from the setting page
     */

    private void linkProfile() {
        final Intent intent = new Intent(this, ProfileActivity.class);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               SettingsActivity.this.startActivity(intent);
            }
        });
    }
    /**
     * Links to the setting page from the setting page
     */

    private void linkSetting() {
        final Intent intent = new Intent(this, SettingsActivity.class);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              SettingsActivity.this.startActivity(intent);
            }
        });
    }

    /**
     * Links to the receipt page from the settings page
     */

    private void linkReceipt() {
        final Intent intent = new Intent(this, ReceiptActivity.class);

        receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              SettingsActivity.this.startActivity(intent);
            }
        });
    }

    /**
     * Links the setting page to the search bar
     */
    private void linkSearch() {
        final Intent intent = new Intent(this, SearchActivityPage.class);

       search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.this.startActivity(intent);
            }
        });
    }

    /**
     * Links each button with its id
     */

    private void setScene() {
        legal = findViewById(R.id.legal_widget);
        help = findViewById(R.id.help_widget);
        camera = findViewById(R.id.camera_setting);
        profile = findViewById(R.id.profile_setting);
        settings = findViewById(R.id.search_setting);
        receipt = findViewById(R.id.receipt_setting);
        search = findViewById(R.id.search_setting);
    }

}
