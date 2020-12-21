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
<<<<<<< HEAD
=======
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lblMinValue = findViewById(R.id.lblMinValue);
        lblAvgValue = findViewById(R.id.lblAvgValue);
        lblMaxValue = findViewById(R.id.lblMaxValue);
        lblBMI = findViewById(R.id.lblBMI);
        btnAdd = findViewById(R.id.btnAdd);
        btnViewGraph = findViewById(R.id.btnViewGraph);
        rvWeightTracker = findViewById(R.id.rvWeightTracker);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        trackerModels = new ArrayList<>();
        rvWeightTracker.setLayoutManager(new LinearLayoutManager(this));
        setAvgMinMax();
        setDailyWeight();

        btnViewGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoGraph = new Intent(WeightTracker.this,WeightTrackerGraph.class);
                startActivity(gotoGraph);
            }
        });
    }

    private void setAvgMinMax() {
        String user_id = mAuth.getCurrentUser().getUid();
        final DatabaseReference currentUserDB = mDatabaseUser.child(user_id);
        DatabaseReference weightTrackerDB = currentUserDB.child("weightTracker");
        final DatabaseReference daily = weightTrackerDB.child("daily");
        weightTrackerDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trackerModels.clear();
                if(snapshot.hasChild("currentDate")) {
                    daily.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            double totalWeight = 0, maxWeight, minWeight, avgWeight;
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                WeightTrackerModel model = dataSnapshot.getValue(WeightTrackerModel.class);
                                trackerModels.add(model);
                            }
                            Comparator<WeightTrackerModel> compareWeight = new CompareWeight();
                            Collections.sort(trackerModels, compareWeight);
                            for (WeightTrackerModel model : trackerModels) {
                                totalWeight = totalWeight + model.getWeight();
                            }
                            avgWeight = totalWeight / trackerModels.size();
                            minWeight = trackerModels.get(0).getWeight();
                            maxWeight = trackerModels.get(trackerModels.size() - 1).getWeight();
                            lblMinValue.setText(String.format("%s", minWeight));
                            lblAvgValue.setText(String.format("%s", avgWeight));
                            lblMaxValue.setText(String.format("%s", maxWeight));
                            currentUserDB.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    double CurrentWeight = Double.parseDouble(snapshot.child("CurrentWeight").getValue().toString());
                                    double CurrentHeight = Double.parseDouble(snapshot.child("CurrentHeight").getValue().toString());
                                    double height = CurrentHeight *2.54*0.01;
                                    height = Math.round(height * 100);
                                    height = height / 100;
                                    double bmi = CurrentWeight/Math. pow(height,2);
                                    bmi = Math.round(bmi * 100);
                                    bmi = bmi / 100;
                                    lblBMI.setText(String.format("%s", bmi));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(WeightTracker.this, "Failed!! to get data", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(WeightTracker.this, "Failed!! to get data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if(!snapshot.hasChild("currentDate")){
                    currentUserDB.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            double CurrentWeight = Double.parseDouble(snapshot.child("CurrentWeight").getValue().toString());
                            double CurrentHeight = Double.parseDouble(snapshot.child("CurrentHeight").getValue().toString());
                            double bmi = CurrentWeight/Math. pow(CurrentHeight,2);
                            bmi = Math.round(bmi * 100);
                            bmi = bmi / 100;
                            lblMinValue.setText(String.format("%s", CurrentWeight));
                            lblAvgValue.setText(String.format("%s", CurrentWeight));
                            lblMaxValue.setText(String.format("%s", CurrentWeight));
                            lblBMI.setText(String.format("%s", bmi));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(WeightTracker.this, "Failed!! to get data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeightTracker.this, "Failed!! to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDailyWeight() {
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference currentUserDB = mDatabaseUser.child(user_id);
        DatabaseReference weightTrackerDB = currentUserDB.child("weightTracker");
        DatabaseReference daily = weightTrackerDB.child("daily");
        daily.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trackerModels.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    WeightTrackerModel model = dataSnapshot.getValue(WeightTrackerModel.class);
                    trackerModels.add(model);
                }
                Collections.reverse(trackerModels);
                adapter = new WeightTrackerAdapter(WeightTracker.this,trackerModels);
                rvWeightTracker.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeightTracker.this, "Failed!! to save data", Toast.LENGTH_SHORT).show();
            }
        });
>>>>>>> 474fc51... back button set
    }

    @Override
    public void setWeight(Double weight) {

    }
}