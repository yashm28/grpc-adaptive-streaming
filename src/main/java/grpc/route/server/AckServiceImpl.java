package grpc.route.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import route.AckServiceGrpc;
import route.Request;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AckServiceImpl extends AckServiceGrpc.AckServiceImplBase {

    private Server svr;

    private static Properties getConfiguration(final File path) throws IOException {
        if (!path.exists())
            throw new IOException("missing file");

        Properties rtn = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            rtn.load(fis);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

        return rtn;
    }

    public static void main(String[] args) throws Exception {
        // TODO check args!

        String path = args[0];
        try {
            Properties conf = AckServiceImpl.getConfiguration(new File(path));
            RequestServer.configure(conf);

            final AckServiceImpl impl = new AckServiceImpl();
            impl.start();
            impl.blockUntilShutdown();

        } catch (IOException e) {
            // TODO better error message
            e.printStackTrace();
        }
    }

    private void start() throws Exception {
        svr = ServerBuilder.forPort(RequestServer.getInstance().getServerPort()).addService(new AckServiceImpl())
                .build();

        System.out.println("-- starting server");
        svr.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                AckServiceImpl.this.stop();
            }
        });
    }

    protected void stop() {
        svr.shutdown();
    }

    private void blockUntilShutdown() throws Exception {
        svr.awaitTermination();
    }

    @Override
    public void request(route.Response request, StreamObserver<Request> responseObserver) {

        // TODO refactor to use RouteServer to isolate implementation from
        // transportation

        route.Request.Builder builder = route.Request.newBuilder();

        // routing/header information
        builder.setOffset(1);
        builder.setOrigin(RequestServer.getInstance().getServerID());
        builder.setDestination(request.getOrigin());
        builder.setPath(request.getPath());
        route.Request rtn = builder.build();

        responseObserver.onNext(rtn);
        responseObserver.onCompleted();
    }

}
