package com.witnip.bmi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.witnip.bmi.R;


public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int [] slide_images = {
            R.drawable.bmi,
            R.drawable.calorie,
            R.drawable.gm_diet,
            R.drawable.water_intake
    };

    public String [] slide_headings ={
            "Body Mass Index",
            "Calorie Count",
            "GM Diet",
            "Water Intake"
    };

    public String [] slide_descriptions={
            "Keep track of your body mass index levels regularly with custom reminders",
            "Manage and calculate your daily calories through various activities.",
            "Built-in guide for 7 day weight loss diet program.",
            "Set your daily water intake goals and measure how well you did."
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView = view.findViewById(R.id.slide_image);
        TextView slideHeading = view.findViewById(R.id.txtHeading);
        TextView slideDescription = view.findViewById(R.id.txtDescription);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descriptions[position]);

        container.addView(view );

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
