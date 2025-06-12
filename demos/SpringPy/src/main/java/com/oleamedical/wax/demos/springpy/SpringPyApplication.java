package com.oleamedical.wax.demos.springpy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/test")
    public String test(@RequestParam(name="name", defaultValue = "test") String name) {
        return String.format("Hello, %s!", name);
    }

}
