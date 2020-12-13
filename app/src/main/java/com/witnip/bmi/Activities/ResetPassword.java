package com.witnip.bmi.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.witnip.bmi.R;

public class ResetPassword extends AppCompatActivity {

    EditText txtEmail;
    Button btnSendEmail;


    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        txtEmail = findViewById(R.id.txtEmail);
        btnSendEmail = findViewById(R.id.btnSendEmail);


        mAuth = FirebaseAuth.getInstance();

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userEmail = txtEmail.getText().toString().trim();
                if(TextUtils.isEmpty(userEmail)){
                    Toast.makeText(ResetPassword.this, "Enter valid email address", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ResetPassword.this, "Reset Email sent to "+userEmail, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ResetPassword.this,Login.class));
                            }else{
                                String message = task.getException().getMessage();
                                Toast.makeText(ResetPassword.this, "Error: "+message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}