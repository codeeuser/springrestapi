package com.wheref.springrestapi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/command")
public class CommandController {
    
    @GetMapping("/top")
    public Map<String, Object> top(){
        ProcessBuilder pb = new ProcessBuilder("top", "-l", "1");
        pb.redirectError();
        try {
            Process p = pb.start();
            InputStream is = p.getInputStream();
            List<String> lines = IOUtils.readLines(is, Charset.defaultCharset());

            Map<String, Object> mapHeader = new HashMap<>();
            List<String> headerLines = new ArrayList<>();
            int index = 0;
            for (String line : lines) {
                if (index<10){
                    headerLines.add(line);
                }
                index++;
            }
            for (String header : headerLines) {
                String[] keyValue = header.split(":");
                String key = keyValue[0];
                String value = keyValue[1];
                if (key==null || value==null) continue;
                mapHeader.put(key, value);
            }

            int exitCode = p.waitFor();
            System.out.println("Top exited with " + exitCode);

            return mapHeader;
        } catch (IOException exp) {
            exp.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
