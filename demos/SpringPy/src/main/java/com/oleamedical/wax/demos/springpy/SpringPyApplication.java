package com.oleamedical.wax.demos.springpy;

import ch.qos.logback.classic.encoder.JsonEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

@SpringBootApplication
@RestController
public class SpringPyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPyApplication.class, args);
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    public record RequestResponse(String status, String message) {};

    @PostMapping("/segment")
    public RequestResponse segment(@RequestParam("dcmFile") MultipartFile file) {
        if (file.isEmpty()) return new RequestResponse("error", "File is empty");
        System.out.printf(file.getOriginalFilename());

        if (!file.getOriginalFilename().endsWith(".dcm")) return new RequestResponse("error", "File is not a dcm file");

        //TODO Graalpy brain segmentation
        return new RequestResponse("success", "File segmented successfully");
    }

}
