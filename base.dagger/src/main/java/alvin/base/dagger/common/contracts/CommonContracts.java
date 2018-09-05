package alvin.adv.dagger.common.contracts;

import android.support.annotation.NonNull;

import java.util.List;

import alvin.adv.dagger.common.domain.models.Message;
import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface CommonContracts {

    interface View extends IView {

        void showMessages(@NonNull List<Message> messageList);

        void showException(@NonNull Throwable error);
    }

    interface Presenter extends IPresenter {

        void loadData();

        void createMessage(@NonNull String message);

        void deleteMessage(int messageId);
    }
}
