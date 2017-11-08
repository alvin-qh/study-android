package alvin.base.mvp.basic.di;

import alvin.base.mvp.basic.views.BasicMainActivity;
import dagger.Component;

@Component(modules = {BasicMainActivityModule.class})
public interface BasicMainActivityComponent {

    void inject(BasicMainActivity activity);
}
