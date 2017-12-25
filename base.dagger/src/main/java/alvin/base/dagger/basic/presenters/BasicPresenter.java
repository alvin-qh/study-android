package alvin.base.dagger.basic.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.dagger.common.contracts.CommonContracts;
import alvin.base.dagger.common.domain.repositories.MessageRepository;
import alvin.base.dagger.common.presenters.BasePresenter;
import alvin.lib.common.dbflow.repositories.TransactionManager;

public class BasicPresenter extends BasePresenter {

    @Inject
    public BasicPresenter(@NonNull CommonContracts.View view,
                          @NonNull MessageRepository messageRepository,
                          @NonNull TransactionManager transactionManager) {
        super(view, messageRepository, transactionManager);
    }
}
