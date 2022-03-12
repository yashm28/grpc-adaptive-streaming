package grpc.route.client;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import route.Request;
import route.RequestServiceGrpc;
import route.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Client {
    private static long clientID = 501;
    private static int port = 2345;

    public static void main(String[] args) {
        ManagedChannel ch = ManagedChannelBuilder.forAddress(args[0], Client.port).usePlaintext().build();

        RequestServiceGrpc.RequestServiceBlockingStub stub = RequestServiceGrpc.newBlockingStub(ch);

        boolean last = false;
        long offset = -1;
        long destination = 0;
        while(!last) {
            Request.Builder bld = Request.newBuilder();
            bld.setOffset(offset + 1);
            bld.setOrigin(Client.clientID);
            bld.setDestination(destination);
            bld.setPath("./sent/Ecoli.txt");

            // blocking!
            Response r = stub.request(bld.build());

            // TODO response handling
            String payload = new String(r.getPayload().toByteArray());
            System.out.println("reply: " + r.getOffset() + ", from: " + r.getOrigin() + ", payload: " + payload);
            try {
                if (!r.getLast()) {
                    writeToFile("./received/Ecoli.txt", r.getPayload());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Client: " + r.getLast());
            last = r.getLast();
            offset = r.getOffset();
            destination = r.getDestination();
        }

        ch.shutdown();
    }

    static void writeToFile(String path, ByteString data) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            if(!file.createNewFile()) {
                throw new IOException();
            }
        }
//        FileOutputStream output = new FileOutputStream("filename", true);
//        System.out.println(data.toStringUtf8());
//        output.write(data.toByteArray());
//        output.flush();
//        output.close();
        FileWriter writer = new FileWriter(file, true);
        writer.write(data.toStringUtf8());
        writer.flush();
        writer.close();
    }

}
