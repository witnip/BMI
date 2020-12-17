package com.witnip.bmi.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.witnip.bmi.Adapters.WaterIntakeAdapter;
import com.witnip.bmi.Model.WaterIntakeGraphModel;
import com.witnip.bmi.Model.WaterIntakeModel;
import com.witnip.bmi.R;

import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import static java.time.DayOfWeek.MONDAY;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

@RequiresApi(api = Build.VERSION_CODES.O)
public class WaterIntakeGraph extends AppCompatActivity {

    BarChart barGraphWaterIntake;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUser;

    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

    ArrayList<WaterIntakeGraphModel> waterIntakes;
    ArrayList<Double> waterIntakeValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_intake_graph);

        barGraphWaterIntake = findViewById(R.id.barGraphWaterIntake);

        waterIntakes = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users");
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference currentUserDB = mDatabaseUser.child(user_id);
        DatabaseReference waterIntakeDB = currentUserDB.child("waterIntake");
        DatabaseReference daily = waterIntakeDB.child("daily");
        daily.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double waterIntake1 = 0;
                double waterIntake2 = 0;
                double waterIntake3 = 0;
                double waterIntake4 = 0;
                double waterIntake5 = 0;
                double waterIntake6 = 0;
                double waterIntake7 = 0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    WaterIntakeModel model = dataSnapshot.getValue(WaterIntakeModel.class);
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
                        waterIntake1 += model.getWaterIntake();
                    }else if(stringDate2.equals(model.getDate())){
                        waterIntake2 += model.getWaterIntake();
                    }else if(stringDate3.equals(model.getDate())){
                        waterIntake3 += model.getWaterIntake();
                    }else if(stringDate4.equals(model.getDate())){
                        waterIntake4 += model.getWaterIntake();
                    }else if(stringDate5.equals(model.getDate())){
                        waterIntake5 += model.getWaterIntake();
                    }else if(stringDate6.equals(model.getDate())){
                        waterIntake6 += model.getWaterIntake();
                    }else if(stringDate7.equals(model.getDate())){
                        waterIntake7 += model.getWaterIntake();
                    }


                }
                WaterIntakeGraphModel model1 = new WaterIntakeGraphModel("Mon",waterIntake1);
                WaterIntakeGraphModel model2 = new WaterIntakeGraphModel("Tue",waterIntake2);
                WaterIntakeGraphModel model3 = new WaterIntakeGraphModel("Wed",waterIntake3);
                WaterIntakeGraphModel model4 = new WaterIntakeGraphModel("Thu",waterIntake4);
                WaterIntakeGraphModel model5 = new WaterIntakeGraphModel("Fri",waterIntake5);
                WaterIntakeGraphModel model6 = new WaterIntakeGraphModel("Sat",waterIntake6);
                WaterIntakeGraphModel model7 = new WaterIntakeGraphModel("Sun",waterIntake7);
                waterIntakes.add(model1);
                waterIntakes.add(model2);
                waterIntakes.add(model3);
                waterIntakes.add(model4);
                waterIntakes.add(model5);
                waterIntakes.add(model6);
                waterIntakes.add(model7);

                //Add data to bar graph
                ArrayList<BarEntry> barEntries = new ArrayList<>();
                ArrayList<String> labelNames = new ArrayList<>();

                for(int i=0;i<waterIntakes.size();i++){
                    String day = waterIntakes.get(i).getDay();
                    int waterIntake = (int)waterIntakes.get(i).getWaterIntake();
                    barEntries.add(new BarEntry(i,waterIntake));
                    labelNames.add(day);
                }

                BarDataSet barDataSet = new BarDataSet(barEntries,"Daily WaterIntake");
                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                barDataSet.setValueTextColor(Color.BLACK);
                barDataSet.setValueTextSize(16f);

                Description description = new Description();
                description.setText("Days");
                barGraphWaterIntake.setDescription(description);

                BarData barData = new BarData(barDataSet);
                barGraphWaterIntake.setData(barData);

                XAxis xAxis = barGraphWaterIntake.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labelNames));
                xAxis.setPosition(XAxis.XAxisPosition.TOP);
                xAxis.setDrawGridLines(false);
                xAxis.setDrawAxisLine(false);
                xAxis.setGranularity(1f);
                xAxis.setLabelCount(labelNames.size());

                barGraphWaterIntake.animateY(2000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WaterIntakeGraph.this, "Failed!!! to get Data", Toast.LENGTH_SHORT).show();
            }
        });



    }


    private void getWaterIntakeTotal(final String givenDate) {
        //Setting up RecyclerView

    }

    public String getDateInString(Date firstDateOfWeek) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
        String date = format.format(firstDateOfWeek);
        return date;
    }

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