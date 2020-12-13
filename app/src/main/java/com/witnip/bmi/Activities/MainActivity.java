package com.witnip.bmi.Activities;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.witnip.bmi.Model.User;
import com.witnip.bmi.R;
import com.witnip.bmi.ui.home.HomeFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabaseUser;
    private ProgressDialog mProgress;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users");
        mProgress = new ProgressDialog(this);
        mDatabaseUser.keepSynced(true);

        checkUserExits();

        setContentView(R.layout.activity_main);





        //Verifying User is logged in
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {

                    Intent loginIntent = new Intent(MainActivity.this, Login.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                    finish();
                } else {
                    if (!firebaseAuth.getCurrentUser().isEmailVerified()) {
                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Verification email sent to : " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        Intent loginIntent = new Intent(MainActivity.this, Login.class);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginIntent);
                        finish();
                        Toast.makeText(MainActivity.this, "Verify email first", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }


        if(mAuth.getCurrentUser() !=null){
            View navHead = navigationView.getHeaderView(0);
            final ImageView profileImage = navHead.findViewById(R.id.navProfile);
            final TextView name = navHead.findViewById(R.id.navName);
            final TextView email = navHead.findViewById(R.id.navEmail);

            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userID = mAuth.getCurrentUser().getUid();
                    String Email = mAuth.getCurrentUser().getEmail();
                    String filepath = snapshot.child(userID).child("profile").getValue().toString();
                    String firstName = snapshot.child(userID).child("firstName").getValue().toString();
                    String lastName = snapshot.child(userID).child("lastName").getValue().toString();

                    Glide.with(MainActivity.this).load(filepath).into(profileImage);
                    name.setText(firstName+" "+lastName);
                    email.setText(Email);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();
                break;
            case R.id.nav_profile:
                Intent gotoProfile = new Intent(MainActivity.this,Profile.class);
                startActivity(gotoProfile);
                break;
            case R.id.nav_about:
                Intent gotoAbout = new Intent(MainActivity.this,About.class);
                startActivity(gotoAbout);
                break;
            case R.id.nav_share:
                Intent gotoShare = new Intent(MainActivity.this,Share.class);
                startActivity(gotoShare);
                break;
            case R.id.nav_feedback:
                Intent gotoFeedback = new Intent(MainActivity.this,FeedBack.class);
                startActivity(gotoFeedback);
                break;
            case R.id.nav_logout:
                Intent gotoLogout = new Intent(MainActivity.this,Logout.class);
                startActivity(gotoLogout);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }


    public void checkUserExits() {
        if (mAuth.getCurrentUser() != null) {
            mProgress.setMessage("Checking User...");
            mProgress.show();
            final String user_id = mAuth.getCurrentUser().getUid();
            final String email = mAuth.getCurrentUser().getEmail();
            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.child(user_id).hasChild("gender")) {
                        String firstName = dataSnapshot.child(user_id).child("firstName").getValue().toString();
                        String lastName = dataSnapshot.child(user_id).child("lastName").getValue().toString();
                        goToSetUpProfile(firstName, lastName, email);
                        mProgress.dismiss();
                        Toast.makeText(MainActivity.this, "Set up user first..", Toast.LENGTH_SHORT).show();
                    }else {
                        mProgress.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mProgress.dismiss();
                }
            });
        }
    }

    private void goToSetUpProfile(String firstName, String lastName, String email) {
        Intent setupIntent = new Intent(MainActivity.this, SetUpActivity.class);
        setupIntent.putExtra("firstName", firstName);
        setupIntent.putExtra("lastName", lastName);
        setupIntent.putExtra("email", email);
        setupIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(setupIntent);
        finish();
    }


}