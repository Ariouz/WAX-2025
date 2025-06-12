package com.oleamedical.wax.demos.springpy.controller;

public record RequestResponse(String status, String message) {

    private static Object data;

    public RequestResponse data(Object data) {
        RequestResponse.data = data;
        return this;
    }

}