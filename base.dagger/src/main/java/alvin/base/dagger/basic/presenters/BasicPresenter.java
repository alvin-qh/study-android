package alvin.base.dagger.basic.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.dagger.basic.domain.repositories.MessageRepository;
import alvin.base.dagger.common.Contract;
import alvin.base.dagger.common.presenters.BaseActivityPresenter;

public class BasicPresenter extends BaseActivityPresenter {

    @Inject
    public BasicPresenter(@NonNull Contract.View view,
                          @NonNull MessageRepository messageRepository) {
        super(view, messageRepository);
    }
}
