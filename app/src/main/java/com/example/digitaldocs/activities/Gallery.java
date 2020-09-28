package com.example.digitaldocs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digitaldocs.R;

public class Gallery extends AppCompatActivity {
    private ImageView gallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.gallery_display);
        setContentView(R.layout.gallery_display);
        setScene();
        link_gallery();


    }

    private void setScene() {
        gallery = findViewById(R.id.gallery_widget);

    }


    private void link_gallery()
    {
        final Intent intent = new Intent(this, Gallery.class);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gallery.this.startActivity(intent);

            }
        });
    }
}