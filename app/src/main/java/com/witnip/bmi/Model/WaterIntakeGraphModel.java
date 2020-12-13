package com.witnip.bmi.Model;

public class WaterIntakeGraphModel {
    String day;
    double waterIntake;

    public WaterIntakeGraphModel() {
    }

    public WaterIntakeGraphModel(String day, double waterIntake) {
        this.day = day;
        this.waterIntake = waterIntake;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public double getWaterIntake() {
        return waterIntake;
    }

    public void setWaterIntake(double waterIntake) {
        this.waterIntake = waterIntake;
    }
}
