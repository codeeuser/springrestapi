package com.wheref.springrestapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.wheref.springrestapi.model.ChartData;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/welcome")
public class WelcomeController {
    
    @GetMapping("/home")
    @ResponseBody
    public String home(){
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        map.put("Jan", randomNum(40.0, 60.0));
        map.put("Feb", randomNum(20.0, 30.0));
        map.put("Mar", randomNum(30.0, 40.0));
        map.put("Apr", randomNum(40.0, 50.0));
        map.put("May", randomNum(50.0, 60.0));
        map.put("Jun", randomNum(60.0, 70.0));
        Gson gson = new Gson();
        String sJson = gson.toJson(map);
        return sJson;
    }

    private double randomNum(double rangeMin, double rangeMax){
        Random r = new Random();
        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        double roundDbl = Math.round(randomValue*100.0)/100.0;
        return roundDbl;
    }
}
