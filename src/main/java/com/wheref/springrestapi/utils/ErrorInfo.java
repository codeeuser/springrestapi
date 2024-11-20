package com.wheref.springrestapi.utils;

public class ErrorInfo {
    private final int code;
    private final String description;
    public ErrorInfo(int code, String description) {
        this.code = code;
        this.description = description;
    }
    public int getCode() {
        return code;
    }
    public String getDescription() {
        return description;
    }
}