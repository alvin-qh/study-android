package alvin.base.net.status.views;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import alvin.base.net.R;
import alvin.base.net.status.network.NetStatusReceiver;
import alvin.base.net.status.network.NetworkStatus;
import alvin.base.net.status.network.OnNetStatusChangedListener;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NetworkStatusActivity extends AppCompatActivity implements OnNetStatusChangedListener {

    @BindView(R.id.bar_network_status)
    NetworkStatusBar barStatus;

    private NetStatusReceiver netStatusReceiver;

    private static class NetworkCallback extends ConnectivityManager.NetworkCallback {

        private final WeakReference<Context> contextRef;

        NetworkCallback(Context context) {
            this.contextRef = new WeakReference<>(context);
        }

        @Override
        public void onAvailable(Network network) {
            final Context context = contextRef.get();
            if (context != null) {
                Toast.makeText(context, R.string.string_network_available, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onLost(Network network) {
            final Context context = contextRef.get();
            if (context != null) {
                Toast.makeText(context, R.string.string_network_lost, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_status);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerNetworkStatusReceiver();
        registerNetworkStatusCallback();

        final NetworkInfo info = new NetworkStatus(this).getActiveNetworkInfo();
        if (info != null) {
            barStatus.showNetworkStatusChangeMessage(info.getTypeName(), info.isConnected());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterNetworkStatusReceiver();
    }

    @Override
    public void onNetworkStatusChanged(String name, boolean isConnected) {
        barStatus.showNetworkStatusChangeMessage(name, isConnected);
    }

    private void registerNetworkStatusReceiver() {
        netStatusReceiver = new NetStatusReceiver();
        netStatusReceiver.addOnNetStatusChangedListener(this);

        registerReceiver(netStatusReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void unregisterNetworkStatusReceiver() {
        if (netStatusReceiver != null) {
            netStatusReceiver.removeOnNetStatusChangedListener(this);
            unregisterReceiver(netStatusReceiver);
            netStatusReceiver = null;
        }
    }

    private void registerNetworkStatusCallback() {
        final ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            final NetworkRequest request = new NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .build();
            manager.registerNetworkCallback(request, new NetworkCallback(this));
        }
    }
}
