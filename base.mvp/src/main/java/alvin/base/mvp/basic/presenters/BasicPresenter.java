package alvin.base.mvp.basic.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.mvp.basic.domain.repositories.MessageRepository;
import alvin.base.mvp.common.Contract;
import alvin.base.mvp.common.presenters.BaseActivityPresenter;

public class BasicPresenter extends BaseActivityPresenter {

    @Inject
    public BasicPresenter(@NonNull Contract.View view,
                          @NonNull MessageRepository messageRepository) {
        super(view, messageRepository);
    }
}
