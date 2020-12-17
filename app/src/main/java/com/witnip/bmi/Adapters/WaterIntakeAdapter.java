package com.witnip.bmi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.witnip.bmi.Model.WaterIntakeModel;
import com.witnip.bmi.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WaterIntakeAdapter extends RecyclerView.Adapter<WaterIntakeAdapter.WaterIntakeViewHolder> {


    Context context;
    ArrayList<WaterIntakeModel> waterIntakeList;

    public WaterIntakeAdapter(Context context, ArrayList<WaterIntakeModel> waterIntakeList) {
        this.context = context;
        this.waterIntakeList = waterIntakeList;
    }

    @NonNull
    @Override
    public WaterIntakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.water_intake_list,parent,false);
        return new WaterIntakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterIntakeViewHolder holder, int position) {

        holder.lblWaterIntake.setText(String.format("%s", waterIntakeList.get(position).getWaterIntake()));
        holder.lblDate.setText(waterIntakeList.get(position).getDate());
        holder.lblTime.setText(waterIntakeList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return waterIntakeList.size();
    }

    static class WaterIntakeViewHolder extends RecyclerView.ViewHolder{

        TextView lblWaterIntake;
        TextView lblDate;
        TextView lblTime;

        public WaterIntakeViewHolder(@NonNull View itemView) {
            super(itemView);

            lblWaterIntake = itemView.findViewById(R.id.lblWaterIntake);
            lblDate = itemView.findViewById(R.id.lblDate);
            lblTime = itemView.findViewById(R.id.lblTime);

        }
    }
}
