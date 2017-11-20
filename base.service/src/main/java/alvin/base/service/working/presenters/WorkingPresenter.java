package alvin.base.service.working.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

import javax.inject.Inject;

import alvin.base.service.working.WorkingContracts;
import alvin.base.service.working.services.WorkingService;
import alvin.lib.mvp.PresenterAdapter;

public class WorkingPresenter extends PresenterAdapter<WorkingContracts.View>
        implements WorkingContracts.Presenter {

    private WorkingService.OnServiceCallbackListener listener = time ->
            withView(view -> view.showTime(time));

    @Inject
    WorkingPresenter(@NonNull WorkingContracts.View view) {
        super(view);
    }

    @Override
    public void startService(Context context) {
        Intent intent = new Intent(context, WorkingService.class);
        intent.putExtra(WorkingService.EXTRA_ARG_ZONE, "Asia/Shanghai");
        context.startService(intent);

        withView(WorkingContracts.View::serviceStarted);
    }

    @Override
    public void stopService(Context context) {
        Intent intent = new Intent(context, WorkingService.class);
        context.stopService(intent);
        withView(WorkingContracts.View::serviceStoped);
    }

    @Override
    public void connectService() {
        withService(service -> {
            service.addOnServiceCallbackListener(listener);
            withView(WorkingContracts.View::onServiceConnected);
        });
    }

    @Override
    public void disconnectService() {
        withService(service -> {
            service.removeOnServiceCallbackListener(listener);
            withView(WorkingContracts.View::onServiceDisconnected);
        });
    }

    private void withService(@NonNull Consumer<WorkingService> fn) {
        final WeakReference<WorkingService> serviceRef = WorkingService.getServiceRef();
        final WorkingService service = serviceRef.get();
        if (service != null) {
            fn.accept(service);
        }
    }
}
