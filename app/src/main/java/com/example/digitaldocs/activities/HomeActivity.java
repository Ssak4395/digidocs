package com.example.digitaldocs.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digitaldocs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class HomeActivity extends AppCompatActivity {
    private static final boolean AUTO_HIDE = true;
    public static Context context;
    private EditText textInputEmail;
    private EditText textInputPassword;
    private TextView signUpPrompt;
    Button signInPrompt;
    Button signIn;
    FirebaseAuth firebaseAuth;
    private TextView forgotPassword;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private View mControlsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        setContentView(R.layout.activity_home);
        mControlsView = findViewById(R.id.container);
        signIn = findViewById(R.id.SignIn);
        firebaseAuth = FirebaseAuth.getInstance();

        setScene();
        explodeSignUpScene();
        explodeForgotPassword();

        signIn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              System.out.println("Button clicked.");
              handleSignIn(textInputEmail.getText().toString(),textInputPassword.getText().toString());
          }
        });
    }

    private boolean validateEmail() {
        String emailInput = textInputEmail.getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Email Address cannot be empty");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = textInputPassword.getText().toString().trim();

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

        String input = "Email: " + textInputEmail.getText().toString();
        input += "\n";
        input += "Password: " + textInputPassword.getText().toString();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }

    private void setScene() {
      textInputEmail = findViewById(R.id.email);
      textInputPassword = findViewById(R.id.password);
      signUpPrompt = findViewById(R.id.sign_up);
      signInPrompt= findViewById(R.id.SignIn);
      forgotPassword= findViewById(R.id.forget_widget); //link the sign up page to the forget pw
    }

    private void explodeDashBoardScene() {

    }
    
    private void explodeForgotPassword() {
        final Intent intent = new Intent(this, ForgotPassword.class);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //linking needed
                HomeActivity.this.startActivity(intent);
            }
        });
    }
    

    private void explodeSignUpScene() {
        final Intent intent = new Intent(this, Sign_Up_Activity.class);

        signUpPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.this.startActivity(intent);
            }
        });
    }

    public void handleSignIn(String email, String password) {
        final Intent intent = new Intent(this,dashboard.class);

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    if(firebaseAuth.getCurrentUser().isEmailVerified()) {
                        HomeActivity.this.startActivity(intent);
                    } else {
                        new AlertDialog.Builder(context)
                                .setTitle("Error")
                                .setMessage("Please Verify Your Email Address ")
                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Continue with delete operation
                                    }
                                }).show();
                    }
                }
            }
        });
    }
}