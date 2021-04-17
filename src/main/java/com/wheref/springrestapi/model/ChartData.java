package com.wheref.springrestapi.model;

import java.io.Serializable;

public class ChartData implements Serializable{
    
    private String x;
    private double y;
    
    public ChartData(double d, double y) {
        this.x = d;
        this.y = y;
    }
    public String getX() {
        return x;
    }
    public void setX(String x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    
}
