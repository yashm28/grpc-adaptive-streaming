package grpc.route.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import route.Request;
import route.RequestServiceGrpc;
import route.Response;

public class Client {
    private static long clientID = 501;
    private static int port = 2345;

    public static void main(String[] args) {
        ManagedChannel ch = ManagedChannelBuilder.forAddress(args[0], Client.port).usePlaintext().build();

        RequestServiceGrpc.RequestServiceBlockingStub stub = RequestServiceGrpc.newBlockingStub(ch);

        boolean last = false;
        long offset = 0;
        long destination = 0;
        while(!last) {
            Request.Builder bld = Request.newBuilder();
            bld.setOffset(offset + 1);
            bld.setOrigin(Client.clientID);
            bld.setDestination(destination);
            bld.setPath("./sent/info.txt");

            // blocking!
            Response r = stub.request(bld.build());

            // TODO response handling
            String payload = new String(r.getPayload().toByteArray());
            System.out.println("reply: " + r.getOffset() + ", from: " + r.getOrigin() + ", payload: " + payload);
            last = r.getLast();
            destination = r.getDestination();
        }

        ch.shutdown();
    }

}
