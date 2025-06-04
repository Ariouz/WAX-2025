package com.oleamedical.wax.samples.dicom;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam;
import org.dcm4che3.imageio.plugins.dcm.DicomMetaData;
import org.dcm4che3.io.DicomInputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class DicomReader {

    public DicomReader() {}

    public BufferedImage readDicom(File dcmFile) throws IOException {
        DicomInputStream dicomInputStream = new DicomInputStream(dcmFile);

        Iterator<ImageReader> iter = ImageIO.getImageReadersByFormatName("DICOM");
        ImageReader reader = iter.next();
        DicomImageReadParam param = (DicomImageReadParam) reader.getDefaultReadParam();

        BufferedImage jpegImage = null;

        try {
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(dcmFile);
            reader.setInput(imageInputStream, false);
            jpegImage = reader.read(0, param);
            imageInputStream.close();
        } catch (Exception e) { e.printStackTrace(); }

        dicomInputStream.close();
        return jpegImage;
    }

}
