package com.example.digitaldocs.utilities;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpHandler {

    FirebaseAuth firebaseAuth;

    public SignUpHandler()
    {
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void signup(String Email, String Password)
    {
        firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    System.out.println("Successfully Created User");
                }
                else throw new IllegalStateException("Error");

            }
        });
    }

}
