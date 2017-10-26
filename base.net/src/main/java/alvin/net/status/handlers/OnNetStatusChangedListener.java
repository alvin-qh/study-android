package alvin.net.status.handlers;

import alvin.net.status.NetworkStatus;

public interface OnNetStatusChangedListener {
    void onNetworkStatusChanged(NetworkStatus status);
}
