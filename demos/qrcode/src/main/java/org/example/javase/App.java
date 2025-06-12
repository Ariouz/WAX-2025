/*
 * Copyright (c) 2024, Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at https://opensource.org/license/UPL.
 */

package org.example.javase;

import org.graalvm.python.embedding.utils.GraalPyResources;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String path = "./python-resources";
        // ①
        try (var context = GraalPyResources.contextBuilder(Path.of(path)).build()) {
            QRCode qrCode = context.eval("python", "import qrcode; qrcode").as(QRCode.class);
            IO io = context.eval("python", "import io; io").as(IO.class);

            BytesIO bytesIO = io.BytesIO();
            qrCode.make("Hello from GraalPy on JDK " + System.getProperty("java.version")).save(bytesIO);

            var qrImage = ImageIO.read(new ByteArrayInputStream(bytesIO.getvalue().toByteArray()));
            ImageIO.write(qrImage, "png", new File("./result.png"));
        }
    }
}
