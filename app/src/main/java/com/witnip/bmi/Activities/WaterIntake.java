package com.witnip.bmi.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.witnip.bmi.Adapters.WaterIntakeAdapter;
import com.witnip.bmi.DatePicker.DatePickerFragment;
import com.witnip.bmi.Dialog.WaterDialog;
import com.witnip.bmi.Model.WaterIntakeModel;
import com.witnip.bmi.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WaterIntake extends AppCompatActivity implements WaterDialog.WaterDialogListener, DatePickerDialog.OnDateSetListener {

    TextView lblDate,lblCurrentWaterIntake,lblRemainingWater;
    Button btnAdd,btnViewGraph;
    RecyclerView rvWaterIntake;
    WaterIntakeAdapter adapter;

    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabaseUser;

    ArrayList<WaterIntakeModel> waterIntakeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_intake);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users");

        lblDate = findViewById(R.id.lblDate);
        lblCurrentWaterIntake = findViewById(R.id.lblCurrentWaterIntake);
        btnAdd = findViewById(R.id.btnAdd);

        btnViewGraph = findViewById(R.id.btnViewGraph);

        rvWaterIntake = findViewById(R.id.rvWaterIntake);
        rvWaterIntake.setLayoutManager(new LinearLayoutManager(this));

        lblRemainingWater = findViewById(R.id.lblRemainingWater);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWaterIntake();
            }
        });

        lblDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        waterIntakeList = new ArrayList<WaterIntakeModel>();

        setWaterIntakeFirstTime();
        setUI();
        getCurrentWaterIntake();

        btnViewGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoWaterIntakeGraph = new Intent(WaterIntake.this,WaterIntakeGraph.class);
                startActivity(gotoWaterIntakeGraph);
            }
        });

    }

    private void getCurrentWaterIntake() {
        //Setting up RecyclerView
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference currentUserDB = mDatabaseUser.child(user_id);
        DatabaseReference waterIntakeDB = currentUserDB.child("waterIntake");
        DatabaseReference daily = waterIntakeDB.child("daily");
        daily.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                waterIntakeList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    WaterIntakeModel model = dataSnapshot.getValue(WaterIntakeModel.class);
                    String date = model.getDate();
                    String currentDate = new SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault()).format(new Date());
                    if(date.equals(currentDate)){
                        waterIntakeList.add(model);
                    }
                }
                Collections.reverse(waterIntakeList);
                adapter = new WaterIntakeAdapter(WaterIntake.this,waterIntakeList);
                rvWaterIntake.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void getWaterIntake(final String givenDate) {
        //Setting up RecyclerView
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference currentUserDB = mDatabaseUser.child(user_id);
        DatabaseReference waterIntakeDB = currentUserDB.child("waterIntake");
        DatabaseReference daily = waterIntakeDB.child("daily");
        daily.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                waterIntakeList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    WaterIntakeModel model = dataSnapshot.getValue(WaterIntakeModel.class);
                    String date = model.getDate();
                    if(date.equals(givenDate)){
                        waterIntakeList.add(model);
                    }
                }
                Collections.reverse(waterIntakeList);
                adapter = new WaterIntakeAdapter(WaterIntake.this,waterIntakeList);
                rvWaterIntake.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setUI() {
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference currentUserDB = mDatabaseUser.child(user_id);
        DatabaseReference waterIntakeDB = currentUserDB.child("waterIntake");
        waterIntakeDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("currentDate")){
                    double currentWater = Double.parseDouble(snapshot.child("remainingWater").getValue().toString());
                    double remainingWater = currentWater;
                    double totalWaterDrink = 3700-remainingWater;
                    lblCurrentWaterIntake.setText(String.format("%s ml", totalWaterDrink));
                    if(remainingWater > 0){
                        lblRemainingWater.setText(String.format("%s ml to go", remainingWater));
                    }
                    else{
                        lblRemainingWater.setText("Yeah!! you have completed today's tasks");
                    }
                } else{
                    lblCurrentWaterIntake.setText(String.format("%s ml", 0.0));
                    lblRemainingWater.setText(String.format("%s ml to go", 3700.0));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setWaterIntakeFirstTime() {
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference currentUserDB = mDatabaseUser.child(user_id);
        final DatabaseReference waterIntakeDB = currentUserDB.child("waterIntake");
        waterIntakeDB.addListenerForSingleValueEvent(new ValueEventListener() {
            String date = new SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault()).format(new Date());
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.hasChild("currentDate")){
                    waterIntakeDB.child("currentDate").setValue(date);
                    waterIntakeDB.child("remainingWater").setValue(3700);
                    lblCurrentWaterIntake.setText(String.format("%s ml", 0));
                    lblRemainingWater.setText(String.format("%s ml to go", 3700));
                    lblDate.setText(date);
                }else{
                    String currentDate = snapshot.child("currentDate").getValue().toString();
                    if(!date.equals(currentDate)){
                        waterIntakeDB.child("currentDate").setValue(date);
                        waterIntakeDB.child("remainingWater").setValue(3700);
                        lblCurrentWaterIntake.setText(String.format("%s ml", 0));
                        lblRemainingWater.setText(String.format("%s ml to go", 3700));
                        lblDate.setText(date);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void openDialog() {
        WaterDialog waterDialog = new WaterDialog();
        waterDialog.show(getSupportFragmentManager(),"Water Intake");

    }

    @Override
    public void setWaterIntake(final Double waterIntake) {
        //Getting the value of waterIntake
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference currentUserDB = mDatabaseUser.child(user_id);
        final DatabaseReference waterIntakeDB = currentUserDB.child("waterIntake");
        final String date = new SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault()).format(new Date());
        final String time = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
        waterIntakeDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double currentWater = Double.parseDouble(snapshot.child("remainingWater").getValue().toString());
                double remainingWater = currentWater - waterIntake;
                double totalWaterDrink = 3700-remainingWater;
                if(remainingWater > 0){
                    lblCurrentWaterIntake.setText(String.format("%s ml", totalWaterDrink));
                    lblRemainingWater.setText(String.format("%s ml to go", remainingWater));
                    waterIntakeDB.child("remainingWater").setValue(remainingWater);
                    long unixDate = Instant.now().getEpochSecond();
                    DatabaseReference dailyWaterIntake = waterIntakeDB.child("daily");
                    DatabaseReference dateWiseData = dailyWaterIntake.child(""+unixDate);
                    dateWiseData.child("date").setValue(date);
                    dateWiseData.child("time").setValue(time);
                    dateWiseData.child("waterIntake").setValue(waterIntake);
                }
                else{
                    lblRemainingWater.setText("Yeah!! you have completed today's tasks");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = ""+dayOfMonth+"-"+(month+1)+"-"+year;
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = null;
        try {
            date1 = format1.parse(currentDate);
            SimpleDateFormat format2 = new SimpleDateFormat("dd-MMMM-yyyy");
            String date2 = format2.format(date1);
            lblDate.setText(date2);
            getWaterIntake(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void addWaterIntake() {
        openDialog();
    }

}