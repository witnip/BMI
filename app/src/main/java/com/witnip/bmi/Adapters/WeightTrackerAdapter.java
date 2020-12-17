package com.witnip.bmi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.witnip.bmi.Model.WeightTrackerModel;
import com.witnip.bmi.R;

import java.util.ArrayList;

public class WeightTrackerAdapter extends RecyclerView.Adapter<WeightTrackerAdapter.WeightTrackerViewHolder> {

    Context context;
    ArrayList<WeightTrackerModel> trackerModels;

    public WeightTrackerAdapter(Context context, ArrayList<WeightTrackerModel> trackerModels) {
        this.context = context;
        this.trackerModels = trackerModels;
    }

    @NonNull
    @Override
    public WeightTrackerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weight_tracker_list,parent,false);
        return new WeightTrackerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeightTrackerViewHolder holder, int position) {
        holder.lblWeight.setText(String.format("%s", trackerModels.get(position).getWeight()));
        holder.lblDate.setText(trackerModels.get(position).getDate());
        holder.lblTime.setText(trackerModels.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return trackerModels.size();
    }

    public static class WeightTrackerViewHolder extends RecyclerView.ViewHolder {

        TextView lblWeight;
        TextView lblDate;
        TextView lblTime;

        public WeightTrackerViewHolder(@NonNull View itemView) {
            super(itemView);

            lblWeight = itemView.findViewById(R.id.lblWeight);
            lblDate = itemView.findViewById(R.id.lblDate);
            lblTime = itemView.findViewById(R.id.lblTime);
        }
    }
}
