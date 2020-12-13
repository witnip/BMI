package com.witnip.bmi.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.witnip.bmi.Adapters.SliderAdapter;
import com.witnip.bmi.R;

public class OnBoard extends AppCompatActivity {

    private ViewPager vpOnBoard;
    private LinearLayout llDots;
    private SliderAdapter sliderAdapter;

    private TextView[] mDots;

    private Button btnBack, btnNext;

    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);

        if(isFirstTime()){
            Intent mainIntent= new Intent(OnBoard.this,MainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);
            finish();
        }

        vpOnBoard = findViewById(R.id.vpOnBoard);
        llDots = findViewById(R.id.llDots);
        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);

        sliderAdapter = new SliderAdapter(this);
        vpOnBoard.setAdapter(sliderAdapter);

        addDotsIndicator(0);
        vpOnBoard.addOnPageChangeListener(viewListener);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentPage == (mDots.length-1)){
                    savePref();
                    boolean firstTime = isFirstTime();
                    if(isFirstTime()){
                        Intent mainIntent= new Intent(OnBoard.this,MainActivity.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                    }
                    vpOnBoard.setCurrentItem(mCurrentPage + 1);
                }
                vpOnBoard.setCurrentItem(mCurrentPage + 1);

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpOnBoard.setCurrentItem(mCurrentPage - 1);
            }
        });
    }

    public void addDotsIndicator(int position) {
        mDots = new TextView[4];
        llDots.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorGray));

            llDots.addView(mDots[i]);
        }
        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mCurrentPage = position;
            if (position == 0) {
                btnNext.setEnabled(true);
                btnBack.setEnabled(false);
                btnBack.setVisibility(View.INVISIBLE);

                btnNext.setText("Next");
                btnBack.setText("");
            } else if (position == (mDots.length - 1)) {
                btnNext.setEnabled(true);
                btnBack.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);

                btnNext.setText("Finish");
                btnBack.setText("Back");
            } else {
                btnNext.setEnabled(true);
                btnBack.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);

                btnNext.setText("Next");
                btnBack.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void savePref(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("OnBoard",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstTime",true);
        editor.apply();
    }

    public boolean isFirstTime(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("OnBoard",MODE_PRIVATE);
        return preferences.getBoolean("firstTime",false);
    }
}