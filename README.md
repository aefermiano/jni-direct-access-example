# JNI Direct Access Example

Very simple example showing how to use [Agrona](ttps://github.com/real-logic/agrona) library buffers to share data with native code via direct memory access.

Unless you use some more complicated stuff (e.g: callbacks from native code which requires JNI code to create global references), direct memory access allows you to design your applications in a way to get rid of JNI code and pass only primitives, which makes code easier to maintain.

Native code tested only with GCC on GNU/Linux amd64.

## Building

### Java code

```
mvn clean package
```

Build will also generate the C header into "native" folder.

### C code

First, figure out where your JAVA\_HOME is. This is necessary because JNI requires some headers that come with your JDK.

Then:

```
cd native
cmake -DJAVA_HOME=/usr/lib/jvm/java-18-openjdk-amd64
make
```

## Using

Tests are in "main" function:

```
java -Djava.library.path=./native -jar target/jni-direct-access-example-*-jar-with-dependencies.jar
```
