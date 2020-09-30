package com.example.digitaldocs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Map;
import java.util.HashMap;
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

public class Profile extends AppCompatActivity {


     private Button exit;

     /**
      * FOR NAVIGATION BAR
      *
      */
     private ImageButton camera;
     private  ImageButton profile;
     private ImageButton settings;
     private ImageButton receipt;

     /**
      *
      * For the top part of profile page
      */
     private TextView name,email,last;
     private Button update;
     private EditText firstName,LastName,userEmail;

     FirebaseAuth fAuth;
     FirebaseFirestore fStore;
     private String userID;
     private FirebaseUser user;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);

     this.setContentView(R.layout.profile_page);
     setContentView(R.layout.profile_page);
     setScene();
     link_camera();
     link_profile();
     link_receipt();
     link_setting();
     link_exit();
     link_update();



     name = findViewById(R.id.test);
     //email = findViewById(R.id.test1);


     fAuth = FirebaseAuth.getInstance();
     fStore = FirebaseFirestore.getInstance();
     userID = fAuth.getCurrentUser().getUid();

     firstName = findViewById(R.id.editfirstName);
     LastName = findViewById(R.id.editLastName);



     user = fAuth.getCurrentUser();


     DocumentReference documentReference = fStore.collection("users").document(userID);

     documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
     @Override
     public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
          name.setText(documentSnapshot.getString("First Name"));
          email.setText(documentSnapshot.getString("Email"));

          firstName.setText(documentSnapshot.getString("First Name"));
          LastName.setText(documentSnapshot.getString("Last Name"));


     }
     });
     }

     private void link_camera()
     {
     final Intent intent = new Intent(this, Camera.class);

     camera.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
     Profile.this.startActivity(intent);

     }
     });
     }
     private void link_update()
     {
          final Intent intent = new Intent(this, profile_updateEdit.class);

          update.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                    Profile.this.startActivity(intent);

               }
          });
     }

     private void link_profile() {
          final Intent intent = new Intent(this, Profile.class);

          profile.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) { Profile.this.startActivity(intent);

          }
          });
     }
     private void link_setting()
     {
     final Intent intent = new Intent(this, Settings.class);

     settings.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
     Profile.this.startActivity(intent);

     }
     });
     }
     private void link_receipt()
     {
     final Intent intent = new Intent(this, Receipt.class);

     receipt.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
     Profile.this.startActivity(intent);

     }
     });
     }
     private void link_exit()
     {
          final Intent intent = new Intent(this, HomeActivity.class);

          exit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                    Profile.this.startActivity(intent);

               }
          });
     }

     private void setScene()
     {
          update = findViewById(R.id.button);
     exit = findViewById(R.id.LOGOUTT);
     camera = findViewById(R.id.done);
     profile = findViewById(R.id.dd);
     settings = findViewById(R.id.ee);
     receipt = findViewById(R.id.aa);

     }


}
