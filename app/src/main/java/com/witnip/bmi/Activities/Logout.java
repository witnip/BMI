package com.witnip.bmi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.witnip.bmi.R;

public class Logout extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        logout();
        finish();
    }

    private void logout() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();
    }
}