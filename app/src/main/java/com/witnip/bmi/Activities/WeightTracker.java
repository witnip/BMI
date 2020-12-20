package com.witnip.bmi.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.witnip.bmi.Dialog.WeightDialog;
import com.witnip.bmi.R;

public class WeightTracker extends AppCompatActivity implements WeightDialog.WeightTrackerDialogListener {

    TextView lblMinValue,lblAvgValue,lblMaxValue,lblBMI;
    Button btnAdd,btnViewGraph;

    RecyclerView rvWeightTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_tracker);
    }

    @Override
    public void setWeight(Double weight) {

    }
}