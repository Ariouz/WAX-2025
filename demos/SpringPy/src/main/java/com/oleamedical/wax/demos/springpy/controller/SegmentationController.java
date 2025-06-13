package com.oleamedical.wax.demos.springpy.controller;

import com.oleamedical.wax.demos.springpy.python.PythonController;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;

@RestController
public class SegmentationController {

    private final PythonController pythonController;

    public SegmentationController() throws Exception {
        pythonController = new PythonController();
    }

    @PostMapping(
            value = "/segment",
            produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> segment(@RequestParam(value = "dcmFile", required = false) MultipartFile file) throws IOException {
        if (file == null) return ResponseEntity.badRequest().build();

        if (file.isEmpty()) return ResponseEntity.badRequest().build();

        if (!file.getOriginalFilename().endsWith(".png")) return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();

        File output = pythonController.segment(file.getBytes());

        byte[] imageBytes = Files.readAllBytes(output.toPath());
        output.delete();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imageBytes);
    }


}
