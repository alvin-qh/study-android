package alvin.adv.dagger.basic.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.adv.dagger.common.contracts.CommonContracts;
import alvin.adv.dagger.common.domain.repositories.MessageRepository;
import alvin.adv.dagger.common.presenters.BasePresenter;
import alvin.lib.common.dbflow.repositories.TransactionManager;

public class BasicPresenter extends BasePresenter {

    @Inject
    public BasicPresenter(@NonNull CommonContracts.View view,
                          @NonNull MessageRepository messageRepository,
                          @NonNull TransactionManager transactionManager) {
        super(view, messageRepository, transactionManager);
    }
}
