package alvin.base.dagger.basic;

import alvin.base.dagger.basic.views.BasicActivity;
import dagger.Component;

@Component(modules = {BasicModule.class})
public interface BasicComponent {

    void inject(BasicActivity activity);
}
