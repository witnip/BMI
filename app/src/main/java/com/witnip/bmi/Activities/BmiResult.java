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

public class BmiResult extends AppCompatActivity {

    ProgressBar pbBMI;
    TextView lblBmiResult;
    RelativeLayout rlUnderWeight,rlNormalWeight,rlOverWeight,rlObese,rlMorbidlyObese;

    Animation blinkAnimation;

    double bmi;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_result);

        pbBMI = findViewById(R.id.pbBMI);
        lblBmiResult = findViewById(R.id.lblBmiResult);

        rlUnderWeight = findViewById(R.id.rlUnderWeight);
        rlNormalWeight = findViewById(R.id.rlNormalWeight);
        rlOverWeight = findViewById(R.id.rlOverWeight);
        rlObese = findViewById(R.id.rlObese);
        rlMorbidlyObese = findViewById(R.id.rlMorbidlyObese);

        blinkAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink);

        Intent data = this.getIntent();
        bmi = data.getDoubleExtra("bmi",0);
        setResult();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setResult() {
        if(bmi != 0){
            lblBmiResult.setText(String.format("%s%%", bmi));
            int progress = (int)bmi;
            pbBMI.setProgress(progress);

            if(bmi < 20){
                rlUnderWeight.setBackground(getDrawable(R.drawable.under_weight));
                rlUnderWeight.setAnimation(blinkAnimation);
                pbBMI.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorUnderWeight)));
                lblBmiResult.setTextColor(getResources().getColor(R.color.colorUnderWeight));
            }else if(bmi >= 20 && bmi <25){
                rlNormalWeight.setBackground(getDrawable(R.drawable.normal_weight));
                rlNormalWeight.setAnimation(blinkAnimation);
                pbBMI.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorNormalWeight)));
                lblBmiResult.setTextColor(getResources().getColor(R.color.colorNormalWeight));
            }else if(bmi >= 25 && bmi <30){
                rlOverWeight.setBackground(getDrawable(R.drawable.over_weight));
                rlOverWeight.setAnimation(blinkAnimation);
                pbBMI.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOverWeight)));
                lblBmiResult.setTextColor(getResources().getColor(R.color.colorOverWeight));
            }else if(bmi >= 30 && bmi <40){
                rlObese.setBackground(getDrawable(R.drawable.obese));
                rlObese.setAnimation(blinkAnimation);
                pbBMI.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorObese)));
                lblBmiResult.setTextColor(getResources().getColor(R.color.colorObese));
            }else if(bmi >= 40){
                rlMorbidlyObese.setBackground(getDrawable(R.drawable.morbidly_obese));
                rlMorbidlyObese.setAnimation(blinkAnimation);
                pbBMI.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorMorbidlyObese)));
                lblBmiResult.setTextColor(getResources().getColor(R.color.colorMorbidlyObese));
            }
        }
    }
}