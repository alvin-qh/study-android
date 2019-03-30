package alvin.base.dagger.basic;


import java.util.List;

import alvin.base.dagger.basic.domain.models.Message;
import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;
import androidx.annotation.NonNull;

public interface BasicContracts {

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
