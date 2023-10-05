package com.wheref.springrestapi.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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

        String topic = "/topic/trigger";
        messagingTemplate.convertAndSend(topic, map);

        return true;
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
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

    @Scheduled(fixedRate = 2, timeUnit = TimeUnit.SECONDS)
	public void reportSingleData() {
        
        double dValue = UtilFunction.randomNum(10.0, 90.0);
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        map.put("single", dValue);
		System.out.println("-- reportSingleData --, dValue: "+ dValue); 

        String topic = "/topic/single";
        messagingTemplate.convertAndSend(topic, map);
	}

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return "message-123";
    }
}
