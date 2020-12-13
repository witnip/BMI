package com.witnip.bmi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.witnip.bmi.Activities.EditProfile;
import com.witnip.bmi.Activities.MainActivity;
import com.witnip.bmi.Activities.Register;
import com.witnip.bmi.Activities.SetUpActivity;
import com.witnip.bmi.Model.User;

public class FirebaseDatabaseHandler {
    DatabaseReference mDatabaseUser;
    FirebaseAuth mAuth;
    Context mContext;
    ProgressDialog mProgress;

    public FirebaseDatabaseHandler(DatabaseReference mDatabaseUser, FirebaseAuth mAuth, Context mContext) {
        this.mDatabaseUser = mDatabaseUser;
        this.mAuth = mAuth;
        this.mContext = mContext;
    }

    public FirebaseDatabaseHandler(DatabaseReference mDatabaseUser, FirebaseAuth mAuth, Context mContext, ProgressDialog mProgress) {
        this.mDatabaseUser = mDatabaseUser;
        this.mAuth = mAuth;
        this.mContext = mContext;
        this.mProgress = mProgress;
    }

    public void checkLogin(final String email, String password) {
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            mProgress.setMessage("Checking Login ...");
            mProgress.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener((Activity) mContext, new  OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        checkUserExits(email);
                        mProgress.dismiss();

                    }else{
                        mProgress.dismiss();
                        Toast.makeText(mContext, "Error in Login!!! \nemail/Password may be incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void startRegister(final User user) {
        final String firstName = user.getFirstName();
        final String lastName = user.getLastName();
        final String email = user.getEmail();
        final String password = user.getPassword();

        mAuth.fetchSignInMethodsForEmail(email).addOnSuccessListener(new OnSuccessListener<SignInMethodQueryResult>() {
            @Override
            public void onSuccess(SignInMethodQueryResult signInMethodQueryResult) {
                if(signInMethodQueryResult.getSignInMethods().isEmpty()){
                    if(!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                        mProgress.setMessage("Signing Up...");
                        mProgress.show();
                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener((Activity) mContext,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    String user_id = mAuth.getCurrentUser().getUid();
                                    DatabaseReference currentUserDB = mDatabaseUser.child(user_id);
                                    currentUserDB.child("firstName").setValue(firstName);
                                    currentUserDB.child("lastName").setValue(lastName);
                                    mProgress.dismiss();

                                    goToSetUpProfile(user.getFirstName(),user.getLastName(),user.getEmail());
                                }else{
                                    mProgress.dismiss();
                                    Log.e("Register", "onComplete: ",task.getException() );
                                    Toast.makeText(mContext, "Failed to Register", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }else {
                    Toast.makeText(mContext, "Eamil : "+email+" \nalready exits", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



    //Facebook Register

    public void checkUserExits(final String email) {
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(user_id).hasChild("gender")){
                    goToMain();
                }else{
                    String firstName = dataSnapshot.child(user_id).child("firstName").getValue().toString();
                    String lastName = dataSnapshot.child(user_id).child("lastName").getValue().toString();
                    goToSetUpProfile(firstName,lastName,email);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }



    public void goToMain(){
        Intent mainIntent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(mainIntent);
        ((Activity) mContext).finish();
    }

    private void goToSetUpProfile(String firstName,String lastName, String email) {
        Intent setupIntent = new Intent(mContext, SetUpActivity.class);
        setupIntent.putExtra("firstName",firstName);
        setupIntent.putExtra("lastName",lastName);
        setupIntent.putExtra("email",email);
        mContext.startActivity(setupIntent);
        ((Activity) mContext).finish();
    }

    //get user data

}
