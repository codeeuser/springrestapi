package com.wheref.springrestapi.controller;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/panorama")
public class PanoramaController {

    @Value("classpath:images/panorama.jpg")
    Resource panorama;

    @Value("classpath:images/car.jpeg")
    Resource car;
    
    @GetMapping(value = "/house", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] house() throws IOException {
        System.out.println("--- LOADING HOUSE ---");
        return IOUtils.toByteArray(panorama.getInputStream());
    } 

    @GetMapping(value = "/car", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] car() throws IOException {
        System.out.println("--- LOADING CAR ---");
        return IOUtils.toByteArray(car.getInputStream());
    } 
}
