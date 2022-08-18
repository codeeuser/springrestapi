package com.wheref.springrestapi.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wheref.springrestapi.utils.UtilFunction;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/event")
public class EventController {
    
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @GetMapping("/trigger")
	public Boolean trigger() {
        System.out.println("-- trigger --"); 
        
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        map.put("Jan", UtilFunction.randomNum(10.0, 90.0));
        map.put("Feb", UtilFunction.randomNum(10.0, 90.0));
        map.put("Mar", UtilFunction.randomNum(10.0, 90.0));
        map.put("Apr", UtilFunction.randomNum(0.0, 90.0));
        map.put("May", UtilFunction.randomNum(10.0, 90.0));
        map.put("Jun", UtilFunction.randomNum(10.0, 90.0));

        String topic = "/topic/greetings";
        messagingTemplate.convertAndSend(topic, map);

        return true;
    }

    @Scheduled(fixedRate = 2000)
	public void reportPeriodic() {
		System.out.println("-- reportPeriodic --"); 
        
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        map.put("Jan", UtilFunction.randomNum(10.0, 90.0));
        map.put("Feb", UtilFunction.randomNum(10.0, 90.0));
        map.put("Mar", UtilFunction.randomNum(10.0, 90.0));
        map.put("Apr", UtilFunction.randomNum(0.0, 90.0));
        map.put("May", UtilFunction.randomNum(10.0, 90.0));
        map.put("Jun", UtilFunction.randomNum(10.0, 90.0));

        String topic = "/topic/greetings";
        messagingTemplate.convertAndSend(topic, map);
	}

    @Scheduled(fixedRate = 3000)
	public void reportSingleData() {
		System.out.println("-- reportSingleData --"); 
        
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        map.put("single", UtilFunction.randomNum(10.0, 90.0));

        String topic = "/topic/single";
        messagingTemplate.convertAndSend(topic, map);
	}
}