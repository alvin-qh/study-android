package alvin.base.net.status.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkStatus {
    private final ConnectivityManager manager;

    public NetworkStatus(Context context) {
        this.manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public NetworkInfo getActiveNetworkInfo() {
        return this.manager.getActiveNetworkInfo();
    }
}
