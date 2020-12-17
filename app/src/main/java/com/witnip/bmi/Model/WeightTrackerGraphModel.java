package com.witnip.bmi.Model;

public class WeightTrackerGraphModel {
    String day;
    double weight;

    public WeightTrackerGraphModel() {
    }

    public WeightTrackerGraphModel(String day, double weight) {
        this.day = day;
        this.weight = weight;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
