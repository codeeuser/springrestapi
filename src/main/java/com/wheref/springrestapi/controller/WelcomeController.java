package com.wheref.springrestapi.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        return "home";
    }

    @GetMapping("/chartData")
	public Map<String, Double> chartData() {
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        map.put("Jan", randomNum(10.0, 200.0));
        map.put("Feb", randomNum(10.0, 200.0));
        map.put("Mar", randomNum(10.0, 200.0));
        map.put("Apr", randomNum(-100.0, 200.0));
        map.put("May", randomNum(10.0, 200.0));
        map.put("Jun", randomNum(10.0, 200.0));
		return map;
	}

    @PostMapping("/postChartData")
	public Map<String, Double> postChartData() {
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        map.put("Jul", randomNum(10.0, 200.0));
        map.put("Aug", randomNum(10.0, 200.0));
        map.put("Sep", randomNum(10.0, 200.0));
        map.put("Oct", randomNum(-100.0, 200.0));
        map.put("Nov", randomNum(10.0, 200.0));
        map.put("Dec", randomNum(10.0, 200.0));
		return map;
	}

    private double randomNum(double rangeMin, double rangeMax){
        Random r = new Random();
        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        double roundDbl = Math.round(randomValue*100.0)/100.0;
        return roundDbl;
    }
}
