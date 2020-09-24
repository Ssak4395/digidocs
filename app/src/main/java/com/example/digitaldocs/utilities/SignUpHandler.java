package com.example.digitaldocs.utilities;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpHandler {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String uid;

    public SignUpHandler()
    {
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void signup(final String Email, String Password, final String FirstName, final String LastName)
    {
        firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    System.out.println("Successfully Created User");
                    //Get the User ID of currently registered user.
                    uid = firebaseAuth.getUid();
                    DocumentReference documentReference = firebaseFirestore.collection("users").document(uid);
                    Map<String,Object> users = new HashMap<>();
                    users.put("First Name",FirstName);
                    users.put("Last Name", LastName);
                    users.put("Email", Email);
                    documentReference.set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            System.out.println("Data Successfully persisted.");
                        }
                    });


                }
                else throw new IllegalStateException("Error");

            }
        });
    }

}
