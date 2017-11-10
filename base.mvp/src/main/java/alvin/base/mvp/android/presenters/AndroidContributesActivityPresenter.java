package alvin.base.mvp.android.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

import alvin.base.mvp.android.di.Names;
import alvin.base.mvp.basic.domain.repositories.MessageRepository;
import alvin.base.mvp.common.Contract;

public class AndroidContributesActivityPresenter extends AndroidActivityPresenter {

    @Inject
    public AndroidContributesActivityPresenter(@NonNull @Named(Names.CONTRIBUTES) Contract.View view,
                                               @NonNull MessageRepository messageRepository) {
        super(view, messageRepository);
    }
}
