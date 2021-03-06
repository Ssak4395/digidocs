package com.example.digitaldocs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.digitaldocs.R;

public class SignUpConfirmationActivity extends AppCompatActivity {

    /**
     * The display which is going to be show when sign up is confirmed
     */
    Button proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.confirmation);
        setContentView(R.layout.confirmation);
        proceed = findViewById(R.id.Proceed);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Button clicked.");
                final Intent intent = new Intent(SignUpConfirmationActivity.this, HomeActivity.class);
                SignUpConfirmationActivity.this.startActivity(intent);
            }
        });

    }

}