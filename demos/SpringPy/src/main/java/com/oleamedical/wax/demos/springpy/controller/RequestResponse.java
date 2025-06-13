package com.oleamedical.wax.demos.springpy.controller;

public class RequestResponse {
    private String status;
    private String message;
    private Object data;

    public RequestResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public Object getData() { return data; }

    public RequestResponse setData(Object data) {
        this.data = data;
        return this;
    }
}
