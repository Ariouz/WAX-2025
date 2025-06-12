# QRCode generation using python qrcode

The source code come from: https://github.com/graalvm/graal-languages-demos/tree/main/graalpy/graalpy-javase-guide

It is modified so it generated png instead of displaying it in a JFrame.

The META-INF/native-image files have been generated with:

```java -agentlib:native-image-agent=config-output-dir=target/native-agent-config -cp target/lib/*:target/javase-1.0-SNAPSHOT.jar org.example.javase.App```

To run in graalpy:
```mvn exec:java -Dexec.mainClass=org.example.javase.App```

To build a native executable:
```mvn clean package -Pnative```
