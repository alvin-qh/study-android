package alvin.base.service.intent;

import android.content.Context;

import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface IntentContracts {

    interface View extends IView {

        void serviceCreated();

        void serviceDestroyed();

        void onJobStart(String jobName);

        void onJobFinish(String jobName, long timeSpend);
    }

    interface Presenter extends IPresenter {

        void registerReceiver(Context context);

        void workOnce(Context context);

        void unregisterReceiver(Context context);
    }
}
