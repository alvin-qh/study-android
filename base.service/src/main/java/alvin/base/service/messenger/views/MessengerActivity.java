package alvin.base.service.messenger.views;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import alvin.base.service.R;
import alvin.base.service.messenger.services.JobInfo;
import alvin.base.service.messenger.services.Messages;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessengerActivity extends AppCompatActivity {
    private static final String TAG = MessengerActivity.class.getSimpleName();

    private static final String SERVICE_ACTION = "alvin.services.MESSENGER_SERVICE";
    private static final String SERVICE_PKG = "alvin.base.service";
    private static final String SERVICE_CLS = "alvin.base.service.messenger.services.MessengerService";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BindView(R.id.tv_job_response)
    TextView tvJobResponse;

    @BindView(R.id.sv_job_response)
    ScrollView svJobResponse;

    private AtomicReference<Messenger> sendMessengerRef = new AtomicReference<>();
    private Messenger receiveMessenger = new Messenger(new ReceiveHandler(this));

    private final AtomicBoolean connected = new AtomicBoolean(false);

    private int jobId = 1;

    private static class ReceiveHandler extends Handler {
        private final WeakReference<MessengerActivity> activityRef;

        ReceiveHandler(MessengerActivity activity) {
            this.activityRef = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final MessengerActivity activity = activityRef.get();
            if (activity != null) {
                switch (msg.what) {
                case Messages.WHAT_JOB_STARTED:
                    activity.onJobStarted(msg);
                    break;
                case Messages.WHAT_JOB_FINISHED:
                    activity.onJobFinished(msg);
                    break;
                }
            }
        }
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sendMessengerRef.set(new Messenger(service));
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            sendMessengerRef.set(null);
            if (connected.compareAndSet(true, false)) {
                connectToService();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_messenger);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        connectToService();
    }

    @Override
    protected void onPause() {
        super.onPause();

        disconnectFromService();
    }

    @OnClick(R.id.btn_work)
    public void onWorkButtonClicked() {
        final Messenger messenger = sendMessengerRef.get();
        if (messenger != null) {
            final Message msg = Messages.makeJobMessage("Job" + jobId++);
            if (jobId == Integer.MAX_VALUE) {
                jobId = 1;
            }
            msg.replyTo = receiveMessenger;
            try {
                messenger.send(msg);
            } catch (RemoteException e) {
                Log.e(TAG, "Cannot send message to remote service");
            }
        }
    }

    private void connectToService() {
        if (connected.compareAndSet(false, true)) {
            Intent intent = new Intent(SERVICE_ACTION);
            intent.setComponent(new ComponentName(SERVICE_PKG, SERVICE_CLS));

            bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        }
    }

    private void disconnectFromService() {
        sendMessengerRef.set(null);
        if (connected.compareAndSet(true, false)) {
            unbindService(serviceConnection);
        }
    }

    private LocalDateTime stringToTime(String s) {
        final ZonedDateTime time = LocalDateTime.parse(s).atZone(ZoneOffset.UTC);
        return time.withZoneSameInstant(ZoneId.of("CTT")).toLocalDateTime();
    }

    private void onJobStarted(Message msg) {
        final Bundle bundle = (Bundle) msg.obj;
        bundle.setClassLoader(getClassLoader());

        final JobInfo job = bundle.getParcelable("job-info");
        if (job != null) {
            tvJobResponse.append(String.format("\nJob %s is started at %s",
                    job.getName(), stringToTime(job.getTimestamp()).format(DATE_TIME_FORMATTER)));
            svJobResponse.fullScroll(View.FOCUS_DOWN);
        }
    }

    private void onJobFinished(Message msg) {
        final Bundle bundle = (Bundle) msg.obj;
        bundle.setClassLoader(getClassLoader());

        final JobInfo job = bundle.getParcelable("job-info");
        if (job != null) {
            tvJobResponse.append(String.format("\nJob %s is finish at %s\n",
                    job.getName(), stringToTime(job.getTimestamp()).format(DATE_TIME_FORMATTER)));
            svJobResponse.fullScroll(View.FOCUS_DOWN);
        }
    }
}
