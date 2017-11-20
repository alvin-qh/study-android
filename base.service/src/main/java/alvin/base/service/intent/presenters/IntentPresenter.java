package alvin.base.service.intent.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import javax.inject.Inject;

import alvin.base.service.common.broadcasts.ServiceBroadcasts;
import alvin.base.service.intent.IntentContracts;
import alvin.base.service.intent.services.IntentService;
import alvin.lib.mvp.PresenterAdapter;

public class IntentPresenter extends PresenterAdapter<IntentContracts.View>
        implements IntentContracts.Presenter {

    private BroadcastReceiver receiver;

    private int jobId = 1;

    private IntentService.OnJobStatusChangeListener listener;

    @Inject
    public IntentPresenter(@NonNull IntentContracts.View view) {
        super(view);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (listener == null) {
            listener = new IntentService.OnJobStatusChangeListener() {
                @Override
                public void onStart(String jobName) {
                    withView(view -> view.onJobStart(jobName));
                }

                @Override
                public void onFinish(String jobName, long timeSpend) {
                    withView(view -> view.onJobFinish(jobName, timeSpend));
                }
            };

            IntentService.addOnJobStatusChangeListener(listener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (listener != null) {
            IntentService.removeOnJobStatusChangeListener(listener);
        }
    }

    @Override
    public void registerReceiver(Context context) {
        if (receiver == null) {
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    final String action = intent.getAction();
                    if (!Strings.isNullOrEmpty(action)) {
                        switch (action) {
                        case ServiceBroadcasts.ACTION_SERVICE_CREATED:
                            withView(IntentContracts.View::serviceCreated);
                            break;
                        case ServiceBroadcasts.ACTION_SERVICE_DESTROYED:
                            withView(IntentContracts.View::serviceDestroyed);
                            break;
                        default:
                            break;
                        }
                    }
                }
            };

            IntentFilter filter = new IntentFilter();
            filter.addAction(ServiceBroadcasts.ACTION_SERVICE_CREATED);
            filter.addAction(ServiceBroadcasts.ACTION_SERVICE_DESTROYED);

            context.registerReceiver(receiver, filter);
        }
    }

    @Override
    public void unregisterReceiver(Context context) {
        if (receiver != null) {
            context.unregisterReceiver(receiver);
        }
    }

    @Override
    public void workOnce(Context context) {
        Intent intent = new Intent(context, IntentService.class);
        intent.putExtra(IntentService.EXTRA_ARG_NAME, "Job_" + jobId++);

        context.startService(intent);
    }
}
