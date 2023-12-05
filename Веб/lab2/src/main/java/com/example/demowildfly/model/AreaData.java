package com.example.demowildfly.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AreaData implements Serializable {
    private double x;
    private double y;
    private double r;
    private boolean result;

    private String resultString;

    public long getCalculationTime() {
        return calculationTime;
    }

    public void setCalculationTime(long calculationTime) {
        this.calculationTime = calculationTime;
    }

    long calculationTime;
    long currentTime;

    public AreaData(double x, double y, double r, boolean result) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        if(result)
            resultString = "Hit";
        else
            resultString = "Miss";
    }

    public AreaData() {
        this.x = 0;
        this.y = 0;
        this.r = 0;
        this.result = false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public boolean getResult() {
        return result;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) { this.y = y; }

    public void setR(double r) {
        this.r = r;
    }

    public void setResult(boolean result) {
        this.result = result;
        if(result)
            resultString = "Hit";
        else
            resultString = "Miss";
    }

    public String getResultString() {
        return resultString;
    }

    public String getCurrentTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        return formattedDateTime;
    }
}
