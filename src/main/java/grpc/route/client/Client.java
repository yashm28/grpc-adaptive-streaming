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
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
    private static long clientID;
    private static int port;

    public static void main(String[] args) {

        port = Integer.valueOf(args[1]);
        clientID = Long.valueOf(args[2]);
        String fileName = args[3];
        int algorithm = Integer.valueOf(args[4]);

        ManagedChannel ch = ManagedChannelBuilder.forAddress(args[0], Client.port).usePlaintext().build();

        RequestServiceGrpc.RequestServiceBlockingStub stub = RequestServiceGrpc.newBlockingStub(ch);

        boolean last = false;
        long offset = -1;
        long destination = 0;
        long responseTime = 200;
        long chunkSize = 4096;
        int index = 0;
        boolean slowStartCompleted = false;
        long timeSum = 0;
        double averageTime = 0;
        double averageChunkSize = 0;
        String timestamp = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        while(!last) {
            Request.Builder bld = Request.newBuilder();
            bld.setOffset(offset + 1);
            bld.setOrigin(Client.clientID);
            bld.setDestination(destination);
            bld.setPath("./sent/" + fileName);
            bld.setResponseTime(responseTime);
            bld.setChunkSize(chunkSize);
            bld.setAlgorithm(algorithm);
            bld.setPackageIndex(index);
            bld.setSlowStartCompleted(slowStartCompleted);
            bld.setTimeSum(timeSum);
//            Request request = bld.build();
            long start = System.currentTimeMillis();
            // blocking!
            Response r = stub.request(bld.build());
            long end = System.currentTimeMillis();
            // TODO response handling
            String payload = new String(r.getPayload().toByteArray());
            System.out.println("reply: " + (r.getOffset() - offset) + ", from: " + r.getOrigin() + ", time: " + (end - start));
            try {
                String[] split =  fileName.split("\\.");
                if (!r.getLast()) {
                    writeToFile("./received/" + split[0] + "_" + clientID + "_" + timestamp + "." + split[1] , r.getPayload());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Client: " + r.getLast());
            last = r.getLast();
            chunkSize = r.getOffset() - offset;
            offset = r.getOffset() + 1;
            destination = r.getDestination();
            responseTime = end - start;
            if (!slowStartCompleted) {
                slowStartCompleted = responseTime > 1000;
            } else {
                timeSum = index % 5 == 0 ? responseTime : timeSum + responseTime;
            }
            index++;
            averageTime += responseTime;
            averageChunkSize += chunkSize;
        }

        ch.shutdown();
        System.out.println("Average Time: " + (averageTime / index) + "Average Chunk Size" + (averageChunkSize - chunkSize / index));
    }

    static void writeToFile(String path, ByteString data) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            if(!file.createNewFile()) {
                throw new IOException();
            }
        }
        FileOutputStream output = new FileOutputStream(path, true);
        output.write(data.toByteArray());
        output.flush();
        output.close();
    }

}
