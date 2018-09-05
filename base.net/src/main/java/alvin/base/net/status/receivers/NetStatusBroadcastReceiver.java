package alvin.adv.net.status.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import alvin.adv.net.status.handlers.NetStatusEventHandler;
import alvin.adv.net.status.network.NetworkStatus;

public class NetStatusBroadcastReceiver extends BroadcastReceiver {

    private final NetStatusEventHandler eventHandler;

    public NetStatusBroadcastReceiver() {
        this.eventHandler = NetStatusEventHandler.getInstance();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            NetworkStatus status = NetworkStatus.getStatus(context);
            eventHandler.send(status);
        }
    }
}
