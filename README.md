# Apple M1 Issues

Note the protobuf (protoc) plugin for java is not currently support on apple M1. This
is used to build java source code files from protobuf (.proto) files.

The work around is to generate the source files on an intel based computer. Not the 
best, but it works rather than installing Rosetta.

# The gRPC blocking lab

build the gRPC/protobuf (proto3) classes from the .proto. 

This project/lab provides you with an example of an gRPC client and server that is more 
appropriate than the low level socket example. This improves upon the design to better
manage the socket connection and provide a encoding/decoding package for the message.

As a result of the increased capabilities, the installation, building, and running becomes
more complex. You will need to download and configure several packages as well as, modify
this project to adjust to the current supported gRPC and Protobuf packages 

## Versions of packages

Protobuf which version? 3.6? The current stable release is 3.6. Note I ran into issues
with 3.6 and java 11 involving conflicts in two seperate jars. 


## Building

Using protobuf requires you to generate code (classes) from the protobuf 4GL 
descriptions of the messages and services. These generated classes are then
compiled into your code base. 

## Steps:

  * Download the gRPC java codegen plugin from: https://github.com/grpc/grpc-java
  * Build as instructed
  * Save/Copy built plugin to your general toolset (e.g., /usr/local/blah)
  * Create a lmod .lua for protobuf and gRPC (provided as an example)
  * Modify the protoc paths in the build_pb.sh to match your toolset

## Benchmarking

   * Like the previous lab, can you construct and test performance of this 
     implementation?  

## Notes

  * Notice the message .proto's use of a byte array for the payload (message 
    body). What are the advantages and concerns with this approach?
  * What are the memory utilization for this code? How do you obtain memory in
    use and free?
