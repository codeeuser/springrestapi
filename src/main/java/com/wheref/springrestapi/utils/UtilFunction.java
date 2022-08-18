package com.wheref.springrestapi.utils;

import java.util.Random;

public class UtilFunction {
    
    public static double randomNum(double rangeMin, double rangeMax){
        Random r = new Random();
        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        double roundDbl = Math.round(randomValue*100.0)/100.0;
        return roundDbl;
    }
}
