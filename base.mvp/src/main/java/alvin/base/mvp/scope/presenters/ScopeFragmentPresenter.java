package alvin.base.mvp.scope.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.mvp.scope.ScopeContract;
import alvin.base.mvp.scope.domain.service.SingletonService;
import alvin.lib.mvp.PresenterAdapter;

public class ScopeFragmentPresenter extends PresenterAdapter<ScopeContract.View> implements ScopeContract.Presenter {

    private final SingletonService singletonService;

    @Inject
    ScopeFragmentPresenter(@NonNull ScopeContract.View view, SingletonService singletonService) {
        super(view);
        this.singletonService = singletonService;
    }

    @Override
    public void started() {
        super.started();

        withView(view -> view.showSingletonService(cutName(singletonService.toString())));
    }

    private String cutName(@NonNull String name) {
        final int index = name.lastIndexOf('.');
        if (index < 0) {
            return name;
        }
        return name.substring(index + 1);
    }
}
