package com.example.digitaldocs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digitaldocs.R;

/**
 * Class that will be displaying scanned images
 */
public class GalleryActivity extends AppCompatActivity {
    private ImageView gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.gallery_display);
        setContentView(R.layout.gallery_display);
        setScene();
        linkGallery();
    }

    private void setScene() {
        gallery = findViewById(R.id.gallery_widget);
    }


    private void linkGallery() {
        final Intent intent = new Intent(this, GalleryActivity.class);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GalleryActivity.this.startActivity(intent);
            }
        });
    }
}
