package alvin.net.status.handlers;

import alvin.net.status.network.NetworkStatus;

public interface OnNetStatusChangedListener {
    void onNetworkStatusChanged(NetworkStatus status);
}
