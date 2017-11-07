package alvin.base.net.socket.net;


import alvin.lib.common.util.ApplicationConfig;

public class NetworkConfig {

    private final String host;
    private final int port;

    NetworkConfig() {
        ApplicationConfig config = ApplicationConfig.getInstance();

        this.host = config.get("socket.host", "localhost");
        this.port = config.getAsInt("socket.port", 8080);
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
