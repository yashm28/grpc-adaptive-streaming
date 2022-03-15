package grpc.route.server;

import com.google.protobuf.ByteString;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import route.RequestServiceGrpc;
import route.Response;
import visualization.Plotter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class RequestServiceImpl extends RequestServiceGrpc.RequestServiceImplBase {

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
            Plotter.initPlotter();
            Properties conf = RequestServiceImpl.getConfiguration(new File(path));
            RequestServer.configure(conf);

            final RequestServiceImpl impl = new RequestServiceImpl();
            impl.start();
            impl.blockUntilShutdown();

        } catch (IOException e) {
            // TODO better error message
            e.printStackTrace();
        }
    }

    private void start() throws Exception {
        svr = ServerBuilder.forPort(RequestServer.getInstance().getServerPort()).addService(new RequestServiceImpl())
                .build();

        System.out.println("-- starting server");
        svr.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                RequestServiceImpl.this.stop();
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
    public void request(route.Request request, StreamObserver<Response> responseObserver) {

        // TODO refactor to use RouteServer to isolate implementation from
        // transportation

        route.Response.Builder builder = route.Response.newBuilder();

        // routing/header information
        builder.setOrigin(RequestServer.getInstance().getServerID());
        builder.setDestination(request.getOrigin());
        try {
            ByteOffset bo = readFile(
                    request.getPath(), request.getOffset(), request.getResponseTime(), request.getChunkSize()
            );
            builder.setPayload(ByteString.copyFrom(bo.data));
            builder.setLast(bo.offset == -1);
            builder.setOffset(bo.offset);
            route.Response rtn = builder.build();

            responseObserver.onNext(rtn);
            responseObserver.onCompleted();
        } catch (IOException ex){
            ex.printStackTrace();
        }

    }

    ByteOffset readFile(String path, long offset, long time, long chunkSize) throws IOException {
        Plotter.chart.updateXYSeries(
                "a", new double[] { (double)chunkSize }, new double[] { (double)time }, new double[] {}
        );
        File file = new File(path);
        if (!file.exists()) {
            if(!file.createNewFile()) {
                throw new IOException();
            }
        }
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        raf.seek(offset);
        System.out.println(chunkSize + " " + time);
        if (time > 2000) chunkSize /= 2;
        else if (time < 1000) chunkSize *= 1.5;
        else chunkSize += 2048;
        byte[] data = new byte[(int)chunkSize];
        int readBytes = raf.read(data);
        if (readBytes == -1) {
            return new ByteOffset(-1, new byte[0]);
        }
        if (readBytes != data.length) {
            byte[] smallerData = new byte[readBytes];
            System.arraycopy(data, 0, smallerData, 0, readBytes);
            return new ByteOffset(offset + readBytes, smallerData);
        }
        raf.close();
        return new ByteOffset(offset + readBytes, data);
    }

    static class ByteOffset {
        protected long offset;
        protected byte[] data;
        public ByteOffset(long offset, byte[] data) {
            this.offset = offset;
            this.data = data;
        }
    }

}
