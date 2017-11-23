package alvin.base.dagger.android.contributes.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

import alvin.base.dagger.android.qualifiers.Names;
import alvin.base.dagger.basic.domain.repositories.MessageRepository;
import alvin.base.dagger.common.Contract;
import alvin.base.dagger.common.presenters.BaseActivityPresenter;

public class ContributesPresenter extends BaseActivityPresenter {

    @Inject
    public ContributesPresenter(@NonNull @Named(Names.CONTRIBUTES) Contract.View view,
                                @NonNull MessageRepository messageRepository) {
        super(view, messageRepository);
    }
}
