package alvin.adv.net.status.handlers;

import alvin.adv.net.status.network.NetworkStatus;

public interface OnNetStatusChangedListener {
    void onNetworkStatusChanged(NetworkStatus status);
}
