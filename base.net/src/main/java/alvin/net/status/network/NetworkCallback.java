package alvin.net.status.network;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;

public class NetworkCallback {

    private final ConnectivityManager connectivityManager;

    public NetworkCallback(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private NetworkRequest networkRequest() {
        final NetworkRequest.Builder builder = new NetworkRequest.Builder();

        builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR);

        return builder.build();
    }

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.N)
    public void registerNetworkCallback(ConnectivityManager.NetworkCallback callback) {
        final NetworkRequest request = networkRequest();
        connectivityManager.registerNetworkCallback(request, callback);
    }

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.N)
    public void requestNetwork(ConnectivityManager.NetworkCallback callback) {
        final NetworkRequest request = networkRequest();
        connectivityManager.requestNetwork(request, callback);
    }
}
