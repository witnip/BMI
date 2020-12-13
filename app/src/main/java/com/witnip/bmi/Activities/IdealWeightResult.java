package com.witnip.bmi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.witnip.bmi.R;

public class IdealWeightResult extends AppCompatActivity {

    TextView lblIBW1,lblIBW,lblLBW,lblBSA;
    ProgressBar pbIBW;

    double ibw;
    double lbw;
    double bsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideal_weight_result);

        lblIBW1 = findViewById(R.id.lblIBW1);
        lblIBW = findViewById(R.id.lblIBW);
        lblLBW = findViewById(R.id.lblLBW);
        lblBSA = findViewById(R.id.lblBSA);

        pbIBW = findViewById(R.id.pbIBW);

        Intent data = this.getIntent();
        ibw = data.getDoubleExtra("bfp",0);
        lbw = data.getDoubleExtra("lbw",0);
        bsa = data.getDoubleExtra("bsa",0);
        setResult();
    }

    private void setResult() {
        if(ibw !=0 && lbw !=0 && bsa != 0){
            lblIBW1.setText(String.format("%s kg", ibw));
            int progress = (int)ibw;
            pbIBW.setProgress(progress);

            lblIBW.setText(String.format("%s kg", ibw));
            lblLBW.setText(String.format("%s kg", lbw));
            lblBSA.setText(String.format("%s m²", bsa));
        }
    }
}