package com.example.digitaldocs.activities;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.digitaldocs.R;
import com.example.digitaldocs.utilities.SignUpHandler;
import com.example.digitaldocs.utilities.alertDialogs;
import com.example.digitaldocs.utilities.emailValidator;
import com.example.digitaldocs.utilities.passwordValidator;

import java.util.regex.Pattern;
import com.example.digitaldocs.utilities.SignUpHandler;


public class Sign_Up_Activity extends AppCompatActivity {



    //SignUpHandler signUpHandler;
    EditText editEmail;
    EditText editPassword;
    EditText editFirstName;
    EditText editLastName;
    Button sign_up;
    LoadingDialog loadingDialog;
    alertDialogs alertDialogs;
    SignUpHandler signUpHandler;
    passwordValidator passwordValidator;
    emailValidator emailValidator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up_);

        loadingDialog = new LoadingDialog(this);
        alertDialogs = new alertDialogs(this);
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
                    boolean validEmail;
                    boolean validPassword;
                    if (editEmail.getText().toString().isEmpty()) {
                        validEmail = false;
                    } else {
                        validEmail = com.example.digitaldocs.utilities.emailValidator.validate(editEmail.getText().toString());
                    }
                    if (editPassword.getText().toString().isEmpty()) { // avoids null pointer exception in validate method
                        validPassword = false;
                    } else {
                        validPassword = passwordValidator.validate(editPassword.getText().toString());
                    }
                    int duration = Toast.LENGTH_LONG;
                    if (!validEmail) { // invalid email displays error message
                        Context context = getApplicationContext();
                        CharSequence text = "Invalid email, please try again.";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    if (!validPassword) { // invalid password displays error message
                        Context context = getApplicationContext();
                        CharSequence text = "Invalid password, please try again.";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    if (validEmail && validPassword) { // if both are valid we do our normal sign-up routine
                        signUpHandler.signup(editEmail.getText().toString(),editPassword.getText().toString(),
                                editFirstName.getText().toString(),editLastName.getText().toString()
                        );
                        loadingDialog.startLoadingAnimationg();
                        //Create new thread to dismiss the loading dialog
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadingDialog.dismissDialog();
                                //Go Back to sign in page
                                reloadDashboard();
                            }
                        },2000);
                    }
            }
        });

    }


public void reloadDashboard()
{
    final Intent intent = new Intent(this,HomeActivity.class);
    this.startActivity(intent);
}




}