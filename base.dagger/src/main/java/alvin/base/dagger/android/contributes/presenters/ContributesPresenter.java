package alvin.base.dagger.android.contributes.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

import alvin.base.dagger.android.qualifiers.Names;
import alvin.base.dagger.common.Contract;
import alvin.base.dagger.common.domain.repositories.MessageRepository;
import alvin.base.dagger.common.presenters.BaseActivityPresenter;
import alvin.lib.common.dbflow.repositories.TransactionManager;

public class ContributesPresenter extends BaseActivityPresenter {

    @Inject
    public ContributesPresenter(@NonNull @Named(Names.CONTRIBUTES) Contract.View view,
                                @NonNull MessageRepository messageRepository,
                                @NonNull TransactionManager transactionManager) {
        super(view, messageRepository, transactionManager);
    }
}
