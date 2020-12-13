package com.witnip.bmi.Model;

public class WaterIntakeModel {
    String date;
    String time;
    double waterIntake;

    public WaterIntakeModel() {
    }

    public WaterIntakeModel(String date, String time, double waterIntake) {
        this.date = date;
        this.time = time;
        this.waterIntake = waterIntake;
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

    public double getWaterIntake() {
        return waterIntake;
    }

    public void setWaterIntake(double waterIntake) {
        this.waterIntake = waterIntake;
    }
}
