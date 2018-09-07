package alvin.base.net.status.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.ArraySet;

import java.util.Set;

public class NetStatusReceiver extends BroadcastReceiver {
    private final Set<OnNetStatusChangedListener> listeners = new ArraySet<>();

    public void addOnNetStatusChangedListener(@NonNull OnNetStatusChangedListener listener) {
        this.listeners.add(listener);
    }

    public void removeOnNetStatusChangedListener(@NonNull OnNetStatusChangedListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            final NetworkStatus status = new NetworkStatus(context);
            final NetworkInfo info = status.getActiveNetworkInfo();
            if (info != null) {
                listeners.forEach(l -> l.onNetworkStatusChanged(info.getTypeName(), info.isConnected()));
            }
        }
    }
}
