package alvin.net.status.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import alvin.net.status.NetworkStatus;
import alvin.net.status.handlers.NetStatusEventHandler;

public class NetStatusBroadcastReceiver extends BroadcastReceiver {

    private final NetStatusEventHandler eventHandler;

    public NetStatusBroadcastReceiver() {
        this.eventHandler = NetStatusEventHandler.getInstance();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            NetworkStatus status = getNetWorkState(context);
            eventHandler.send(status);
        }
    }

    public static NetworkStatus getNetWorkState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return NetworkStatus.WIFI;
                } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return NetworkStatus.MOBILE;
                }
            }
        }
        return NetworkStatus.NONE;
    }
}
