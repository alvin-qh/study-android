package alvin.base.service.foreground.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.service.common.broadcasts.ServiceBroadcasts;
import alvin.base.service.foreground.ForegroundContracts;
import alvin.lib.mvp.adapters.ViewPresenterAdapter;

public class ForegroundPresenter extends ViewPresenterAdapter<ForegroundContracts.View>
        implements ForegroundContracts.Presenter {

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
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

    @Inject
    public ForegroundPresenter(@NonNull ForegroundContracts.View view) {
        super(view);
    }

    @Override
    public BroadcastReceiver getBroadcastReceiver() {
        return receiver;
    }
}
