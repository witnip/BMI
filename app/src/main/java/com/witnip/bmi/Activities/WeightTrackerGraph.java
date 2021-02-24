package com.witnip.bmi.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.witnip.bmi.Model.WaterIntakeGraphModel;
import com.witnip.bmi.Model.WaterIntakeModel;
import com.witnip.bmi.Model.WeightTrackerGraphModel;
import com.witnip.bmi.Model.WeightTrackerModel;
import com.witnip.bmi.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeightTrackerGraph extends AppCompatActivity {

    BarChart barGraphWeightTracker;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUser;

    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

    ArrayList<WeightTrackerGraphModel> weightGraphModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_tracker_graph);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barGraphWeightTracker = findViewById(R.id.barGraphWeightTracker);

        weightGraphModels = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users");
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference currentUserDB = mDatabaseUser.child(user_id);

        DatabaseReference waterIntakeDB = currentUserDB.child("weightTracker");
        DatabaseReference daily = waterIntakeDB.child("daily");
        daily.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double weight1 = 0;
                double weight2 = 0;
                double weight3 = 0;
                double weight4 = 0;
                double weight5 = 0;
                double weight6 = 0;
                double weight7 = 0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    WeightTrackerModel model = dataSnapshot.getValue(WeightTrackerModel.class);
                    Date date1 = getWeekStartDate();
                    Date date2 = findNextDay(date1);
                    Date date3 = findNextDay(date2);
                    Date date4 = findNextDay(date3);
                    Date date5 = findNextDay(date4);
                    Date date6 = findNextDay(date5);
                    Date date7 = findNextDay(date6);
                    String stringDate1 = getDateInString(date1);
                    String stringDate2 = getDateInString(date2);
                    String stringDate3 = getDateInString(date3);
                    String stringDate4 = getDateInString(date4);
                    String stringDate5 = getDateInString(date5);
                    String stringDate6 = getDateInString(date6);
                    String stringDate7 = getDateInString(date7);
                    if(stringDate1.equals(model.getDate())){
                        weight1 = model.getWeight();
                    }else if(stringDate2.equals(model.getDate())){
                        weight2 = model.getWeight();
                    }else if(stringDate3.equals(model.getDate())){
                        weight3 = model.getWeight();
                    }else if(stringDate4.equals(model.getDate())){
                        weight4 = model.getWeight();
                    }else if(stringDate5.equals(model.getDate())){
                        weight5 = model.getWeight();
                    }else if(stringDate6.equals(model.getDate())){
                        weight6 = model.getWeight();
                    }else if(stringDate7.equals(model.getDate())){
                        weight7 = model.getWeight();
                    }


                }
                WeightTrackerGraphModel model1 = new WeightTrackerGraphModel("Mon",weight1);
                WeightTrackerGraphModel model2 = new WeightTrackerGraphModel("Tue",weight2);
                WeightTrackerGraphModel model3 = new WeightTrackerGraphModel("Wed",weight3);
                WeightTrackerGraphModel model4 = new WeightTrackerGraphModel("Thu",weight4);
                WeightTrackerGraphModel model5 = new WeightTrackerGraphModel("Fri",weight5);
                WeightTrackerGraphModel model6 = new WeightTrackerGraphModel("Sat",weight6);
                WeightTrackerGraphModel model7 = new WeightTrackerGraphModel("Sun",weight7);
                weightGraphModels.add(model1);
                weightGraphModels.add(model2);
                weightGraphModels.add(model3);
                weightGraphModels.add(model4);
                weightGraphModels.add(model5);
                weightGraphModels.add(model6);
                weightGraphModels.add(model7);

                //Add data to bar graph
                ArrayList<BarEntry> barEntries = new ArrayList<>();
                ArrayList<String> labelNames = new ArrayList<>();

                for(int i=0;i<weightGraphModels.size();i++){
                    String day = weightGraphModels.get(i).getDay();
                    int waterIntake = (int)weightGraphModels.get(i).getWeight();
                    barEntries.add(new BarEntry(i,waterIntake));
                    labelNames.add(day);
                }

                BarDataSet barDataSet = new BarDataSet(barEntries,"Daily Weight");
                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                barDataSet.setValueTextColor(Color.BLACK);
                barDataSet.setValueTextSize(16f);

                Description description = new Description();
                description.setText("Days");
                barGraphWeightTracker.setDescription(description);

                BarData barData = new BarData(barDataSet);
                barGraphWeightTracker.setData(barData);

                XAxis xAxis = barGraphWeightTracker.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labelNames));
                xAxis.setPosition(XAxis.XAxisPosition.TOP);
                xAxis.setDrawGridLines(false);
                xAxis.setDrawAxisLine(false);
                xAxis.setGranularity(1f);
                xAxis.setLabelCount(labelNames.size());

                barGraphWeightTracker.animateY(2000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeightTrackerGraph.this, "Failed!!! to get Data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String getDateInString(Date firstDateOfWeek) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
        String date = format.format(firstDateOfWeek);
        return date;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Date getWeekStartDate() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        return calendar.getTime();
    }

    private static Date findNextDay(Date date)
    {
        return new Date(date.getTime() + MILLIS_IN_A_DAY);
    }
}