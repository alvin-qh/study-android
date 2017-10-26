package alvin.net.status;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import alvin.net.R;
import alvin.net.status.handlers.NetStatusEventHandler;
import alvin.net.status.handlers.OnNetStatusChangedListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StatusMainActivity extends AppCompatActivity implements OnNetStatusChangedListener {

    @BindView(R.id.notify_bar)
    View notityBar;

    @BindView(R.id.text_net_status)
    TextView textNetStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_main);

        ButterKnife.bind(this);

        NetStatusEventHandler.getInstance().addOnNetStatusChangedListener(this);
    }

    @Override
    public void onNetworkStatusChanged(NetworkStatus status) {

    }
}
