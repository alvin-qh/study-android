package alvin.base.service.foreground.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.service.common.broadcasts.ServiceBroadcasts;
import alvin.base.service.foreground.ForegroundContracts;
import alvin.lib.mvp.contracts.adapters.ActivityAdapter;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;

public class ForegroundPresenter
        extends PresenterAdapter<ForegroundContracts.IView>
        implements ForegroundContracts.Presenter {

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action != null) {
                switch (action) {
                case ServiceBroadcasts.ACTION_SERVICE_CREATED:
                    with(ForegroundContracts.IView::serviceCreated);
                    break;
                case ServiceBroadcasts.ACTION_SERVICE_DESTROYED:
                    with(ForegroundContracts.IView::serviceDestroyed);
                    break;
                default:
                    break;
                }
            }
        }
    };

    @Inject
    public ForegroundPresenter(@NonNull ForegroundContracts.IView view) {
        super(view);
    }

    @Override
    public BroadcastReceiver getBroadcastReceiver() {
        return receiver;
    }
}
