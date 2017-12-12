package alvin.base.service.intent.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import javax.inject.Inject;

import alvin.base.service.common.broadcasts.ServiceBroadcasts;
import alvin.base.service.intent.IntentContracts;
import alvin.base.service.intent.services.IntentService;
import alvin.lib.mvp.adapters.ViewPresenterAdapter;

public class IntentPresenter extends ViewPresenterAdapter<IntentContracts.View>
        implements IntentContracts.Presenter {

    private int jobId = 1;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
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

    private final IntentService.OnJobStatusChangeListener listener =
            new IntentService.OnJobStatusChangeListener() {
                @Override
                public void onStart(String jobName) {
                    withView(view -> view.onJobStart(jobName));
                }

                @Override
                public void onFinish(String jobName, long timeSpend) {
                    withView(view -> view.onJobFinish(jobName, timeSpend));
                }
            };

    @Inject
    public IntentPresenter(@NonNull IntentContracts.View view) {
        super(view);
    }

    @Override
    public BroadcastReceiver getBroadcastReceiver() {
        return receiver;
    }

    @Override
    public String getJobName() {
        return "Job_" + jobId++;
    }

    @Override
    public IntentService.OnJobStatusChangeListener getListener() {
        return listener;
    }
}
