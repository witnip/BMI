package com.witnip.bmi.ui.home;

import android.content.Intent;
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
import com.witnip.bmi.Activities.BmiCalculation;
import com.witnip.bmi.Activities.CaloriesCalculation;
import com.witnip.bmi.Activities.Diet;
import com.witnip.bmi.Activities.IdealWeightCalculation;
import com.witnip.bmi.Activities.WaterIntake;
import com.witnip.bmi.Activities.WeightGoal;
import com.witnip.bmi.Activities.WeightTracker;
import com.witnip.bmi.FirebaseDatabaseHandler;
import com.witnip.bmi.Model.User;
import com.witnip.bmi.R;

public class HomeFragment extends Fragment {

    private TextView lblName,txtHeight,txtWeight,txtWaist,lblWaterIntake,lblWeightTracker;
    private LinearLayout btnBMI,btnWeightGoal,btnDiet,btnBFP,btnIdealWeight,btnCalories;
    private RelativeLayout rlWaterIntake,rlWeightTracker;
    private ImageView ivWaterIntake,ivWeightTracker;

    private DatabaseReference mDatabaseUser;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        lblName = root.findViewById(R.id.lblName);
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

        mDatabaseUser = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            setCurrentUserData();
        }

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
                double currentHeight;
                double currentWeight;
                double currentWaist;
                firstName = snapshot.child("firstName").getValue().toString();
                lastName = snapshot.child("lastName").getValue().toString();

                currentHeight = Double.parseDouble(snapshot.child("CurrentHeight").getValue().toString());
                currentWeight = Double.parseDouble(snapshot.child("CurrentWeight").getValue().toString());
                currentWaist  = Double.parseDouble(snapshot.child("CurrentWaist").getValue().toString());



                lblName.setText(firstName+" "+lastName);
                int[] height = new int[2];
                height[0] = (int)currentHeight/12;
                height[1] = (int)currentHeight%12;
                

                txtHeight.setText(height[0]+" ft "+height[1]+" in");
                txtWeight.setText(currentWeight+" kg");
                txtWaist.setText(currentWaist+" in");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", "onCancelled: "+error);
            }
        });
    }


}