package alvin.base.service.bind.presenters;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import javax.inject.Inject;

import alvin.base.service.bind.BindContracts;
import alvin.base.service.bind.services.BindService;
import alvin.lib.mvp.PresenterAdapter;

public class BindPresenter extends PresenterAdapter<BindContracts.View>
        implements BindContracts.Presenter {

    private ServiceConnection connection;
    private BindService.ServiceBinder binder;
    private Consumer<LocalDateTime> timeConsumer = time -> withView(view -> view.showTime(time));

    @Inject
    public BindPresenter(@NonNull BindContracts.View view) {
        super(view);
    }

    @Override
    public void bindService(Context context) {
        if (connection == null) {
            Intent intent = new Intent(context, BindService.class);
            intent.putExtra(BindService.EXTRA_ARG_ZONE, "Asia/Shanghai");

            connection = new ServiceConnection() {

                /**
                 * When service is connected, this method will be callback.
                 * The service name and IBinder instance will be given.
                 *
                 * @see BindService.ServiceBinder#addTimeCallback(Consumer)
                 */
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    if (iBinder instanceof BindService.ServiceBinder) {
                        binder = (BindService.ServiceBinder) iBinder;

                        // set callback method to binder,
                        binder.addTimeCallback(timeConsumer);
                    }
                }

                /**
                 * Only service is killed unexpected, this method will be callback.
                 */
                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    binder.remoteTimeCallback(timeConsumer);
                }
            };

            // Bind service
            context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
        withView(BindContracts.View::serviceBind);
    }

    @Override
    public void unbindService(Context context) {
        if (connection != null) {
            binder.remoteTimeCallback(timeConsumer);
            context.unbindService(connection);
        }
        withView(BindContracts.View::serviceUnbind);
    }
}
