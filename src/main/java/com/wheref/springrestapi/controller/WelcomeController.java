package com.wheref.springrestapi.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wheref.springrestapi.model.Coordinates;
import com.wheref.springrestapi.model.Geometry;
import com.wheref.springrestapi.utils.UtilFunction;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/welcome")
public class WelcomeController {

    @Value("classpath:files/googleapiscom-analyticsreporting.json")
    Resource sampleJson;

    @Value("classpath:images/avatar.jpeg")
    Resource avatar;

    @Value("classpath:images/avatar-movie.jpeg")
    Resource avatarMovie;

    @Value("classpath:images/floor-plan.png")
    Resource floorPlan;

    @Value("classpath:files/nasdaq.csv")
    Resource nasdaq;

    private Long gbUnit = 1073741824L;

    private Coordinates co;
    
    @GetMapping("/home")
    @ResponseBody
    public String home(){
        return "home";
    }

    @GetMapping(value = "/photo", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] photo() throws IOException {
        System.out.println("--- LOADING PHOTO ---");
        InputStream[] list = {
            avatar.getInputStream(),
            avatarMovie.getInputStream()
        };
        int randomNum = ThreadLocalRandom.current().nextInt(0, 2);
        return IOUtils.toByteArray(list[randomNum]);
    } 

    @GetMapping(value = "/floorPlan", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] floorPlan() throws IOException {
        System.out.println("--- LOADING FLOOR PLAN ---");
        return IOUtils.toByteArray(floorPlan.getInputStream());
    } 

    @GetMapping("/chartData")
	public Map<String, Double> chartData(@RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            System.out.println(String.format("GET: Header '%s' = %s", key, value));
        });
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        map.put("Jan", UtilFunction.randomNum(300.0, 900000.0));
        map.put("Feb", UtilFunction.randomNum(10.0, 90.0));
        map.put("Mar", UtilFunction.randomNum(10.0, 90.0));
        map.put("Apr", UtilFunction.randomNum(0.0, 90.0));
        map.put("May", UtilFunction.randomNum(10.0, 90.0));
        map.put("Jun", UtilFunction.randomNum(10.0, 90.0));
		return map;
	}

    @GetMapping("/chartDataList")
	public Map<String, Object> chartData() {
        Map<String, Object> root = new LinkedHashMap<String, Object>();
        List<Map<String, Double>> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Map<String, Double> map = new LinkedHashMap<String, Double>();
            map.put("Jan", UtilFunction.randomNum(10.0, 90.0));
            map.put("Feb", UtilFunction.randomNum(10.0, 90.0));
            map.put("Mar", UtilFunction.randomNum(10.0, 90.0));
            map.put("Apr", UtilFunction.randomNum(0.0, 90.0));
            map.put("May", UtilFunction.randomNum(10.0, 90.0));
            map.put("Jun", UtilFunction.randomNum(10.0, 90.0));

            list.add(map);
        }

        Map<String, Double> map2 = new LinkedHashMap<String, Double>();
        map2.put("Jul", UtilFunction.randomNum(10.0, 90.0));
        map2.put("Aug", UtilFunction.randomNum(10.0, 90.0));
        map2.put("Sep", UtilFunction.randomNum(10.0, 90.0));
        map2.put("Oct", UtilFunction.randomNum(0.0, 90.0));
        map2.put("Nov", UtilFunction.randomNum(10.0, 90.0));
        map2.put("Dec", UtilFunction.randomNum(10.0, 90.0));
        
        root.put("month", map2);
        root.put("list", list);
		return root;
	}

    @PostMapping("/postChartData")
	public Map<String, Double> postChartData(@RequestBody String body, @RequestHeader Map<String, String> headers) {
        System.out.println("L3: body: "+body);
        headers.forEach((key, value) -> {
            System.out.println(String.format("POST: Header '%s' = %s", key, value));
        });

        Map<String, Double> map = new LinkedHashMap<String, Double>();
        map.put("Jul", UtilFunction.randomNum(10.0, 90.0));
        map.put("Aug", UtilFunction.randomNum(10.0, 90.0));
        map.put("Sep", UtilFunction.randomNum(10.0, 90.0));
        map.put("Oct", UtilFunction.randomNum(0.0, 90.0));
        map.put("Nov", UtilFunction.randomNum(10.0, 90.0));
        map.put("Dec", UtilFunction.randomNum(10.0, 90.0));
		return map;
	}

    @GetMapping("/chartDataLevel2")
	public Map<String, Map<String, Double> > chartDataLevel2() {
        Map<String, Map<String, Double> > root = new LinkedHashMap<String, Map<String, Double> >();
        
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        map.put("Jan", UtilFunction.randomNum(10.0, 90.0));
        map.put("Feb", UtilFunction.randomNum(10.0, 90.0));
        map.put("Mar", UtilFunction.randomNum(10.0, 90.0));
        map.put("Apr", UtilFunction.randomNum(0.0, 90.0));
        map.put("May", UtilFunction.randomNum(10.0, 90.0));
        map.put("Jun", UtilFunction.randomNum(10.0, 90.0));

        Map<String, Double> map2 = new LinkedHashMap<String, Double>();
        map2.put("Jul", UtilFunction.randomNum(10.0, 90.0));
        map2.put("Aug", UtilFunction.randomNum(10.0, 90.0));
        map2.put("Sep", UtilFunction.randomNum(10.0, 90.0));
        map2.put("Oct", UtilFunction.randomNum(0.0, 90.0));
        map2.put("Nov", UtilFunction.randomNum(10.0, 90.0));
        map2.put("Dec", UtilFunction.randomNum(10.0, 90.0));

        root.put("first", map);
        root.put("second", map2);
		return root;
	}

    @GetMapping("/chartDataLevel3")
	public Map<String, Object> chartDataLevel3() {
        Map<String, Object> root = new LinkedHashMap<String, Object>();
        
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        map.put("Temperature", UtilFunction.randomNum(50.0, 70.0));
        map.put("Speedometer", UtilFunction.randomNum(40.0, 60.0));

        Map<String, Object> map2 = new LinkedHashMap<String, Object>();
        map2.put("Water", UtilFunction.randomNum(50.0, 70.0));
        map2.put("Gas", UtilFunction.randomNum(40.0, 60.0));

        Map<String, Object> map3 = new LinkedHashMap<String, Object>();
        map3.put("Orange", UtilFunction.randomNum(10.0, 90.0));
        map3.put("Web Site", "https://wheref.com");
        map3.put("Microsoft Store", "https://apps.microsoft.com/store/detail/kpi-mboard/9NKW3Q13X88K");
        map3.put("AppStore", "https://apps.apple.com/us/app/mboard/id1632169331");

        map2.put("fruit", map3);
        root.put("one", map);
        root.put("two", map2);
		return root;
	}

    @GetMapping("/stock")
	public Map<String, Object> stock() throws ParseException, InterruptedException{
        TimeUnit.SECONDS.sleep(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> root = new LinkedHashMap<String, Object>();
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Calendar c = Calendar.getInstance();
            String dt = "2022-07-0"+i;
            c.setTime(sdf.parse(dt));
            c.add(Calendar.DATE, 1);  // number of days to add
            dt = sdf.format(c.getTime()); 
            Map<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("symbol", "AAPL");
            map.put("exchange", "XNAS");
            map.put("open", UtilFunction.randomNum(10.0, 90.0));
            map.put("high", UtilFunction.randomNum(30.0, 90.0));
            map.put("low", UtilFunction.randomNum(10.0, 90.0));
            map.put("close", UtilFunction.randomNum(10.0, 90.0));
            map.put("date", dt);

            list.add(map);
        }
        root.put("data", list);
        return root;
    }

    @GetMapping("/list")
    public Map<String, Object> dataList() {
        Map<String, Object> root = new LinkedHashMap<String, Object>();
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < 7; i++) {
            list.add(UtilFunction.randomNum(10.0, 90.0));
        }

        root.put("data", list);
        return root;
    }


    @GetMapping("/computer")
	public Map<String, Object> computer() throws InstanceNotFoundException, AttributeNotFoundException, MalformedObjectNameException, ReflectionException, MBeanException {
        Map<String, Object> root = new LinkedHashMap<String, Object>();

        // http://docs.oracle.com/javase/6/docs/api/java/lang/management/OperatingSystemMXBean.html
        OperatingSystemMXBean myOsBean = ManagementFactory.getOperatingSystemMXBean();
        double load = myOsBean.getSystemLoadAverage();
        int numCpu = myOsBean.getAvailableProcessors();
        String arch = myOsBean.getArch();
        String nameCpu = myOsBean.getName();
        String versionCpu = myOsBean.getVersion();
        Map<String, Object> mapCpu = new LinkedHashMap<String, Object>();
        mapCpu.put("CPU Usage", load);
        mapCpu.put("Number", numCpu);
        mapCpu.put("Architecture", arch);
        mapCpu.put("OS name", nameCpu);
        mapCpu.put("OS Version", versionCpu);
        root.put("CPU", mapCpu);

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
        mapSpaceG.put("TotalSpace", Math.ceil((double) (totalSpac/gbUnit)));
        mapSpaceG.put("FreeSpace", Math.ceil((double) freeSpace/gbUnit));
        mapSpaceG.put("UsableSpace", Math.ceil((double) usableSpace/gbUnit));
        root.put("SpaceG", mapSpaceG);

        return root;
    }

    @RequestMapping(
        value = "/resource-json",
        method = RequestMethod.GET, 
        produces = "application/json"
    )
	public String resourceJson() throws IOException {
        File file = sampleJson.getFile();
        String str = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        return str;
    }

    @RequestMapping(
        value = "/nasdaq",
        method = RequestMethod.GET, 
        produces = "application/csv"
    )
	public String nasdaq() throws IOException, InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        File file = nasdaq.getFile();
        String str = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        return str;
    }

    @GetMapping("/coordinate")
	public Geometry coordinate() {
        Map<String, Float> coordinates = new HashMap<>();
        System.out.println("co: "+ co);
        if (this.co!=null){
            coordinates.put("lat", this.co.getLat());
            coordinates.put("lng", this.co.getLng());
        } else {
            coordinates.put("lat", 3.1390f);
            coordinates.put("lng", 101.6869f);
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
}
