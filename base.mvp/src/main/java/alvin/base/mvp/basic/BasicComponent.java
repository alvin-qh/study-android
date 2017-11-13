package alvin.base.mvp.basic;

import alvin.base.mvp.basic.views.BasicActivity;
import dagger.Component;

@Component(modules = {BasicModule.class})
public interface BasicComponent {

    void inject(BasicActivity activity);
}
