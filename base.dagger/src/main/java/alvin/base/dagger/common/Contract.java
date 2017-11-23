package alvin.base.dagger.common;

import android.support.annotation.NonNull;

import java.util.List;

import alvin.base.dagger.common.domain.models.Message;
import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface Contract {

    interface View extends IView {

        void showMessages(@NonNull List<Message> messageList);

        void showException(@NonNull Throwable error);
    }

    interface Presenter extends IPresenter {

        void createMessage(@NonNull String message);

        void deleteMessage(int messageId);
    }
}
