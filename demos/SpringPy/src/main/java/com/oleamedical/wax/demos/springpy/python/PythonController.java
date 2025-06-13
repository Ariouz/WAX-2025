package com.oleamedical.wax.demos.springpy.python;

import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam;
import org.dcm4che3.io.DicomInputStream;
import org.graalvm.polyglot.*;
import org.graalvm.polyglot.io.IOAccess;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;

public class PythonController {

    private final Context context;

    private final String PYTHON = "python";

    private BrainSegmentation brainSegmentation;

    public PythonController() {
        String pythonPath = "target/classes/org.graalvm.python.vfs/venv/lib/python3.11/site-packages";

        this.context = Context.newBuilder("python")
                .allowAllAccess(true)
                .allowHostClassLoading(true)
                .allowPolyglotAccess(PolyglotAccess.ALL)
                .allowExperimentalOptions(true)
                .allowHostAccess(HostAccess.ALL)
                .allowIO(IOAccess.ALL)
                .allowNativeAccess(true)
                .allowCreateProcess(true)
                .allowCreateThread(true)
                .option("python.PythonPath", pythonPath)
                .option("python.WarnExperimentalFeatures", "false")
                .build();

        this.context.initialize(PYTHON);
        this.context.eval(PYTHON, "import sys; print(sys.path)");
        this.context.eval(PYTHON, "sys.setrecursionlimit(2000)");

        //evalFile("unet_model.py");
        evalFile("brain_segmentation.py");
        this.brainSegmentation = context.eval(PYTHON, "BrainSegmentation()").as(BrainSegmentation.class);
    }

    public void evalFile(String filename) {
        File sourceFile = null;
        try {
            sourceFile = getFileFromResource(filename);
            Source source = Source.newBuilder(PYTHON, sourceFile).build();
            context.eval(source);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Context getContext() {
        return context;
    }

    public static int[] toUnsignedBytes(byte[] data) {
        int[] unsigned = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            unsigned[i] = data[i] & 0xFF; // convert signed byte to 0-255
        }
        return unsigned;
    }


    public File segment(byte[] data) throws IOException {
        File file = File.createTempFile("segment", ".png");

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
        }

        BufferedImage bufferedImage;
        bufferedImage = ImageIO.read(file);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", out);

        int[] intArray = brainSegmentation.segment(toUnsignedBytes(out.toByteArray()));

        byte[] byteArray = new byte[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            byteArray[i] = (byte) intArray[i];
        }

        ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
        BufferedImage bufferedImage2 = ImageIO.read(in);
        ImageIO.write(bufferedImage2, "png", out);

        File output = new File("output.png");
        ImageIO.write(bufferedImage2, "png", output);

        return output;
    }

    private File getFileFromResource(String fileName) throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {

            return new File(resource.toURI());
        }

    }

}
