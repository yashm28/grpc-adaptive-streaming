package grpc.route.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

public class AckServer {

    protected static Logger logger = LoggerFactory.getLogger("server");
    protected static AtomicReference<AckServer> instance = new AtomicReference<AckServer>();
    protected static Properties conf;
    protected Long serverID;
    protected Integer serverPort;
    protected Long nextMessageID;

    private AckServer() {
        init();
    };

    public static void configure(Properties conf) {
        AckServer.conf = conf;
    }

    public static AckServer getInstance() {
        instance.compareAndSet(null, new AckServer());
        return instance.get();
    }

    private void init() {
        if (conf == null)
            throw new RuntimeException("server not configured!");

        // extract settings. Here we are using basic properties which, requires
        // type checking and should also include range checking as well.

        String tmp = conf.getProperty("server.id");
        if (tmp == null)
            throw new RuntimeException("missing server ID");
        serverID = Long.parseLong(tmp);

        tmp = conf.getProperty("server.port");
        if (tmp == null)
            throw new RuntimeException("missing server port");
        serverPort = Integer.parseInt(tmp);
        if (serverPort <= 1024)
            throw new RuntimeException("server port must be above 1024");

        nextMessageID = 0L;
    }

    public static Properties getConf() {
        return conf;
    }

    public Long getServerID() {
        return serverID;
    }

    public synchronized Long getNextMessageID() {
        return ++nextMessageID;
    }

    public Integer getServerPort() {
        return serverPort;
    }

}
