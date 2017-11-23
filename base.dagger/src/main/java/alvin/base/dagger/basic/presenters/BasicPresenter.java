package alvin.base.dagger.basic.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.dagger.common.Contract;
import alvin.base.dagger.common.domain.repositories.MessageRepository;
import alvin.base.dagger.common.presenters.BaseActivityPresenter;
import alvin.lib.common.dbflow.repositories.TransactionManager;

public class BasicPresenter extends BaseActivityPresenter {

    @Inject
    public BasicPresenter(@NonNull Contract.View view,
                          @NonNull MessageRepository messageRepository,
                          @NonNull TransactionManager transactionManager) {
        super(view, messageRepository, transactionManager);
    }
}
