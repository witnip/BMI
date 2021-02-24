package com.witnip.bmi.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.witnip.bmi.R;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {


    CircleImageView profileImage;
    TextView lblName,lblEmail,lblHeight,lblWeight,lblWaist;
    Button editProfile;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUser;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profileImage = findViewById(R.id.profileImage);

        lblName = findViewById(R.id.lblName);
        lblEmail = findViewById(R.id.lblEmail);
        lblHeight = findViewById(R.id.lblHeight);
        lblWeight = findViewById(R.id.lblWeight);
        lblWaist = findViewById(R.id.lblWaist);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseUser.keepSynced(true);

        setInfo();

        editProfile = findViewById(R.id.btnEditProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfileIntent = new Intent(Profile.this,EditProfile.class);
                startActivity(editProfileIntent);
            }
        });
    }

    private void setInfo() {
        if(mAuth.getCurrentUser() != null){
            String user_id = mAuth.getCurrentUser().getUid();
            DatabaseReference currentUserDB = mDatabaseUser.child(user_id);
            currentUserDB.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String Email = mAuth.getCurrentUser().getEmail();
                    String firstName = snapshot.child("firstName").getValue().toString();
                    String lastName = snapshot.child("lastName").getValue().toString();
                    String gender = snapshot.child("gender").getValue().toString();
                    double currentHeight = Double.parseDouble(snapshot.child("CurrentHeight").getValue().toString());
                    double currentWeight = Double.parseDouble(snapshot.child("CurrentWeight").getValue().toString());
                    double currentWaist = Double.parseDouble(snapshot.child("CurrentWaist").getValue().toString());
                    int age = Integer.parseInt(snapshot.child("age").getValue().toString());
                    String profilePath = snapshot.child("profile").getValue().toString();

                    Glide.with(getApplicationContext()).load(profilePath).into(profileImage);
                    lblName.setText(firstName+" "+lastName);
                    lblEmail.setText(Email);

                    int[] height = new int[2];
                    height[0] = (int)currentHeight/12;
                    height[1] = (int)currentHeight%12;

                    lblHeight.setText(height[0]+" ft "+height[1]+" in");
                    lblWeight.setText(currentWeight+" kg");
                    lblWaist.setText(currentWaist+" in");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}