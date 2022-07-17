package com.wheref.springrestapi.model;

import java.util.Map;

public class Geometry {
    String type;
    Map<String, Float> coordinates;
    
    public Geometry(String type, Map<String, Float> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Map<String, Float> getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(Map<String, Float> coordinates) {
        this.coordinates = coordinates;
    }

    
}
