package alvin.base.dagger.android.contributes.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

import alvin.base.dagger.android.qualifiers.Names;
import alvin.base.dagger.common.contracts.CommonContracts;
import alvin.base.dagger.common.domain.repositories.MessageRepository;
import alvin.base.dagger.common.presenters.BasePresenter;
import alvin.lib.common.dbflow.repositories.TransactionManager;

public class ContributesPresenter extends BasePresenter {

    @Inject
    public ContributesPresenter(@NonNull @Named(Names.CONTRIBUTES) CommonContracts.View view,
                                @NonNull MessageRepository messageRepository,
                                @NonNull TransactionManager transactionManager) {
        super(view, messageRepository, transactionManager);
    }
}
