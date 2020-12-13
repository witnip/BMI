package com.witnip.bmi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.witnip.bmi.R;

public class BmiCalculation extends AppCompatActivity {

    Button btnCalculate;
    Spinner spHeight,spWeight;
    TextView lblDimHeight1,lblDimHeight2,lblDimWeight,lblErrorHeight, lblErrorWeight,lblReset;
    EditText txtHeight1,txtHeight2,txtWeight;

    double height;
    double weight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculation);

        btnCalculate = findViewById(R.id.btnCalculate);

        spHeight = findViewById(R.id.spHeight);
        spWeight = findViewById(R.id.spWeight);


        txtHeight1 = findViewById(R.id.txtHeight1);
        txtHeight2 = findViewById(R.id.txtHeight2);
        txtWeight = findViewById(R.id.txtWeight);

        lblDimHeight1 = findViewById(R.id.lblDimHeight1);
        lblDimHeight2 = findViewById(R.id.lblDimHeight2);
        lblDimWeight = findViewById(R.id.lblDimWeight);
        lblErrorHeight = findViewById(R.id.lblErrorHeight);
        lblErrorWeight = findViewById(R.id.lblErrorWeight);
        lblReset = findViewById(R.id.lblReset);

        setHeight();
        setWeight();

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidHeight() | !isValidWeight()) {
                    return;
                } else {
                    calculateBMI();
                }
            }
        });

        lblReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtHeight1.setText("");
                txtHeight2.setText("");
                txtWeight.setText("");
                lblErrorHeight.setVisibility(View.GONE);
                lblErrorWeight.setVisibility(View.GONE);
            }
        });
    }



    private void setHeight() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.height, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHeight.setAdapter(adapter);

        spHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String dimensionType = parent.getItemAtPosition(position).toString();
                if (dimensionType.equals("Imperial(ft,in)")) {
                    lblDimHeight1.setText("ft");
                    lblDimHeight2.setText("in");
                    txtHeight2.setVisibility(View.VISIBLE);
                    lblDimHeight2.setVisibility(View.VISIBLE);
                } else if (dimensionType.equals("Metric(cm)")) {
                    lblDimHeight1.setText("cm");
                    lblDimHeight2.setVisibility(View.GONE);
                    txtHeight2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setWeight() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.weight, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWeight.setAdapter(adapter);

        spWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String dimensionType = parent.getItemAtPosition(position).toString();
                lblDimWeight.setText(dimensionType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private double getHeight() {
        double height = 0;
        String dimensionType = spHeight.getSelectedItem().toString();
        if (dimensionType.equals("Imperial(ft,in)")) {
            double height1 = Double.parseDouble(txtHeight1.getText().toString().trim());
            double height2 = Double.parseDouble(txtHeight2.getText().toString().trim());
            height = ((height1 * 12) + height2)*2.54*0.01;
            height = Math.round(height * 100);
            height = height / 100;
        } else if (dimensionType.equals("Metric(cm)")) {
            double height1 = Double.parseDouble(txtHeight1.getText().toString().trim());
            height = height1 * 0.01;
            height = Math.round(height * 100);
            height = height / 100;
        }
        return height;
    }

    private double getWeight() {
        double weight = 0;
        String dimensionType = spWeight.getSelectedItem().toString();
        if (dimensionType.equals("kg")) {
            weight = Double.parseDouble(txtWeight.getText().toString().trim());
            weight = Math.round(weight * 100);
            weight = weight / 100;
        } else if (dimensionType.equals("lbs")) {
            double w = Double.parseDouble(txtWeight.getText().toString().trim());
            weight = w * 0.4536;
            weight = Math.round(weight * 100);
            weight = weight / 100;
        }
        return weight;
    }

    private boolean isValidHeight() {
        boolean valid = true;
        lblErrorHeight.setVisibility(View.VISIBLE);
        String height1 = txtHeight1.getText().toString().trim();
        if (txtHeight2.getVisibility() == View.VISIBLE) {
            String height2 = txtHeight2.getText().toString().trim();
            if (TextUtils.isEmpty(height1) | TextUtils.isEmpty(height2)) {

                lblErrorHeight.setText("Fields can't be empty");
                valid = false;
            } else {
                double h1 = Double.parseDouble(txtHeight1.getText().toString().trim());
                double h2 = Double.parseDouble(txtHeight2.getText().toString().trim());

                if (h1 > 9 && h2 > 11) {
                    lblErrorHeight.setText("Height can't be greater than 9 ft and 11 in");
                    valid = false;
                } else {
                    valid = true;
                    lblErrorHeight.setText("");
                    lblErrorHeight.setVisibility(View.GONE);
                }
            }
        } else {
            if (TextUtils.isEmpty(height1)) {
                lblErrorHeight.setText("Fields can't be empty");
                valid = false;
            } else {
                double h1 = Double.parseDouble(txtHeight1.getText().toString().trim());
                if (h1 > 300) {
                    lblErrorHeight.setText("Height can't be greater than 300 cm");
                    valid = false;
                } else {
                    valid = true;
                    lblErrorHeight.setText("");
                    lblErrorHeight.setVisibility(View.GONE);
                }
            }
        }
        return valid;
    }

    private boolean isValidWeight() {
        boolean valid = true;
        lblErrorWeight.setVisibility(View.VISIBLE);
        String weight = txtWeight.getText().toString().trim();
        if (TextUtils.isEmpty(weight)) {

            lblErrorWeight.setText("Fields can't be empty");
            valid = false;
        } else {
            double w = Double.parseDouble(txtWeight.getText().toString().trim());
            if (w > 1000) {
                lblErrorWeight.setText("Height can't be greater than 1000" + lblDimWeight.getText().toString().trim());
                valid = false;
            } else {
                valid = true;
                lblErrorWeight.setText("");
                lblErrorWeight.setVisibility(View.GONE);
            }
        }
        return valid;
    }

    private void calculateBMI(){
        //Height in meter
        height = getHeight();
        //Weight in kg
        weight = getWeight();

        double bmi = getBmi();

        Intent gotoBMIResult = new Intent(this,BmiResult.class);
        gotoBMIResult.putExtra("bmi",bmi);
        startActivity(gotoBMIResult);
    }

    private double getBmi() {
        double bmi = weight/Math. pow(height,2);
        bmi = Math.round(bmi * 100);
        bmi = bmi / 100;
        return bmi;
    }
}