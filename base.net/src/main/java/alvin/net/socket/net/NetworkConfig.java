package alvin.net.socket.net;

import alvin.utils.ApplicationConfig;

public class NetworkConfig {

    private final String host;
    private final int port;

    public NetworkConfig() {
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
