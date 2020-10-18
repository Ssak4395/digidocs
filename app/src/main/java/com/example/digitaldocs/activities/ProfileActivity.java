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

public class ProfileActivity extends AppCompatActivity {
     private Button exit;

     /**
      * FOR NAVIGATION BAR
      */
     private ImageButton camera;
     private  ImageButton profile;
     private ImageButton settings;
     private ImageButton receipt;
     private ImageButton search;

     /**
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
          linkCamera();
          linkProfile();
          linkReceipt();
          linkSetting();
          linkSearch();
          linkExit();
          linkUpdate();
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

                    firstName.setText(documentSnapshot.getString("First Name"));
                    LastName.setText(documentSnapshot.getString("Last Name"));
               }
          });
     }

     private void linkCamera() {
          final Intent intent = new Intent(this, CameraActivity.class);

          camera.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                    ProfileActivity.this.startActivity(intent);
               }
          });
     }
     private void linkSearch() {
          final Intent intent = new Intent(this, SearchActivityPage.class);

          search.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                    ProfileActivity.this.startActivity(intent);
               }
          });
     }


     private void linkUpdate() {
          final Intent intent = new Intent(this, ProfileUpdateActivity.class);

          update.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                    ProfileActivity.this.startActivity(intent);
               }
          });
     }

     private void linkProfile() {
          final Intent intent = new Intent(this, ProfileActivity.class);

          profile.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                    ProfileActivity.this.startActivity(intent);
               }
          });
     }

     private void linkSetting() {
          final Intent intent = new Intent(this, SettingsActivity.class);

          settings.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                    ProfileActivity.this.startActivity(intent);
               }
          });
     }

     private void linkReceipt() {
          final Intent intent = new Intent(this, ReceiptActivity.class);

          receipt.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                    ProfileActivity.this.startActivity(intent);
               }
          });
     }

     private void linkExit() {
          final Intent intent = new Intent(this, HomeActivity.class);

          exit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                    ProfileActivity.this.startActivity(intent);
               }
          });
     }

     private void setScene() {
          update = findViewById(R.id.button);
          exit = findViewById(R.id.LOGOUTT);
          camera = findViewById(R.id.done);
          profile = findViewById(R.id.dd);
          settings = findViewById(R.id.ee);
          receipt = findViewById(R.id.aa);
          search = findViewById(R.id.bb);

     }

}
