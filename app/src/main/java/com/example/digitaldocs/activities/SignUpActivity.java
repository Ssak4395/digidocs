package com.example.digitaldocs.activities;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.digitaldocs.R;
import com.example.digitaldocs.utilities.SignUpHandler;
import com.example.digitaldocs.utilities.AlertDialogs;
import com.example.digitaldocs.utilities.EmailValidator;
import com.example.digitaldocs.utilities.PasswordValidator;

public class SignUpActivity extends AppCompatActivity {
    EditText editEmail;
    EditText editPassword;
    EditText editFirstName;
    EditText editLastName;
    Button signUp;
    LoadingActivity loadingDialog;
    AlertDialogs alertDialogs;
    SignUpHandler signUpHandler;
    PasswordValidator passwordValidator;
    EmailValidator emailValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up_);

        loadingDialog = new LoadingActivity(this);
        alertDialogs = new AlertDialogs(this);
        signUpHandler = new SignUpHandler();
        passwordValidator = new PasswordValidator();
        emailValidator = new EmailValidator();
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassInput);
        signUp = findViewById(R.id.sign_up_button);
        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);

        signUpHandler = new SignUpHandler();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    boolean validEmail;
                    boolean validPassword;
                    if (editEmail.getText().toString().isEmpty()) {
                        validEmail = false;
                    } else {
                        validEmail = emailValidator.validate(editEmail.getText().toString());
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
                        signUpHandler.signUp(editEmail.getText().toString(),editPassword.getText().toString(),
                                editFirstName.getText().toString(),editLastName.getText().toString()
                        );
                        loadingDialog.startLoadingAnimation();
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

    public void reloadDashboard() {
        final Intent intent = new Intent(this,SignUpConfirmationActivity.class);
        this.startActivity(intent);
    }

}
