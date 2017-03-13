# NettyServer

Equalizer based on [Java 8](http://docs.oracle.com/javase/8/javase-clienttechnologies.htm).


## New Features!
  - Added maven assembly.

## Installing and Running

#### Requirements
NettyServer  runs on any system equipped with the Java Virtual Machine (1.8 or newer), which can be downloaded at no cost from [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html).
[Maven](https://maven.apache.org/index.html) to assembly.

#### Running

```sh
$ java -jar <path to jar> -p <port> -c <threads> -r <document_root>
```

#### Building NettyServer From Source
To compile NettyServer from source, you need a Java compiler supporting Java 1.8 and JAVA_HOME pointing to this JDK.
You can compile EqualizerFX with [Maven](https://maven.apache.org/index.html).

```sh
$ https://github.com/DmitryZagr/ru.mail.server.git
$ cd ru.mail.server
$ make
$ java -jar target/httpd -p <port> -c <threads> -r <document_root>
```
