package com.witnip.bmi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.witnip.bmi.BuildConfig;
import com.witnip.bmi.R;

public class Share extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_SUBJECT,"Download Fitness Ensure app: \n\n");
            String shareMessage = "https://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID+"\n\n";
            share.putExtra(Intent.EXTRA_TEXT,shareMessage);
            startActivity(Intent.createChooser(share,"Share"));

        }catch (Exception e){
            Log.d("Share", "Error : "+e);
        }
    }
}