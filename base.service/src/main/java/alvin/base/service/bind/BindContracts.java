package alvin.base.service.bind;

import android.support.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface BindContracts {

    interface View extends IView {

        void showTime(LocalDateTime time);
    }

    interface Presenter extends IPresenter {
        void serviceBind(ServiceBinder binder);

        void serviceUnbind();
    }

    interface ServiceBinder {

        void addTimeCallback(@NonNull Consumer<LocalDateTime> consumer);

        void remoteTimeCallback(@NonNull Consumer<LocalDateTime> consumer);
    }
}
