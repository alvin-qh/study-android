package alvin.base.net.socket.common.config;


import alvin.lib.common.utils.ApplicationConfig;

public class NetworkConfig {
    private static final int DEFAULT_PORT = 8080;

    private final int port;

    public NetworkConfig() {
        ApplicationConfig config = ApplicationConfig.getInstance();
        this.port = config.getAsInt("socket.port", DEFAULT_PORT);
    }

    public int getPort() {
        return port;
    }
}
