package com.witnip.bmi.Model;

public class WeightTrackerModel {
    String date;
    String time;
    double weight;

    public WeightTrackerModel() {
    }

    public WeightTrackerModel(String date, String time, double weight) {
        this.date = date;
        this.time = time;
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
