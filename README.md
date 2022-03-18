# Adaptive Streaming using GRPC

- Java Project build with gradle
- Recommended JDK version 17
- Recommended Gradle version 7.2
- GRPC max message size: 4MB (default)

## Usage:

- To run the server:
```bash
./gradlew runServer --args "./src/main/resources/request.properties"
```

- To run the client:
```bash
./gradlew runClient --args "localhost 2345 50 books.zip”
```

- To Generate Protocol Buffer (ProtoBuf) stubs:
```bash
protoc --plugin=protoc-gen-grpc-java --proto_path=src --java_out=src/main/java src/main/proto/route.proto
```
