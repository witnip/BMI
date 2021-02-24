package com.witnip.bmi.ui.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.witnip.bmi.Activities.BfpCalculation;
import com.witnip.bmi.Activities.BfpResult;
import com.witnip.bmi.Activities.BmiCalculation;
import com.witnip.bmi.Activities.BmiResult;
import com.witnip.bmi.Activities.CaloriesCalculation;
import com.witnip.bmi.Activities.Diet;
import com.witnip.bmi.Activities.IdealWeightCalculation;
import com.witnip.bmi.Activities.WaterIntake;
import com.witnip.bmi.Activities.WeightGoal;
import com.witnip.bmi.Activities.WeightTracker;
import com.witnip.bmi.FirebaseDatabaseHandler;
import com.witnip.bmi.Model.User;
import com.witnip.bmi.R;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    private TextView lblName,txtHeight,txtWeight,txtWaist,lblWaterIntake,lblWeightTracker,txtBMI,txtBFP,lblGreeting;
    private LinearLayout btnBMI,btnWeightGoal,btnDiet,btnBFP,btnIdealWeight,btnCalories;
    private RelativeLayout rlWaterIntake,rlWeightTracker;
    private ImageView ivWaterIntake,ivWeightTracker,ivBanner;

    private LinearLayout llBMI,llBFP;

    private DatabaseReference mDatabaseUser;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        lblGreeting = root.findViewById(R.id.lblGreeting);
        lblName = root.findViewById(R.id.lblName);

        llBMI = root.findViewById(R.id.llBMI);
        llBFP = root.findViewById(R.id.llBFP);

        txtHeight = root.findViewById(R.id.txtHeight);
        txtWeight = root.findViewById(R.id.txtWeight);
        txtWaist = root.findViewById(R.id.txtWaist);

        btnBMI = root.findViewById(R.id.btnBMI);
        btnWeightGoal = root.findViewById(R.id.btnWeightGoal);
        btnDiet = root.findViewById(R.id.btnDiet);
        btnBFP = root.findViewById(R.id.btnBFP);
        btnIdealWeight = root.findViewById(R.id.btnIdealWeight);
        btnCalories = root.findViewById(R.id.btnCalories);

        rlWaterIntake = root.findViewById(R.id.rlWaterIntake);
        ivWaterIntake = root.findViewById(R.id.ivWaterIntake);
        lblWaterIntake = root.findViewById(R.id.lblWaterIntake);

        rlWeightTracker = root.findViewById(R.id.rlWeightTracker);
        ivWeightTracker = root.findViewById(R.id.ivWeightTracker);
        lblWeightTracker = root.findViewById(R.id.lblWeightTracker);

        txtBMI = root.findViewById(R.id.txtBMI);
        txtBFP = root.findViewById(R.id.txtBFP);

        ivBanner = root.findViewById(R.id.ivBanner);

        mDatabaseUser = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();


        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            lblGreeting.setText("Good Morning");
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            lblGreeting.setText("Good Afternoon");
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            lblGreeting.setText("Good Evening");
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            lblGreeting.setText("Good Night");
        }

        if(mAuth.getCurrentUser() != null){
            setCurrentUserData();
        }

        llBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double bmi = Double.parseDouble(txtBMI.getText().toString());
                Intent gotoBMIResult = new Intent(getActivity(), BmiResult.class);
                gotoBMIResult.putExtra("bmi",bmi);
                startActivity(gotoBMIResult);
            }
        });

        llBFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final double bfp = Double.parseDouble(txtBFP.getText().toString());
                String user_id = mAuth.getCurrentUser().getUid();
                DatabaseReference mCurrentUser = mDatabaseUser.child(user_id);
                mCurrentUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String gender = snapshot.child("gender").getValue().toString();
                        Intent gotoBFPResult = new Intent(getActivity(), BfpResult.class);
                        gotoBFPResult.putExtra("gender",gender);
                        gotoBFPResult.putExtra("bfp",bfp);
                        startActivity(gotoBFPResult);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("HomeFragment", "onCancelled: "+error);
                    }
                });
            }
        });

        btnBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoBMI = new Intent(getActivity(), BmiCalculation.class);
                startActivity(gotoBMI);
            }
        });

        btnWeightGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoWeightGoal = new Intent(getActivity(), WeightGoal.class);
                startActivity(gotoWeightGoal);
            }
        });

        btnDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoDiet = new Intent(getActivity(), Diet.class);
                startActivity(gotoDiet);
            }
        });

        btnBFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoBFP = new Intent(getActivity(), BfpCalculation.class);
                startActivity(gotoBFP);
            }
        });

        btnIdealWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoIdealWeight = new Intent(getActivity(), IdealWeightCalculation.class);
                startActivity(gotoIdealWeight);
            }
        });

        btnCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoCalories = new Intent(getActivity(), CaloriesCalculation.class);
                startActivity(gotoCalories);
            }
        });

        rlWaterIntake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoWaterIntake = new Intent(getActivity(), WaterIntake.class);
                startActivity(gotoWaterIntake);
            }
        });

        ivWaterIntake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoWaterIntake = new Intent(getActivity(), WaterIntake.class);
                startActivity(gotoWaterIntake);
            }
        });

        lblWaterIntake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoWaterIntake = new Intent(getActivity(), WaterIntake.class);
                startActivity(gotoWaterIntake);
            }
        });


        rlWeightTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoWeightTracker = new Intent(getActivity(), WeightTracker.class);
                startActivity(gotoWeightTracker);
            }
        });

        ivWeightTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoWeightTracker = new Intent(getActivity(), WeightTracker.class);
                startActivity(gotoWeightTracker);
            }
        });

        lblWeightTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoWeightTracker = new Intent(getActivity(), WeightTracker.class);
                startActivity(gotoWeightTracker);
            }
        });

        return root;
    }

    public void setCurrentUserData(){
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference mCurrentUser = mDatabaseUser.child(user_id);
        mCurrentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String firstName;
                String lastName;
                String gender;
                int age;
                double currentHeight;
                double currentWeight;
                double currentWaist;
                firstName = snapshot.child("firstName").getValue().toString();
                lastName = snapshot.child("lastName").getValue().toString();
                gender = snapshot.child("gender").getValue().toString();

                age = Integer.parseInt(snapshot.child("age").getValue().toString());
                currentHeight = Double.parseDouble(snapshot.child("CurrentHeight").getValue().toString());
                currentWeight = Double.parseDouble(snapshot.child("CurrentWeight").getValue().toString());
                currentWaist  = Double.parseDouble(snapshot.child("CurrentWaist").getValue().toString());

                double newHieght = currentHeight *2.54*0.01;
                newHieght = Math.round(newHieght * 100);
                newHieght = newHieght / 100;

                double bmi = currentWeight/Math. pow(newHieght,2);
                bmi = Math.round(bmi * 100);
                bmi = bmi / 100;

                txtBMI.setText(String.format("%s", bmi));



                double bfp = 0;
                if(age < 15){
                    if (gender.equals("Male")) {
                        bfp = (1.15 * bmi) - (0.70 * age) - (3.6*1) + 1.4;
                    } else if (gender.equals("Female")) {
                        bfp = (1.15 * bmi) - (0.70 * age) - (3.6*0) + 1.4;
                    }
                }else{
                    if (gender.equals("Male")) {
                        bfp = (1.20 * bmi) + (0.23 * age) - (10.8 * 1) - 5.4;
                    } else if (gender.equals("Female")) {
                        bfp = (1.20 * bmi) + (0.23 * age) - (10.8 * 0) - 5.4;
                    }
                }

                bfp = Math.round(bfp * 100);
                bfp = bfp / 100;

                txtBFP.setText(String.format("%s", bfp));

                lblName.setText(firstName+" "+lastName);
                int[] height = new int[2];
                height[0] = (int)currentHeight/12;
                height[1] = (int)currentHeight%12;
                

                txtHeight.setText(height[0]+" ft "+height[1]+" in");
                txtWeight.setText(currentWeight+" kg");
                txtWaist.setText(currentWaist+" in");

                if(gender.equals("Male")){
                    ivBanner.setImageResource(R.drawable.home_banner_2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", "onCancelled: "+error);
            }
        });
    }


}