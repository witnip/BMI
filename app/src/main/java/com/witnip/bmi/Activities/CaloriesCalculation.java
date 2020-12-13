package com.witnip.bmi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.witnip.bmi.DatePicker.DatePickerFragment;
import com.witnip.bmi.R;

import java.text.DateFormat;
import java.util.Calendar;

public class CaloriesCalculation extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView lblGender, lblDimHeight1, lblDimHeight2, lblDimWeight, lblErrorHeight, lblErrorWeight, lblErrorDOB, lblDOB,lblBMRValue,lblDCCValue,lblMaxDCCValue,lblMaxDCC;
    EditText txtHeight1, txtHeight2, txtWeight;
    ImageView ivGender;
    Spinner spGender, spHeight, spWeight,spExerciseLevel;
    Button btnCalculate,btnReset;

    RelativeLayout rlMaxDCC,rlDCC,rlBMR;

    String gender;
    double height;
    double weight;
    int age;
    double [] pal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_calculation);

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

        spExerciseLevel = findViewById(R.id.spExerciseLevel);

        lblErrorHeight = findViewById(R.id.lblErrorHeight);
        lblErrorWeight = findViewById(R.id.lblErrorWeight);
        lblErrorDOB = findViewById(R.id.lblErrorDOB);

        lblBMRValue = findViewById(R.id.lblBMRValue);
        lblDCCValue = findViewById(R.id.lblDCCValue);
        lblMaxDCCValue = findViewById(R.id.lblMaxDCCValue);

        rlBMR = findViewById(R.id.rlBMR);
        lblMaxDCC = findViewById(R.id.lblMaxDCC);
        rlMaxDCC = findViewById(R.id.rlMaxDCC);

        rlDCC = findViewById(R.id.rlDCC);


        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);

        setGender();
        setDOB();
        setHeight();
        setWeight();
        setExerciseLevel();

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidHeight() | !isValidWeight()) {
                    return;
                } else {
                    calculateDailyCalories();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlBMR.setVisibility(View.GONE);
                rlDCC.setVisibility(View.GONE);
                rlMaxDCC.setVisibility(View.GONE);

                txtHeight1.setText("");
                txtHeight2.setText("");
                txtWeight.setText("");

                lblDOB.setText("DOB");
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

    private void setExerciseLevel(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.pal, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spExerciseLevel.setAdapter(adapter);
    }


    private double getHeight() {
        double height = 0;
        String dimensionType = spHeight.getSelectedItem().toString();
        if (dimensionType.equals("Imperial(ft,in)")) {
            double height1 = Double.parseDouble(txtHeight1.getText().toString().trim());
            double height2 = Double.parseDouble(txtHeight2.getText().toString().trim());
            height = (height1 * 12) + height2;
            height = Math.round(height * 100);
            height = height / 100;
        } else if (dimensionType.equals("Metric(cm)")) {
            double height1 = Double.parseDouble(txtHeight1.getText().toString().trim());
            height = height1 * 0.394;
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

    private double [] getPAL(){
        double pal[] = new double[2];
        String exerciseLevel = spExerciseLevel.getSelectedItem().toString();
        if(exerciseLevel.equals("Extremely Inactive")){
            pal[0] = 0;
            pal[1] = 1.39;
        }else if(exerciseLevel.equals("Sedentary with No Exercise")){
            pal[0] = 1.40;
            pal[1] = 1.55;
        }else if(exerciseLevel.equals("Sedentary with Little Exercise")){
            pal[0] = 1.56;
            pal[1] = 1.69;
        }else if(exerciseLevel.equals("Moderately Active")){
            pal[0] = 1.70;
            pal[1] = 1.99;
        }else if(exerciseLevel.equals("Vigorously Active")){
            pal[0] = 2.00;
            pal[1] = 2.39;
        }else if(exerciseLevel.equals("Extremely Active")){
            pal[0] = 2.40;
            pal[1] = 0;
        }
        return pal;
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

    private void calculateDailyCalories() {
        gender = spGender.getSelectedItem().toString();
        //Height in meter
        height = getHeight();
        //Weight in kg
        weight = getWeight();

        pal = getPAL();

        double bmr = getBMR();
        double minCalories = bmr * pal[0];
        minCalories = Math.round(minCalories * 100);
        minCalories = minCalories / 100;

        double maxCalories = bmr* pal[1];
        maxCalories = Math.round(maxCalories * 100);
        maxCalories = maxCalories / 100;

        rlBMR.setVisibility(View.VISIBLE);
        rlMaxDCC.setVisibility(View.VISIBLE);
        if(minCalories!=0 && maxCalories != 0){
            rlDCC.setVisibility(View.VISIBLE);


            lblBMRValue.setText(String.format("%s", bmr));
            lblDCCValue.setText(String.format("%s", minCalories));
            lblMaxDCCValue.setText(String.format("%s", maxCalories));
        }
        else if(maxCalories == 0){

            rlDCC.setVisibility(View.GONE);

            lblBMRValue.setText(String.format("%s", bmr));
            lblMaxDCCValue.setText(String.format("%s", minCalories));
            lblMaxDCC.setText("Daily Calorie Count");
        }else if(minCalories == 0){
            rlDCC.setVisibility(View.GONE);

            lblBMRValue.setText(String.format("%s", bmr));
            lblMaxDCCValue.setText(String.format("%s", maxCalories));
            lblMaxDCC.setText("Daily Calorie Count");
        }

    }

    private double getBMR() {
        double bmr = 0;
        if (gender.equals("Male")) {
            bmr = (10.0 * weight) + (6.25 * height) - (5.0 * age) + 5;
        } else if (gender.equals("Female")) {
            bmr = (10.0 * weight) + (6.25 * height) - (5.0 * age) - 161;
        }
        return bmr;
    }
}