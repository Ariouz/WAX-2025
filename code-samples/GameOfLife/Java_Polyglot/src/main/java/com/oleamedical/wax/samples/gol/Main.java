package com.oleamedical.wax.samples.gol;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.python.embedding.utils.GraalPyResources;
import org.graalvm.python.embedding.utils.VirtualFileSystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Main {

    private static VirtualFileSystem vfs;

    public static Context createPythonContext(String pythonResourcesDirectory) {
        return GraalPyResources.contextBuilder(Path.of(pythonResourcesDirectory)).build();
    }

    public static Context createPythonContextFromResources() {
        if (vfs == null) {
            vfs = VirtualFileSystem.newBuilder().allowHostIO(VirtualFileSystem.HostIO.READ).build();
        }
        return GraalPyResources.contextBuilder(vfs).build();
    }

    public static void main(String[] args) throws IOException {
        Context context = createPythonContextFromResources();

        File pythonFile = new File("main.py");
        System.out.println("Python file: " + pythonFile.getAbsolutePath());

        Source source = Source.newBuilder("python", pythonFile).build();

        context.eval(source);

        context.close();
    }
}