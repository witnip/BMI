package com.witnip.bmi.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.witnip.bmi.R;

public class BfpResult extends AppCompatActivity {

    RelativeLayout rlEssentialFatPercent,rlFatPercentForAthletes,rlFitnessLevel,rlAverageLevel,rlObeseLevel;
    TextView lblEssentialFatPercentValue,lblFatPercentForAthletesValue,lblFitnessLevelValue,lblAverageLevelValue,lblObeseLevelValue,lblBfpResult;
    ProgressBar pbBFP;

    Animation blinkAnimation;

    double bfp;
    String gender;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bfp_result);

        rlEssentialFatPercent = findViewById(R.id.rlEssentialFatPercent);
        lblEssentialFatPercentValue = findViewById(R.id.lblEssentialFatPercentValue);

        rlFatPercentForAthletes = findViewById(R.id.rlFatPercentForAthletes);
        lblFatPercentForAthletesValue = findViewById(R.id.lblFatPercentForAthletesValue);

        rlFitnessLevel = findViewById(R.id.rlFitnessLevel);
        lblFitnessLevelValue = findViewById(R.id.lblFitnessLevelValue);

        rlAverageLevel = findViewById(R.id.rlAverageLevel);
        lblAverageLevelValue = findViewById(R.id.lblAverageLevelValue);

        rlObeseLevel = findViewById(R.id.rlObeseLevel);
        lblObeseLevelValue = findViewById(R.id.lblObeseLevelValue);

        lblBfpResult = findViewById(R.id.lblBfpResult);
        pbBFP = findViewById(R.id.pbBFP);

        blinkAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink);

        Intent data = this.getIntent();
        gender = data.getStringExtra("gender");
        bfp = data.getDoubleExtra("bfp",0);
        setResult();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setResult() {
        if(bfp != 0){
            lblBfpResult.setText(String.format("%s%%", bfp));
            int progress = (int)bfp;
            pbBFP.setProgress(progress);

            if(gender.equals("Male")){
                if(bfp >= 2 && bfp < 6){
                    rlEssentialFatPercent.setBackground(getDrawable(R.drawable.under_weight));
                    rlEssentialFatPercent.setAnimation(blinkAnimation);
                    pbBFP.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorUnderWeight)));
                    lblBfpResult.setTextColor(getResources().getColor(R.color.colorUnderWeight));
                    lblEssentialFatPercentValue.setText("2-5%");
                }else if(bfp >= 6 && bfp < 14){
                    rlFatPercentForAthletes.setBackground(getDrawable(R.drawable.normal_weight));
                    rlFatPercentForAthletes.setAnimation(blinkAnimation);
                    pbBFP.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorNormalWeight)));
                    lblBfpResult.setTextColor(getResources().getColor(R.color.colorNormalWeight));
                    lblEssentialFatPercentValue.setText("6-13%");
                }else if(bfp >= 14 && bfp < 18){
                    rlFitnessLevel.setBackground(getDrawable(R.drawable.over_weight));
                    rlFitnessLevel.setAnimation(blinkAnimation);
                    pbBFP.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOverWeight)));
                    lblBfpResult.setTextColor(getResources().getColor(R.color.colorOverWeight));
                    lblEssentialFatPercentValue.setText("14-17%");
                }else if(bfp >= 18 && bfp < 25){
                    rlAverageLevel.setBackground(getDrawable(R.drawable.obese));
                    rlAverageLevel.setAnimation(blinkAnimation);
                    pbBFP.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorObese)));
                    lblBfpResult.setTextColor(getResources().getColor(R.color.colorObese));
                    lblEssentialFatPercentValue.setText("18-24%");
                }else if(bfp >= 25){
                    rlObeseLevel.setBackground(getDrawable(R.drawable.morbidly_obese));
                    rlObeseLevel.setAnimation(blinkAnimation);
                    pbBFP.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorMorbidlyObese)));
                    lblBfpResult.setTextColor(getResources().getColor(R.color.colorMorbidlyObese));
                    lblEssentialFatPercentValue.setText("25% and above");
                }
            }else if(gender.equals("Female")){
                if(bfp >= 10 && bfp < 14){
                    rlEssentialFatPercent.setBackground(getDrawable(R.drawable.under_weight));
                    rlEssentialFatPercent.setAnimation(blinkAnimation);
                    pbBFP.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorUnderWeight)));
                    lblBfpResult.setTextColor(getResources().getColor(R.color.colorUnderWeight));
                    lblEssentialFatPercentValue.setText("10-13%");
                }else if(bfp >= 14 && bfp < 21){
                    rlFatPercentForAthletes.setBackground(getDrawable(R.drawable.normal_weight));
                    rlFatPercentForAthletes.setAnimation(blinkAnimation);
                    pbBFP.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorNormalWeight)));
                    lblBfpResult.setTextColor(getResources().getColor(R.color.colorNormalWeight));
                    lblEssentialFatPercentValue.setText("14-20%");
                }else if(bfp >= 21 && bfp < 24){
                    rlFitnessLevel.setBackground(getDrawable(R.drawable.over_weight));
                    rlFitnessLevel.setAnimation(blinkAnimation);
                    pbBFP.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOverWeight)));
                    lblBfpResult.setTextColor(getResources().getColor(R.color.colorOverWeight));
                    lblEssentialFatPercentValue.setText("21-24%");
                }else if(bfp >= 24 && bfp < 32){
                    rlAverageLevel.setBackground(getDrawable(R.drawable.obese));
                    rlAverageLevel.setAnimation(blinkAnimation);
                    pbBFP.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorObese)));
                    lblBfpResult.setTextColor(getResources().getColor(R.color.colorObese));
                    lblEssentialFatPercentValue.setText("24-31%");
                }else if(bfp >= 32){
                    rlObeseLevel.setBackground(getDrawable(R.drawable.morbidly_obese));
                    rlObeseLevel.setAnimation(blinkAnimation);
                    pbBFP.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorMorbidlyObese)));
                    lblBfpResult.setTextColor(getResources().getColor(R.color.colorMorbidlyObese));
                    lblEssentialFatPercentValue.setText("32% and above");
                }
            }
        }
    }
}