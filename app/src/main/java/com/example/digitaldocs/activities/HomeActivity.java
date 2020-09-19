package com.example.digitaldocs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digitaldocs.R;
import com.google.android.material.textfield.TextInputLayout;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class HomeActivity extends AppCompatActivity {


    private static final boolean AUTO_HIDE = true;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private TextView sign_up_prompt;
    Button sign_in_prompt;



    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;


    private View mControlsView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_home);
        setContentView(R.layout.activity_home);
        mControlsView = findViewById(R.id.container);
//        firebaseAuth = FirebaseAuth.getInstance();

setScene();
explodeSignUpScene();
explodeDashBoardScene();




    }

    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Email Address cannot be empty");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Password cannot be empty");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }

    public void confirmInput(View v) {
        if (!validateEmail() | !validatePassword()) {
            return;
        }

        String input = "Email: " + textInputEmail.getEditText().getText().toString();
        input += "\n";
        input += "Password: " + textInputPassword.getEditText().getText().toString();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }

    private void setScene()
    {
      textInputEmail = findViewById(R.id.text_input_email);
      textInputPassword = findViewById(R.id.text_input_password);
      sign_up_prompt = findViewById(R.id.sign_up);
      sign_in_prompt= findViewById(R.id.LinkToDashboard);
    }
    private void explodeDashBoardScene()
    {
        final Intent intent = new Intent(this, dashboard.class);

        sign_in_prompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.this.startActivity(intent);

            }
        });
    }

    private void explodeSignUpScene()
    {
        final Intent intent = new Intent(this, Sign_Up_Activity.class);

        sign_up_prompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.this.startActivity(intent);

            }
        });
    }

}