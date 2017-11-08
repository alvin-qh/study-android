package alvin.base.mvp.common;

import android.support.annotation.NonNull;

import java.util.List;

import alvin.base.mvp.domain.models.Message;
import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface Constract {

    interface View extends IView {

        void showMessages(@NonNull List<Message> messageList);
    }

    interface Presenter extends IPresenter {

        void createMessage(@NonNull final String message);

        void deleteMessage(int messageId);
    }
}
