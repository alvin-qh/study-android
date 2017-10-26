package alvin.net.status.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public enum NetworkStatus {
    NONE, MOBILE, WIFI;

    public static NetworkStatus getStatus(Context context) {
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
