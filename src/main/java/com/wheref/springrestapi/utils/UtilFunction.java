package com.wheref.springrestapi.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class UtilFunction {
    
    public static Double randomNum(double rangeMin, double rangeMax){
        Random r = new Random();
        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        double roundDbl = Math.round(randomValue*100.0)/100.0;
        return roundDbl;
    }

    public static Integer randomInt(int rangeMin, int rangeMax){
        Random r = new Random();
        return r.nextInt((rangeMax - rangeMin) + 1) + rangeMin;
    }

    public static Map<String, Integer> statRandomTime(int rangeMin, int rangeMax){
        Map<String, Integer> map = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < 24; i++) {
            String hour = String.valueOf(i);
            map.put(hour, UtilFunction.randomInt(rangeMin, rangeMax));
        }
        return map;
    }

    public static Map<String, Integer> statBasedTime(int addValue){
        Map<String, Integer> map = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < 8; i++) {
            String hour = String.valueOf(i);
            map.put(hour, UtilFunction.randomInt(0, 10) + addValue);
        }
        for (int i = 8; i < 10; i++) {
            String hour = String.valueOf(i);
            map.put(hour, UtilFunction.randomInt(10, 70) + addValue);
        }
        for (int i = 10; i < 20; i++) {
            String hour = String.valueOf(i);
            map.put(hour, UtilFunction.randomInt(100, 200) + addValue);
        }
        for (int i = 20; i < 24; i++) {
            String hour = String.valueOf(i);
            map.put(hour, 0);
        }
        return map;
    }
}
