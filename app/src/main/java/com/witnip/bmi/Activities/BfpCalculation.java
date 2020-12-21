package com.witnip.bmi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.witnip.bmi.DatePicker.DatePickerFragment;
import com.witnip.bmi.R;

import java.text.DateFormat;
import java.util.Calendar;

public class BfpCalculation extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView lblGender, lblDimHeight1, lblDimHeight2, lblDimWeight, lblErrorHeight, lblErrorWeight, lblDOB;
    EditText txtHeight1, txtHeight2, txtWeight;
    ImageView ivGender;
    Spinner spGender, spHeight, spWeight;
    Button btnCalculate,btnReset;

    String gender;
    double height;
    double weight;
    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bfp_calculation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivGender = findViewById(R.id.ivGender);
        lblGender = findViewById(R.id.lblGender);
        spGender = findViewById(R.id.spGender);

        lblDOB = findViewById(R.id.lblDOB);

        lblDimHeight1 = findViewById(R.id.lblDimHeight1);
        lblDimHeight2 = findViewById(R.id.lblDimHeight2);
        txtHeight1 = findViewById(R.id.txtHeight1);
        txtHeight2 = findViewById(R.id.txtHeight2);
        spHeight = findViewById(R.id.spHeight);

        txtWeight = findViewById(R.id.txtWeight);
        lblDimWeight = findViewById(R.id.lblDimWeight);
        spWeight = findViewById(R.id.spWeight);


        lblErrorHeight = findViewById(R.id.lblErrorHeight);
        lblErrorWeight = findViewById(R.id.lblErrorWeight);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);

        setGender();
        setDOB();
        setHeight();
        setWeight();

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidHeight() | !isValidWeight()) {
                    return;
                } else {
                    calculateBFP();
                }
            }
        });
    }



    private void setGender() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender1, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(adapter);

        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String gender = parent.getItemAtPosition(position).toString();
                if (gender.equals("Male")) {
                    ivGender.setImageResource(R.drawable.ic_gender_male);
                    lblGender.setText("Male");
                } else if (gender.equals("Female")) {
                    ivGender.setImageResource(R.drawable.ic_gender_female);
                    lblGender.setText("Female");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setDOB() {
        lblDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        age = currentYear - year;
        lblDOB.setText(currentDate);
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

    private void calculateBFP() {
        gender = spGender.getSelectedItem().toString();
        //Height in meter
        height = getHeight();
        //Weight in kg
        weight = getWeight();

        double bmi = getBmi();

        double bfp = getBFP(bmi);

        Intent gotoBFPResult = new Intent(this,BfpResult.class);
        gotoBFPResult.putExtra("gender",gender);
        gotoBFPResult.putExtra("bfp",bfp);
        startActivity(gotoBFPResult);

    }

    private double getBFP(double bmi) {
        double bfp =0;
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
        return bfp;
    }

    private double getBmi() {
        double bmi = weight/Math. pow(height,2);
        bmi = Math.round(bmi * 100);
        bmi = bmi / 100;
        return bmi;
    }

}