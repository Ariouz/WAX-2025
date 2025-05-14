# Questions and Answers

### Key performance indices ?
See [benchmarks](/bench)

## GraalVM

### How to natively compile a JavaFx app ?
Set Java version to [GluonHQ JavaFX](https://gluonhq.com/products/javafx/) and replace the native-image maven plugin by the [GluonfFX plugin](https://docs.gluonhq.com/#_gluonfx_plugin_for_maven).

### How to natively compile a Swing app ?
No AWT support. Use [Liberica NIK](https://bell-sw.com/pages/downloads/native-image-kit/#nik-23-(jdk-17)) and [compile](https://docs.bell-sw.com/liberica-nik/latest/how-to/using-nik-with-desktop-applications/).

### Native SpringBoot ?
[Spring.io](https://start.spring.io/) => Dependencies => GraalVM Native Support


## GraalPY