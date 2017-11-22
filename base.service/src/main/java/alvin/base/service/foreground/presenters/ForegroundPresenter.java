package alvin.base.service.foreground.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.service.common.broadcasts.ServiceBroadcasts;
import alvin.base.service.foreground.ForegroundContracts;
import alvin.base.service.foreground.services.ForegroundService;
import alvin.lib.common.util.IntentFilters;
import alvin.lib.common.util.Version;
import alvin.lib.mvp.PresenterAdapter;

public class ForegroundPresenter extends PresenterAdapter<ForegroundContracts.View>
        implements ForegroundContracts.Presenter {

    private BroadcastReceiver receiver;

    private Version version;

    @Inject
    public ForegroundPresenter(@NonNull ForegroundContracts.View view,
                               @NonNull Version version) {
        super(view);
        this.version = version;
    }

    @Override
    public void startReceiver(Context context) {
        if (receiver == null) {
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    final String action = intent.getAction();
                    if (action != null) {
                        switch (action) {
                        case ServiceBroadcasts.ACTION_SERVICE_CREATED:
                            withView(ForegroundContracts.View::serviceCreated);
                            break;
                        case ServiceBroadcasts.ACTION_SERVICE_DESTROYED:
                            withView(ForegroundContracts.View::serviceDestroyed);
                            break;
                        default:
                            break;
                        }
                    }
                }
            };

            context.registerReceiver(receiver,
                    IntentFilters.newBuilder()
                            .addAction(ServiceBroadcasts.ACTION_SERVICE_CREATED)
                            .addAction(ServiceBroadcasts.ACTION_SERVICE_DESTROYED)
                            .build());
        }
    }

    @Override
    public void stopReceiver(Context context) {
        if (receiver != null) {
            context.unregisterReceiver(receiver);
            receiver = null;
        }
    }

    @Override
    public void startService(Context context) {
        Intent intent = new Intent(context, ForegroundService.class);

        if (version.isEqualOrGreatThan()) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    @Override
    public void stopService(Context context) {
        Intent intent = new Intent(context, ForegroundService.class);
        context.stopService(intent);
    }
}
