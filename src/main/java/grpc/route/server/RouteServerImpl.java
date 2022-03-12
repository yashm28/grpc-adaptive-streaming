package grpc.route.server;

import java.io.*;
import java.util.Properties;

import com.google.protobuf.ByteString;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import route.RouteServiceGrpc.RouteServiceImplBase;

public class RouteServerImpl extends RouteServiceImplBase {
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

	/**
	 * TODO refactor this!
	 * 
	 * @param msg
	 * @return
	 */
	protected ByteString process(route.Route msg) {

		// TODO placeholder
		String content = new String(msg.getPayload().toByteArray());
		System.out.println("-- got: " + msg.getOrigin() + ", path: " + msg.getPath() + ", with: " + content);

		// TODO complete processing
		//final String blank = "blank";
		byte[] raw = content.getBytes();

		return ByteString.copyFrom(raw);
	}

	public static void main(String[] args) throws Exception {
		// TODO check args!

		String path = args[0];
		try {
			Properties conf = RouteServerImpl.getConfiguration(new File(path));
			RouteServer.configure(conf);

			final RouteServerImpl impl = new RouteServerImpl();
			impl.start();
			impl.blockUntilShutdown();

		} catch (IOException e) {
			// TODO better error message
			e.printStackTrace();
		}
	}

	private void start() throws Exception {
		svr = ServerBuilder.forPort(RouteServer.getInstance().getServerPort()).addService(new RouteServerImpl())
				.build();

		System.out.println("-- starting server");
		svr.start();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				RouteServerImpl.this.stop();
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
	public void request(route.Route request, StreamObserver<route.Route> responseObserver) {

		// TODO refactor to use RouteServer to isolate implementation from
		// transportation

		route.Route.Builder builder = route.Route.newBuilder();

		// routing/header information
		builder.setId(RouteServer.getInstance().getNextMessageID());
		builder.setOrigin(RouteServer.getInstance().getServerID());
		builder.setDestination(request.getOrigin());
		builder.setPath(request.getPath());

		// do the work
		builder.setPayload(process(request));
		try {
			writeToFile(builder.getPayload(), builder.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		route.Route rtn = builder.build();
		responseObserver.onNext(rtn);
		responseObserver.onCompleted();
	}

	void writeToFile(ByteString content, String path) throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			//boolean createdDir = file.mkdirs(); // If the directory containing the file and/or its parent(s) does not exist
			boolean createdFile = file.createNewFile();
			if (/*!createdDir || */!createdFile) throw new IOException();
		}
		FileWriter writer = new FileWriter(file);
		writer.write(content.toStringUtf8());
	}
}
