package alvin.base.service.bind.presenters;

import android.support.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import javax.inject.Inject;

import alvin.base.service.bind.BindContracts;
import alvin.lib.mvp.ViewPresenterAdapter;

public class BindPresenter extends ViewPresenterAdapter<BindContracts.View>
        implements BindContracts.Presenter {

    private BindContracts.ServiceBinder binder;
    private Consumer<LocalDateTime> timeConsumer = time -> withView(view -> view.showTime(time));

    @Inject
    public BindPresenter(@NonNull BindContracts.View view) {
        super(view);
    }

    @Override
    public void serviceBind(BindContracts.ServiceBinder binder) {
        this.binder = binder;
        this.binder.addTimeCallback(timeConsumer);
    }

    @Override
    public void serviceUnbind() {
        if (this.binder != null) {
            this.binder.remoteTimeCallback(timeConsumer);
            this.binder = null;
        }
    }
}
