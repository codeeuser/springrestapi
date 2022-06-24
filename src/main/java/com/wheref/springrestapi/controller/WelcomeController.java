package com.wheref.springrestapi.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.wheref.springrestapi.model.Coordinates;
import com.wheref.springrestapi.models.Geometry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/welcome")
public class WelcomeController {

    @Value("classpath:complex-sample.json")
    Resource sampleJson;

    private Coordinates co;
    
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

    @GetMapping("/chartDataLevel2")
	public Map<String, Map<String, Double> > chartDataLevel2() {
        Map<String, Map<String, Double> > root = new LinkedHashMap<String, Map<String, Double> >();
        
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        map.put("Jan", randomNum(10.0, 200.0));
        map.put("Feb", randomNum(10.0, 200.0));
        map.put("Mar", randomNum(10.0, 200.0));
        map.put("Apr", randomNum(-100.0, 200.0));
        map.put("May", randomNum(10.0, 200.0));
        map.put("Jun", randomNum(10.0, 200.0));

        Map<String, Double> map2 = new LinkedHashMap<String, Double>();
        map2.put("Jul", randomNum(10.0, 200.0));
        map2.put("Aug", randomNum(10.0, 200.0));
        map2.put("Sep", randomNum(10.0, 200.0));
        map2.put("Oct", randomNum(-100.0, 200.0));
        map2.put("Nov", randomNum(10.0, 200.0));
        map2.put("Dec", randomNum(10.0, 200.0));

        root.put("first", map);
        root.put("second", map2);
		return root;
	}

    @GetMapping("/chartDataLevel3")
	public Map<String, Object> chartDataLevel3() {
        Map<String, Object> root = new LinkedHashMap<String, Object>();
        
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        map.put("School", randomNum(10.0, 200.0));
        map.put("Polis", randomNum(10.0, 200.0));

        Map<String, Object> map2 = new LinkedHashMap<String, Object>();
        map2.put("Store", randomNum(10.0, 200.0));
        map2.put("Stall", randomNum(10.0, 200.0));

        Map<String, Double> map3 = new LinkedHashMap<String, Double>();
        map3.put("Orange", randomNum(10.0, 200.0));

        map2.put("fruit", map3);
        root.put("one", map);
        root.put("two", map2);
		return root;
	}

    @GetMapping("/coordinate")
	public Geometry coordinate() {
        // float random = RandomUtils.nextFloat(0, 0.01f);
        float lat = 4.5841f;
        float lng = 101.0829f;
        // lat = lat + random;
        // System.out.println("random: "+random+", lat: "+lat);
         List<Float> coordinates = new ArrayList<>();// [4.5841 101.0829];
        //  coordinates.add(lat);
        //  coordinates.add(lng);
        if (this.co!=null){
            coordinates.add(this.co.getLat());
            coordinates.add(this.co.getLng());
        }
        Geometry geometry = new Geometry("Point", coordinates);
		return geometry;
	}

    @PostMapping(path="/postCoordinate", consumes = "application/json", produces = "application/json")
	public Coordinates postcoordinate(@RequestBody Coordinates coordinates) {
        System.out.println("coordinates: "+ coordinates.getLat()+", "+ coordinates.getLng());
        this.co = coordinates;
        return coordinates;
    }

    private double randomNum(double rangeMin, double rangeMax){
        Random r = new Random();
        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        double roundDbl = Math.round(randomValue*100.0)/100.0;
        return roundDbl;
    }
}
