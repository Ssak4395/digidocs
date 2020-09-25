package com.example.digitaldocs.activities;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.digitaldocs.R;
import com.example.digitaldocs.utilities.SignUpHandler;
//import com.example.digitaldocs.utilities.SignUpHandler;


public class Sign_Up_Activity extends AppCompatActivity {



    //SignUpHandler signUpHandler;
    EditText editEmail;
    EditText editPassword;
    EditText editFirstName;
    EditText editLastName;
    Button sign_up;
    SignUpHandler signUpHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up_);

        signUpHandler = new SignUpHandler();
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassInput);
        sign_up = findViewById(R.id.sign_up_button);
        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);


        signUpHandler = new SignUpHandler();
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpHandler.signup(editEmail.getText().toString(),editPassword.getText().toString(),
                        editFirstName.getText().toString(),editLastName.getText().toString()
                );
            }
        });

    }







}