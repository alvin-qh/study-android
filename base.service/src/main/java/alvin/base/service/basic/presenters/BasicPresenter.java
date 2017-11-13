package alvin.base.service.basic.presenters;

import android.content.Intent;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.service.basic.BasicContracts;
import alvin.base.service.basic.services.BasicService;
import alvin.lib.mvp.PresenterAdapter;

public class BasicPresenter extends PresenterAdapter<BasicContracts.View> implements BasicContracts.Presenter {

    @Inject
    BasicPresenter(@NonNull BasicContracts.View view) {
        super(view);
    }

    @Override
    public void started() {
        super.started();

        withView(view -> {
            Intent intent = new Intent(view.context(), BasicService.class);
            view.context().startService(intent);
        });
    }
}
