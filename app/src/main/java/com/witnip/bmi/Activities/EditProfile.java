package com.witnip.bmi.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.witnip.bmi.DatePicker.DatePickerFragment;
import com.witnip.bmi.R;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    CircleImageView profileImage;
    TextView lblName,lblEmail,lblGender, lblDimHeight1,lblDimHeight2,lblDimWeight,lblDimWaist,lblErrorHeight,lblErrorWeight,lblErrorWaist,lblImagePath;
    EditText txtHeight1,txtHeight2,txtWeight,txtWaist,txtAge;
    ImageView ivGender;
    Spinner spGender,spHeight,spWeight,spWaist;
    Button btnUpdate;

    private static final int GALLERY_REQUEST = 1;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUser;
    private StorageReference mStorage;
    private ProgressDialog mProgress;

    String firstName;
    String lastName;
    String email;
    String gender;
    double height;
    double weight;
    double waist;
    int age;
    Uri mImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profileImage = findViewById(R.id.profile_image);
        lblName = findViewById(R.id.lblName);
        lblEmail = findViewById(R.id.lblEmail);

        ivGender = findViewById(R.id.ivGender);
        spGender = findViewById(R.id.spGender);
        lblGender = findViewById(R.id.lblGender);

        txtAge = findViewById(R.id.txtAge);

        lblDimHeight1 = findViewById(R.id.lblDimHeight1);
        lblDimHeight2 = findViewById(R.id.lblDimHeight2);
        txtHeight1 = findViewById(R.id.txtHeight1);
        txtHeight2 = findViewById(R.id.txtHeight2);
        spHeight = findViewById(R.id.spHeight);

        txtWeight = findViewById(R.id.txtWeight);
        lblDimWeight = findViewById(R.id.lblDimWeight);
        spWeight = findViewById(R.id.spWeight);

        txtWaist = findViewById(R.id.txtWaist);
        lblDimWaist = findViewById(R.id.lblDimWaist);
        spWaist = findViewById(R.id.spWaist);

        lblErrorHeight = findViewById(R.id.lblErrorHeight);
        lblErrorWeight = findViewById(R.id.lblErrorWeight);
        lblErrorWaist = findViewById(R.id.lblErrorWaist);

        lblImagePath = findViewById(R.id.lblImagePath);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users");
        mStorage = FirebaseStorage.getInstance().getReference().child("Profile_Images");

        mProgress = new ProgressDialog(this);

        btnUpdate = findViewById(R.id.btnUpdate);

        setGender();
        setDOB();
        setHeight();
        setWeight();
        setWaist();

        setInfo();

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isValidHeight() | !isValidWeight() | !isValidWaist()){
                    return;
                }else{
//                    Log.d("TAG", "onClick: hello");
                    saveData();
                }
            }
        });
    }

    private void setInfo() {
        if(mAuth.getCurrentUser() != null){
            String user_id = mAuth.getCurrentUser().getUid();
            DatabaseReference currentUserDB = mDatabaseUser.child(user_id);
            currentUserDB.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String Email = mAuth.getCurrentUser().getEmail();
                    String firstName = Objects.requireNonNull(snapshot.child("firstName").getValue()).toString();
                    String lastName = Objects.requireNonNull(snapshot.child("lastName").getValue()).toString();
                    String gender = Objects.requireNonNull(snapshot.child("gender").getValue()).toString();
                    double currentHeight = Double.parseDouble(Objects.requireNonNull(snapshot.child("CurrentHeight").getValue()).toString());
                    double currentWeight = Double.parseDouble(Objects.requireNonNull(snapshot.child("CurrentWeight").getValue()).toString());
                    double currentWaist = Double.parseDouble(Objects.requireNonNull(snapshot.child("CurrentWaist").getValue()).toString());
                    int age = Integer.parseInt(Objects.requireNonNull(snapshot.child("age").getValue()).toString());
                    String profilePath = Objects.requireNonNull(snapshot.child("profile").getValue()).toString();

                    Glide.with(EditProfile.this).load(profilePath).into(profileImage);
                    lblName.setText(firstName+" "+lastName);
                    lblEmail.setText(Email);
                    if (gender.equals("Male")) {
                        ivGender.setImageResource(R.drawable.ic_gender_male);
                        lblGender.setText("Male");
                    } else if (gender.equals("Female")) {
                        ivGender.setImageResource(R.drawable.ic_gender_female);
                        lblGender.setText("Female");
                    } else if (gender.equals("Other")) {
                        ivGender.setImageResource(R.drawable.ic_gender_other);
                        lblGender.setText("Other");
                    }

                    int[] height = new int[2];
                    height[0] = (int)currentHeight/12;
                    height[1] = (int)currentHeight%12;

                    txtAge.setText(""+age);
                    txtHeight1.setText(""+height[0]);
                    txtHeight2.setText(""+height[1]);
                    txtWeight.setText(""+currentWeight);
                    txtWaist.setText(""+currentWaist);
                    lblImagePath.setText(profilePath);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri = result.getUri();
                Glide.with(EditProfile.this).load(mImageUri).into(profileImage);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("Crop Image Error : ", ""+error);
            }
        }
    }

    private void setGender() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.gender,android.R.layout.simple_spinner_item);
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
                } else if (gender.equals("Other")) {
                    ivGender.setImageResource(R.drawable.ic_gender_other);
                    lblGender.setText("Other");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setDOB() {
        txtAge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
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

        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        age = currentYear - year;
        txtAge.setText(""+age);
    }

    private void setHeight() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.height,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHeight.setAdapter(adapter);

        spHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String dimensionType = parent.getItemAtPosition(position).toString();
                if(dimensionType.equals("Imperial(ft,in)")){
                    lblDimHeight1.setText("ft");
                    lblDimHeight2.setText("in");
                    txtHeight2.setVisibility(View.VISIBLE);
                    lblDimHeight2.setVisibility(View.VISIBLE);
                }else if(dimensionType.equals("Metric(cm)")){
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

    private void setWeight(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.weight,android.R.layout.simple_spinner_item);
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

    private void setWaist(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.waist,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWaist.setAdapter(adapter);

        spWaist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String dimensionType = parent.getItemAtPosition(position).toString();
                lblDimWaist.setText(dimensionType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private double getHeight() {
        double height = 0;


        String dimensionType = spHeight.getSelectedItem().toString();
        if(dimensionType.equals("Imperial(ft,in)")){
            double height1 = Double.parseDouble(txtHeight1.getText().toString().trim());
            double height2 = Double.parseDouble(txtHeight2.getText().toString().trim());
            height = (height1 * 12) + height2;
            height = Math.round(height * 100);
            height = height/100;
        }else if(dimensionType.equals("Metric(cm)")){
            double height1 = Double.parseDouble(txtHeight1.getText().toString().trim());
            height = height1 * 0.394;
            height = Math.round(height * 100);
            height = height/100;
        }
        return height;
    }

    private double getWeight() {
        double weight = 0;
        String dimensionType = spWeight.getSelectedItem().toString();
        if(dimensionType.equals("kg")){
            weight = Double.parseDouble(txtWeight.getText().toString().trim());
            weight = Math.round(weight * 100);
            weight = weight/100;
        }else if(dimensionType.equals("lbs")){
            double w = Double.parseDouble(txtWeight.getText().toString().trim());
            weight = w * 0.4536;
            weight = Math.round(weight * 100);
            weight = weight/100;
        }
        return weight;
    }

    private double getWaist() {
        double waist = 0;
        String dimensionType = spWaist.getSelectedItem().toString();
        if(dimensionType.equals("in")){
            waist = Double.parseDouble(txtWaist.getText().toString().trim());
            waist = Math.round(waist * 100);
            waist = waist/100;
        }else if(dimensionType.equals("cm")){
            double waist1 = Double.parseDouble(txtWaist.getText().toString().trim());
            waist = waist1 * 0.394;
            waist = Math.round(waist * 100);
            waist = waist/100;
        }
        return waist;
    }

    private boolean isValidHeight() {
        boolean valid = true;
        lblErrorHeight.setVisibility(View.VISIBLE);
        String height1 = txtHeight1.getText().toString().trim();
        if(txtHeight2.getVisibility() == View.VISIBLE) {
            String height2 = txtHeight2.getText().toString().trim();
            if (TextUtils.isEmpty(height1) | TextUtils.isEmpty(height2)) {

                lblErrorHeight.setText("Fields can't be empty");
                valid = false;
            } else {
                double h1 = Double.parseDouble(txtHeight1.getText().toString().trim());
                double h2 = Double.parseDouble(txtHeight2.getText().toString().trim());

                if(h1 > 9 && h2 > 11){
                    lblErrorHeight.setText("Height can't be greater than 9 ft and 11 in");
                    valid = false;
                }else{
                    valid = true;
                    lblErrorHeight.setText("");
                    lblErrorHeight.setVisibility(View.GONE);
                }
            }
        }else{
            if(TextUtils.isEmpty(height1)){
                lblErrorHeight.setText("Fields can't be empty");
                valid = false;
            }else{
                double h1 = Double.parseDouble(txtHeight1.getText().toString().trim());
                if(h1 > 300){
                    lblErrorHeight.setText("Height can't be greater than 300 cm");
                    valid = false;
                }else{
                    valid = true;
                    lblErrorHeight.setText("");
                    lblErrorHeight.setVisibility(View.GONE);
                }
            }
        }
        return valid;
    }

    private boolean isValidWeight(){
        boolean valid = true;
        lblErrorWeight.setVisibility(View.VISIBLE);
        String weight = txtWeight.getText().toString().trim();
        if(TextUtils.isEmpty(weight)){

            lblErrorWeight.setText("Fields can't be empty");
            valid = false;
        }else{
            double w = Double.parseDouble(txtWeight.getText().toString().trim());
            if(w > 1000){
                lblErrorWeight.setText("Height can't be greater than 1000"+lblDimWeight.getText().toString().trim());
                valid = false;
            }else{
                valid = true;
                lblErrorWeight.setText("");
                lblErrorWeight.setVisibility(View.GONE);
            }
        }
        return valid;
    }

    private boolean isValidWaist(){
        boolean valid = true;
        lblErrorWaist.setVisibility(View.VISIBLE);
        String waist = txtWaist.getText().toString().trim();
        if(TextUtils.isEmpty(waist)){
            lblErrorWaist.setText("Fields can't be empty");
            valid = false;
        }else{
            double w = Double.parseDouble(txtWaist.getText().toString().trim());
            if(w > 300){
                lblErrorWaist.setText("Height can't be greater than 300"+lblDimWaist.getText().toString().trim());
                valid = false;
            }else{
                valid = true;
                lblErrorWaist.setText("");
                lblErrorWaist.setVisibility(View.GONE);
            }
        }
        return valid;
    }

    private void saveData() {
        if(mAuth.getCurrentUser() != null){
            if(mImageUri != null ){

                mProgress.setMessage("Updating...");
                mProgress.show();
                StorageReference filepath = mStorage.child(mImageUri.getLastPathSegment());
                filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (taskSnapshot.getMetadata() != null) {
                            if (taskSnapshot.getMetadata().getReference() != null) {
                                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String name = lblName.getText().toString();
                                        String [] newName = name.split(" ",2);
                                        firstName = newName[0];
                                        lastName = newName[1];
                                        email = lblEmail.getText().toString();
                                        gender = spGender.getSelectedItem().toString();
                                        age = Integer.parseInt(txtAge.getText().toString());
                                        //Height in inches
                                        height = getHeight();
                                        //Weight in kg
                                        weight = getWeight();
                                        //Waist in inches
                                        waist = getWaist();
//                                            String profilePath = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                                        String profilePath = uri.toString();
                                        String user_id = mAuth.getCurrentUser().getUid();
                                        DatabaseReference currentUserDB = mDatabaseUser.child(user_id);
                                        currentUserDB.child("firstName").setValue(firstName);
                                        currentUserDB.child("lastName").setValue(lastName);
                                        currentUserDB.child("gender").setValue(gender);
                                        currentUserDB.child("age").setValue(age);
                                        currentUserDB.child("CurrentHeight").setValue(height);
                                        currentUserDB.child("CurrentWeight").setValue(weight);
                                        currentUserDB.child("CurrentWaist").setValue(waist);
                                        currentUserDB.child("profile").setValue(profilePath);

                                        mProgress.dismiss();

                                        Intent profileIntent = new Intent(EditProfile.this, Profile.class);
                                        profileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(profileIntent);
                                        finish();
                                    }
                                });
                            }
                        }
                    }
                });

            }else{
                mProgress.setMessage("Updating...");
                mProgress.show();
                String name = lblName.getText().toString();
                String [] newName = name.split(" ",2);
                firstName = newName[0];
                lastName = newName[1];
                email = lblEmail.getText().toString();
                gender = spGender.getSelectedItem().toString();
                age = Integer.parseInt(txtAge.getText().toString());
                //Height in inches
                height = getHeight();
                //Weight in kg
                weight = getWeight();
                //Waist in inches
                waist = getWaist();
                String user_id = mAuth.getCurrentUser().getUid();
                DatabaseReference currentUserDB = mDatabaseUser.child(user_id);
                currentUserDB.child("firstName").setValue(firstName);
                currentUserDB.child("lastName").setValue(lastName);
                currentUserDB.child("gender").setValue(gender);
                currentUserDB.child("age").setValue(age);
                currentUserDB.child("CurrentHeight").setValue(height);
                currentUserDB.child("CurrentWeight").setValue(weight);
                currentUserDB.child("CurrentWaist").setValue(waist);

                mProgress.dismiss();

                Intent profileIntent = new Intent(EditProfile.this, Profile.class);
                profileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(profileIntent);
                finish();
            }
        }
    }
}