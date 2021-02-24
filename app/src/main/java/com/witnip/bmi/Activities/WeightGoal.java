package com.witnip.bmi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.witnip.bmi.R;

public class WeightGoal extends AppCompatActivity {

    EditText txtStartingWeight, txtCurrentWeight, txtGoalWeight;
    TextView lblDimStartingWeight, lblDimCurrentWeight, lblDimGoalWeight, lblErrorStartingWeight, lblErrorCurrentWeight, lblErrorGoalWeight,
            lblProgressValue, lblProgressWeightLoss, lblProgressWeightLossTarget, lblProgressTotalWeightLoss;
    Spinner spStartingWeight, spCurrentWeight, spGoalWeight;

    Button btnCalculate, btnReset;

    ProgressBar pbProgress;

    RelativeLayout rlProgress;

    double weightStarting;
    double weightCurrent;
    double weightGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_goal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtStartingWeight = findViewById(R.id.txtStartingWeight);
        lblDimStartingWeight = findViewById(R.id.lblDimStartingWeight);
        spStartingWeight = findViewById(R.id.spStartingWeight);
        lblErrorStartingWeight = findViewById(R.id.lblErrorStartingWeight);

        txtCurrentWeight = findViewById(R.id.txtCurrentWeight);
        lblDimCurrentWeight = findViewById(R.id.lblDimCurrentWeight);
        spCurrentWeight = findViewById(R.id.spCurrentWeight);
        lblErrorCurrentWeight = findViewById(R.id.lblErrorCurrentWeight);

        txtGoalWeight = findViewById(R.id.txtGoalWeight);
        lblDimGoalWeight = findViewById(R.id.lblDimGoalWeight);
        spGoalWeight = findViewById(R.id.spGoalWeight);
        lblErrorGoalWeight = findViewById(R.id.lblErrorGoalWeight);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);

        pbProgress = findViewById(R.id.pbProgress);
        lblProgressValue = findViewById(R.id.lblProgressValue);
        lblProgressWeightLoss = findViewById(R.id.lblProgressWeightLoss);
        lblProgressWeightLossTarget = findViewById(R.id.lblProgressWeightLossTarget);
        lblProgressTotalWeightLoss = findViewById(R.id.lblProgressTotalWeightLoss);

        rlProgress = findViewById(R.id.rlProgress);

        setStartingWeight();
        setCurrentWeight();
        setGoalWeight();

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidStartingWeight() | !isValidCurrentWeight() | !isValidGoalWeight()) {
                    return;
                } else {
                    calculateWeightGoal();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtStartingWeight.setText("");
                txtCurrentWeight.setText("");
                txtGoalWeight.setText("");

                rlProgress.setVisibility(View.GONE);
            }
        });
    }


    private void setStartingWeight() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.weight, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStartingWeight.setAdapter(adapter);

        spStartingWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String dimensionType = parent.getItemAtPosition(position).toString();
                lblDimStartingWeight.setText(dimensionType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setCurrentWeight() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.weight, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCurrentWeight.setAdapter(adapter);

        spCurrentWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String dimensionType = parent.getItemAtPosition(position).toString();
                lblDimCurrentWeight.setText(dimensionType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setGoalWeight() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.weight, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGoalWeight.setAdapter(adapter);

        spGoalWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String dimensionType = parent.getItemAtPosition(position).toString();
                lblDimGoalWeight.setText(dimensionType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private double getStartingWeight() {
        double weight = 0;
        String dimensionType = spStartingWeight.getSelectedItem().toString();
        if (dimensionType.equals("kg")) {
            weight = Double.parseDouble(txtStartingWeight.getText().toString().trim());
            weight = Math.round(weight * 100);
            weight = weight / 100;
        } else if (dimensionType.equals("lbs")) {
            double w = Double.parseDouble(txtStartingWeight.getText().toString().trim());
            weight = w * 0.4536;
            weight = Math.round(weight * 100);
            weight = weight / 100;
        }
        return weight;
    }

    private double getCurrentWeight() {
        double weight = 0;
        String dimensionType = spCurrentWeight.getSelectedItem().toString();
        if (dimensionType.equals("kg")) {
            weight = Double.parseDouble(txtCurrentWeight.getText().toString().trim());
            weight = Math.round(weight * 100);
            weight = weight / 100;
        } else if (dimensionType.equals("lbs")) {
            double w = Double.parseDouble(txtCurrentWeight.getText().toString().trim());
            weight = w * 0.4536;
            weight = Math.round(weight * 100);
            weight = weight / 100;
        }
        return weight;
    }

    private double getGoalWeight() {
        double weight = 0;
        String dimensionType = spGoalWeight.getSelectedItem().toString();
        if (dimensionType.equals("kg")) {
            weight = Double.parseDouble(txtGoalWeight.getText().toString().trim());
            weight = Math.round(weight * 100);
            weight = weight / 100;
        } else if (dimensionType.equals("lbs")) {
            double w = Double.parseDouble(txtGoalWeight.getText().toString().trim());
            weight = w * 0.4536;
            weight = Math.round(weight * 100);
            weight = weight / 100;
        }
        return weight;
    }

    private boolean isValidStartingWeight() {
        boolean valid = true;
        lblErrorStartingWeight.setVisibility(View.VISIBLE);
        String weight = txtStartingWeight.getText().toString().trim();
        if (TextUtils.isEmpty(weight)) {

            lblErrorStartingWeight.setText("Fields can't be empty");
            valid = false;
        } else {
            double w = Double.parseDouble(txtStartingWeight.getText().toString().trim());
            if (w > 1000) {
                lblErrorStartingWeight.setText("Height can't be greater than 1000" + lblDimStartingWeight.getText().toString().trim());
                valid = false;
            } else {
                valid = true;
                lblErrorStartingWeight.setText("");
                lblErrorStartingWeight.setVisibility(View.GONE);
            }
        }
        return valid;
    }

    private boolean isValidCurrentWeight() {
        boolean valid = true;
        lblErrorCurrentWeight.setVisibility(View.VISIBLE);
        String weight = txtCurrentWeight.getText().toString().trim();
        if (TextUtils.isEmpty(weight)) {

            lblErrorCurrentWeight.setText("Fields can't be empty");
            valid = false;
        } else {
            double w = Double.parseDouble(txtCurrentWeight.getText().toString().trim());
            if (w > 1000) {
                lblErrorCurrentWeight.setText("Height can't be greater than 1000" + lblDimCurrentWeight.getText().toString().trim());
                valid = false;
            } else {
                valid = true;
                lblErrorCurrentWeight.setText("");
                lblErrorCurrentWeight.setVisibility(View.GONE);
            }
        }
        return valid;
    }

    private boolean isValidGoalWeight() {
        boolean valid = true;
        lblErrorGoalWeight.setVisibility(View.VISIBLE);
        String weight = txtGoalWeight.getText().toString().trim();
        if (TextUtils.isEmpty(weight)) {

            lblErrorGoalWeight.setText("Fields can't be empty");
            valid = false;
        } else {
            double w = Double.parseDouble(txtGoalWeight.getText().toString().trim());
            if (w > 1000) {
                lblErrorGoalWeight.setText("Height can't be greater than 1000" + lblDimGoalWeight.getText().toString().trim());
                valid = false;
            } else {
                valid = true;
                lblErrorGoalWeight.setText("");
                lblErrorGoalWeight.setVisibility(View.GONE);
            }
        }
        return valid;
    }

    private void calculateWeightGoal() {
        weightStarting = getStartingWeight();
        weightCurrent = getCurrentWeight();
        weightGoal = getGoalWeight();

        double weightLossSoFar,totalWeightLoss,weightLossTarget,weightLossProgress;

        double weightLossSoFar1 = ((weightStarting - weightCurrent) / weightStarting) * 100;
        weightLossSoFar = Math.round(weightLossSoFar1 * 100);
        weightLossSoFar = weightLossSoFar / 100;

        double totalWeightLoss1 = ((weightStarting - weightGoal) / weightStarting) * 100;
        totalWeightLoss = Math.round(totalWeightLoss1 * 100);
        totalWeightLoss = totalWeightLoss / 100;

        double weightLossTarget1 = ((weightCurrent - weightGoal) / weightCurrent) * 100;
        weightLossTarget = Math.round(weightLossTarget1 * 100);
        weightLossTarget = weightLossTarget / 100;

        double weightLossProgress1 = (weightLossSoFar / totalWeightLoss) * 100;
        weightLossProgress = Math.round(weightLossProgress1 * 100);
        weightLossProgress = weightLossProgress / 100;

        int progress = (int) weightLossProgress;

        rlProgress.setVisibility(View.VISIBLE);
        pbProgress.setProgress(progress);
        lblProgressValue.setText(String.format("%s%%", weightLossProgress));
        lblProgressWeightLoss.setText(String.format("Percentage of weight loss so far is %s%%", weightLossSoFar));
        lblProgressWeightLossTarget.setText(String.format("Percentage of weight loss still required to reach the target weight is %s%%", weightLossTarget));
        lblProgressTotalWeightLoss.setText(String.format("Total percentage of weight lost after reaching the target weight is %s%%", totalWeightLoss));
    }
}