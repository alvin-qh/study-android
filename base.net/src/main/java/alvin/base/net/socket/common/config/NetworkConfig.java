package alvin.base.net.socket.common.config;


import alvin.lib.common.utils.ApplicationConfig;

public class NetworkConfig {
    private static final int DEFAULT_PORT = 8080;

    private final String host;
    private final int port;

    public NetworkConfig() {
        ApplicationConfig config = ApplicationConfig.getInstance();

        this.host = config.get("socket.host", "localhost");
        this.port = config.getAsInt("socket.port", DEFAULT_PORT);
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
