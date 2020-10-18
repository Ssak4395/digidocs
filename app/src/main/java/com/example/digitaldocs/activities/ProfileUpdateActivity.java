package com.example.digitaldocs.activities;
import java.util.HashMap;
import java.util.Map;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.digitaldocs.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

/**
 * Class allowing the user to update their data
 */
public class ProfileUpdateActivity extends AppCompatActivity {
    private EditText firstName,LastName,Email;
    private Button update;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.update_profile);
        setContentView(R.layout.update_profile);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        firstName = findViewById(R.id.editFName);
        LastName = findViewById(R.id.editlName);
        Email = findViewById(R.id.editE);

        update = findViewById(R.id.button2);

        user = fAuth.getCurrentUser();
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Email.setText(documentSnapshot.getString("Email"));
                firstName.setText(documentSnapshot.getString("First Name"));
                LastName.setText(documentSnapshot.getString("Last Name"));
            }
        });

        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (Email.getText().toString().isEmpty()|| LastName.getText().toString().isEmpty() || firstName.getText().toString().isEmpty()){
                    Toast.makeText(ProfileUpdateActivity.this,"You havent made any changes",Toast.LENGTH_SHORT).show();
                    return;
                }

                final String changeEmail= Email.getText().toString();
                user.updateEmail(changeEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fStore.collection("users").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("Email,",changeEmail);
                        edited.put("First Name,",firstName.getText().toString());
                        edited.put("Last Name,",LastName.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ProfileUpdateActivity.this,"Profile Updated",Toast.LENGTH_SHORT).show();
                            }
                        });
                        Toast.makeText(ProfileUpdateActivity.this,"EMAIL HAS BEEN CHANGED",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileUpdateActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}

