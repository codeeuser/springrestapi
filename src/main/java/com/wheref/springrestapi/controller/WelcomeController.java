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
import java.util.LinkedList;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Value("classpath:files/nasdaq.csv")
    Resource nasdaq;

    @Value("classpath:files/trajectory.csv")
    Resource trajectory;

    private Long gbUnit = 1073741824L;

    private Coordinates co;

    int fileLineNumber = 0;
    
    @GetMapping("/home")
    @ResponseBody
    public String home(){
        return "home";
    }

    @GetMapping(value = "/anatomy-body")
    public Map<String, Object> anatomyBody(@RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            System.out.println(String.format("GET: Header '%s' = %s", key, value));
        });
        Map<String, Object> root = new LinkedHashMap<String, Object>();

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("Weight", 85); // kg
        map.put("Body Fat Percentage", 23);// Percentage
        map.put("BMI", 22.1);
        map.put("Basal Metabolic Rate", 1850.2);// kcal/d
        map.put("Visceral Fat Level", 14.3);
        map.put("Muscle Mass", 70.8); // kg
        map.put("Bone Mineral Content", 3.8);// kg
        map.put("Protein", 23.5); // Percentage
        map.put("Body Water Percentage", 51.7); // percentage

        // 24 hours Stat
        root.put("Step per Minute", UtilFunction.statBasedTime(0));
        root.put("Heart Rate", UtilFunction.statRandomTime(70, 80)); // BPM
        root.put("Active KCal", UtilFunction.statRandomTime(170, 200));
        root.put("Distance", UtilFunction.statRandomTime(10, 100));
        root.put("Blood Oxygen", UtilFunction.statRandomTime(95, 100)); // peercentage

        root.put("Today Metrics", map);

        return root;
    }

    @GetMapping(value = "/wind-energy")
    public Map<String, Object> windEnergy(@RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            System.out.println(String.format("GET: Header '%s' = %s", key, value));
        });
        Map<String, Object> root = new LinkedHashMap<String, Object>();

        List<Object> items = new LinkedList<Object>();       
        Map<String, Object> map1 = createEnergyMetrics("OMNJI-121");
        Map<String, Object> map2 = createEnergyMetrics("OMNJI-122");
        Map<String, Object> map3 = createEnergyMetrics("OMNJI-123");
        
        List<Map<String, Object>> powers = new LinkedList<Map<String, Object>>();
        powers.add(map1);
        powers.add(map2);
        powers.add(map3);

        items.add(map1);
        items.add(map2);
        items.add(map3);

        root.put("Turbines", items);
        root.put("Powers", createPowerMetrics(powers));
        root.put("Temperature", UtilFunction.randomNum(15.0, 50.0));
		return root;
    }

    Map<String, Object> createPowerMetrics(List<Map<String, Object>> list){
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
              Map<String, Object> map = list.get(i);
              Object key = map.get("Model");
              Object value = map.get("Turbine Power");
              
              result.put(key.toString(), value);
            }
        }
        return result;
    }

    Map<String, Object> createEnergyMetrics(String model){
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("Turbine Speed", UtilFunction.randomNum(100.0, 200.0));
        map.put("Turbine Torque", UtilFunction.randomNum(100.0, 200.0));
        map.put("Wind Speed", UtilFunction.randomNum(100.0, 200.0));
        map.put("Wind Direction", UtilFunction.randomNum(45.0, 90.0));
        map.put("Turbine Power", UtilFunction.randomNum(100.0, 200.0));
        map.put("lat", 6.11);
        map.put("lng", 100.37);
        map.put("Firmware Version", "1.0.22223");
        map.put("Model", model);
        return map;
    }

    @GetMapping(value = "/retail-shop")
    public Map<String, Object> retailShop(@RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            System.out.println(String.format("GET: Header '%s' = %s", key, value));
        });
        Map<String, Object> root = new LinkedHashMap<String, Object>();

        Map<String, Double> reportMap = new LinkedHashMap<String, Double>();
        reportMap.put("Sales Revenue", UtilFunction.randomNum(1000000.0, 9000.0));
        reportMap.put("Customer", UtilFunction.randomNum(1000000.0, 9000.0));
        reportMap.put("Avg Transaction Price", UtilFunction.randomNum(1000000.0, 9000.0));
        reportMap.put("Avg Unit per Customer", UtilFunction.randomNum(1.0, 3.0));

        Map<String, Double> map = new LinkedHashMap<String, Double>();
        map.put("Jan", UtilFunction.randomNum(1000.0, 9000.0));
        map.put("Feb", UtilFunction.randomNum(1000.0, 9000.0));
        map.put("Mar", UtilFunction.randomNum(1000.0, 9000.0));
        map.put("Apr", UtilFunction.randomNum(1000.0, 9000.0));
        map.put("May", UtilFunction.randomNum(1000.0, 9000.0));
        map.put("Jun", UtilFunction.randomNum(1000.0, 9000.0));
        map.put("Jul", UtilFunction.randomNum(1000.0, 9000.0));
        map.put("Aug", UtilFunction.randomNum(1000.0, 9000.0));
        map.put("Sep", UtilFunction.randomNum(1000.0, 9000.0));
        map.put("Oct", UtilFunction.randomNum(1000.0, 9000.0));
        map.put("Nov", UtilFunction.randomNum(1000.0, 9000.0));
        map.put("Dec", UtilFunction.randomNum(1000.0, 9000.0));

        Map<String, Double> map2 = new LinkedHashMap<String, Double>();
        map2.put("Silk", UtilFunction.randomNum(1000.0, 9000.0));
        map2.put("Linen", UtilFunction.randomNum(1000.0, 9000.0));
        map2.put("Wool", UtilFunction.randomNum(1000.0, 9000.0));
        map2.put("Cotton", UtilFunction.randomNum(1000.0, 9000.0));
        map2.put("Cashmere", UtilFunction.randomNum(1000.0, 9000.0));
        map2.put("Velvet", UtilFunction.randomNum(1000.0, 9000.0));
        map2.put("Satin", UtilFunction.randomNum(1000.0, 9000.0));
        map2.put("Chiffon", UtilFunction.randomNum(1000.0, 9000.0));
        map2.put("Lace", UtilFunction.randomNum(1000.0, 9000.0));
        map2.put("Leather", UtilFunction.randomNum(1000.0, 9000.0));

        Map<String, Double> map3 = new LinkedHashMap<String, Double>();
        map3.put("Hand-dyed silk scarf", UtilFunction.randomNum(1000.0, 9000.0));
        map3.put("Handwoven cotton shawl", UtilFunction.randomNum(1000.0, 9000.0));
        map3.put("Recycled denim jacket with patches", UtilFunction.randomNum(1000.0, 9000.0));
        map3.put("Vintage sequin blouse", UtilFunction.randomNum(1000.0, 9000.0));
        map3.put("Upcycled leather boots", UtilFunction.randomNum(1000.0, 9000.0));
        map3.put("Bamboo fiber t-shirt", UtilFunction.randomNum(1000.0, 9000.0));
        map3.put("Embroidered linen tunic", UtilFunction.randomNum(1000.0, 9000.0));
        map3.put("Alpaca wool sweater", UtilFunction.randomNum(1000.0, 9000.0));
        map3.put("Organic cotton sundress", UtilFunction.randomNum(1000.0, 9000.0));
        map3.put("Handmade crochet swimsuit cover-up", UtilFunction.randomNum(1000.0, 9000.0));

        Map<String, Double> map4 = new LinkedHashMap<String, Double>();
        map4.put("Men", UtilFunction.randomNum(1000.0, 9000.0));
        map4.put("Women", UtilFunction.randomNum(1000.0, 9000.0));
        map4.put("Kids", UtilFunction.randomNum(1000.0, 9000.0));

        Map<String, Double> map5 = new LinkedHashMap<String, Double>();
        map5.put("KL", UtilFunction.randomNum(1000.0, 9000.0));
        map5.put("Penang", UtilFunction.randomNum(1000.0, 9000.0));
        map5.put("JB", UtilFunction.randomNum(1000.0, 9000.0));

        Map<String, Double> map6 = new LinkedHashMap<String, Double>();
        map6.put("Jan", UtilFunction.randomNum(0.0, 10.0));
        map6.put("Feb", UtilFunction.randomNum(0.0, 10.0));
        map6.put("Mar", UtilFunction.randomNum(0.0, 10.0));
        map6.put("Apr", UtilFunction.randomNum(0.0, 10.0));
        map6.put("May", UtilFunction.randomNum(0.0, 10.0));
        map6.put("Jun", UtilFunction.randomNum(0.0, 10.0));
        map6.put("Jul", UtilFunction.randomNum(0.0, 10.0));
        map6.put("Aug", UtilFunction.randomNum(0.0, 10.0));
        map6.put("Sep", UtilFunction.randomNum(0.0, 10.0));
        map6.put("Oct", UtilFunction.randomNum(0.0, 10.0));
        map6.put("Nov", UtilFunction.randomNum(0.0, 10.0));
        map6.put("Dec", UtilFunction.randomNum(0.0, 10.0));

        Map<String, Double> map7 = new LinkedHashMap<String, Double>();
        map7.put("Jan", UtilFunction.randomNum(0.0, 3.0));
        map7.put("Feb", UtilFunction.randomNum(0.0, 3.0));
        map7.put("Mar", UtilFunction.randomNum(0.0, 3.0));
        map7.put("Apr", UtilFunction.randomNum(0.0, 3.0));
        map7.put("May", UtilFunction.randomNum(0.0, 3.0));
        map7.put("Jun", UtilFunction.randomNum(0.0, 3.0));
        map7.put("Jul", UtilFunction.randomNum(0.0, 3.0));
        map7.put("Aug", UtilFunction.randomNum(0.0, 3.0));
        map7.put("Sep", UtilFunction.randomNum(0.0, 3.0));
        map7.put("Oct", UtilFunction.randomNum(0.0, 3.0));
        map7.put("Nov", UtilFunction.randomNum(0.0, 3.0));
        map7.put("Dec", UtilFunction.randomNum(0.0, 3.0));

        

        root.put("Report", reportMap);
        root.put("Visitors", map);
        root.put("Sold Item", map2);
        root.put("Collections Revenue", map3);
        root.put("Sales by Divison", map4);
        root.put("Sales by City", map5);
        root.put("Out of Stock percentage", map6);
        root.put("Unit per Trasaction", map7);
        root.put("Today Popular Times", UtilFunction.statBasedTime(UtilFunction.randomInt(0, 100)));
        root.put("Cashier Income", UtilFunction.statBasedTime(UtilFunction.randomInt(100, 500)));
		return root;
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
	public List<Map<String, Object>> chartData() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Map<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("Jan", "http://localhost:8080/images/meditation.png");
            map.put("Feb", UtilFunction.randomNum(10.0, 90.0));
            map.put("Mar", UtilFunction.randomNum(10.0, 90.0));
            map.put("Apr", UtilFunction.randomNum(0.0, 90.0));
            map.put("May", UtilFunction.randomNum(10.0, 90.0));
            map.put("Jun", UtilFunction.randomNum(10.0, 90.0));
            list.add(map);
        }
		return list;
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
        List<Object> list2 = new ArrayList<Object>();
        list2.add(UtilFunction.randomNum(10.0, 90.0));
        list2.add(UtilFunction.randomNum(10.0, 90.0));
        list.add(list2);

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

    @Scheduled(fixedRate = 1000) // Milliseconds
    public void readLinesPeriodically() throws IOException {
        System.out.println("fileLineNumber: "+ fileLineNumber);
        File file  = trajectory.getFile();
        List<String> lines = FileUtils.readLines(file, "UTF-8");
        String line = lines.get(fileLineNumber);
        String[] arr = StringUtils.split(line, ","); 

        Coordinates coordinates = new Coordinates();
        coordinates.setLat(Float.parseFloat(arr[0]));
        coordinates.setLng(Float.parseFloat(arr[1]));
        this.co = coordinates;

        fileLineNumber++;
        fileLineNumber = fileLineNumber % lines.size();
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
