package com.oleamedical.wax.demos.springpy;

import com.oleamedical.wax.demos.springpy.python.PythonController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;


@SpringBootApplication
@RestController
public class SpringPyApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringPyApplication.class, args);
    }

}
