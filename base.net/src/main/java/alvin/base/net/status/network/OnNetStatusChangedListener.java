package alvin.base.net.status.network;

public interface OnNetStatusChangedListener {
    void onNetworkStatusChanged(String name, boolean isConnected);
}
