package com.wheref.springrestapi.controller;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wheref.springrestapi.model.Coordinates;
import com.wheref.springrestapi.model.Geometry;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/welcome")
public class WelcomeController {

    @Value("classpath:complex-sample.json")
    Resource sampleJson;

    private Long gbUnit = 1073741824L;

    private Coordinates co;
    
    @GetMapping("/home")
    @ResponseBody
    public String home(){
        return "home";
    }

    @GetMapping("/chartData")
	public Map<String, Double> chartData(@RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            System.out.println(String.format("GET: Header '%s' = %s", key, value));
        });
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
	public Map<String, Double> postChartData(@RequestBody String body, @RequestHeader Map<String, String> headers) {
        System.out.println("L3: body: "+body);
        headers.forEach((key, value) -> {
            System.out.println(String.format("POST: Header '%s' = %s", key, value));
        });

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

    @GetMapping("/computer")
	public Map<String, Object> computer() throws InstanceNotFoundException, AttributeNotFoundException, MalformedObjectNameException, ReflectionException, MBeanException {
        Map<String, Object> root = new LinkedHashMap<String, Object>();
        ThreadMXBean tb = ManagementFactory.getThreadMXBean();
        
        Map<String, Integer> map = new LinkedHashMap<String, Integer>();
        map.put("Thread Count", tb.getThreadCount());
        map.put("Peak Thread Count", tb.getPeakThreadCount());
        root.put("Thread", map);

        Map<String, Double> mapMemoryHeap = new LinkedHashMap<String, Double>();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        double heapMemoryUsage = (double)memoryMXBean.getHeapMemoryUsage().getInit() / gbUnit;
        double heapMemoryUsed = (double)memoryMXBean.getHeapMemoryUsage().getUsed() / gbUnit;
        double heapMemoryMax = (double)memoryMXBean.getHeapMemoryUsage().getMax() / gbUnit;
        double heapMemoryCommited = (double)memoryMXBean.getHeapMemoryUsage().getCommitted() / gbUnit;
        mapMemoryHeap.put("Usage", heapMemoryUsage);
        mapMemoryHeap.put("Used", heapMemoryUsed);
        mapMemoryHeap.put("max", heapMemoryMax);
        mapMemoryHeap.put("Comitted", heapMemoryCommited);
        root.put("Heap Memory", mapMemoryHeap);

        Map<String, Long> mapPhysicalMemory = new LinkedHashMap<String, Long>();
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        Object attribute = mBeanServer.getAttribute(new ObjectName("java.lang","type","OperatingSystem"), "TotalPhysicalMemorySize");
        Object attribute2 = mBeanServer.getAttribute(new ObjectName("java.lang","type","OperatingSystem"), "FreePhysicalMemorySize");
        Long totalPhysicalMemorySize = Long.parseLong(attribute.toString()) / gbUnit;
        Long freePhysicalMemorySize = Long.parseLong(attribute2.toString()) / gbUnit; 
        mapPhysicalMemory.put("total", totalPhysicalMemorySize);
        mapPhysicalMemory.put("free", freePhysicalMemorySize);
        root.put("Physical Memory", mapPhysicalMemory);

        File fileRoot = new File("/");
        Long totalSpac = fileRoot.getTotalSpace();
        Long freeSpace = fileRoot.getFreeSpace();
        Long usableSpace = fileRoot.getUsableSpace();
        Map<String, Long> mapSpace = new LinkedHashMap<String, Long>();
        mapSpace.put("TotalSpac", totalSpac);
        mapSpace.put("FreeSpace", freeSpace);
        mapSpace.put("UsableSpace", usableSpace);
        root.put("SpaceRaw", mapSpace);

        Map<String, Double> mapSpaceG = new LinkedHashMap<String, Double>();
        mapSpaceG.put("TotalSpac", Math.ceil((double) (totalSpac/gbUnit)));
        mapSpaceG.put("FreeSpace", Math.ceil((double) freeSpace/gbUnit));
        mapSpaceG.put("UsableSpace", Math.ceil((double) usableSpace/gbUnit));
        root.put("SpaceG", mapSpaceG);

        return root;
    }

    @GetMapping("/coordinate")
	public Geometry coordinate() {
        List<Float> coordinates = new ArrayList<>();
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
