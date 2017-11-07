package alvin.base.net.status.handlers;

import alvin.base.net.status.network.NetworkStatus;

public interface OnNetStatusChangedListener {
    void onNetworkStatusChanged(NetworkStatus status);
}
