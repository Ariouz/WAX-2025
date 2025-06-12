package com.oleamedical.wax.demos.springpy.controller;

import com.oleamedical.wax.demos.springpy.python.PythonController;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.*;

@RestController
public class SegmentationController {

    private final PythonController pythonController;

    public SegmentationController() throws Exception {
        pythonController = new PythonController();
    }

    @PostMapping(
            value = "/segment",
            produces = MediaType.IMAGE_PNG_VALUE)
    public RequestResponse segment(@RequestParam("dcmFile") MultipartFile file) throws IOException {
        if (file.isEmpty()) return new RequestResponse("error", "File is empty");
        System.out.println(file.getOriginalFilename());

        if (!file.getOriginalFilename().endsWith(".dcm")) return new RequestResponse("error", "File is not a dcm file");

        //TODO Graalpy brain segmentation
        File output = pythonController.segment(file.getResource().getFile());
        FileInputStream fis = new FileInputStream(output);
        InputStream in = new BufferedInputStream(fis);

        pythonController.getContext().eval("python", "print('hello world')");

        return new RequestResponse("success", "Dicom file segmented").data(in.readAllBytes());
    }

}
