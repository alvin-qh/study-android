package alvin.base.dagger.android.subcomponent.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

import alvin.base.dagger.android.qualifiers.Names;
import alvin.base.dagger.basic.domain.repositories.MessageRepository;
import alvin.base.dagger.common.Contract;
import alvin.base.dagger.common.presenters.BaseActivityPresenter;

public class SubcomponentPresenter extends BaseActivityPresenter {

    @Inject
    public SubcomponentPresenter(@NonNull @Named(Names.SUBCOMPONENT) Contract.View view,
                                 @NonNull MessageRepository messageRepository) {
        super(view, messageRepository);
    }
}
