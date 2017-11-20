package alvin.base.service.bind;

import android.content.Context;

import java.time.LocalDateTime;

import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface BindContracts {

    interface View extends IView {

        void showTime(LocalDateTime time);

        void serviceBind();

        void serviceUnbind();
    }

    interface Presenter extends IPresenter {

        void bindService(Context context);

        void unbindService(Context context);
    }
}
