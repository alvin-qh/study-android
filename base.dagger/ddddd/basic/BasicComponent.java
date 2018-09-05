package alvin.adv.dagger.basic;

import alvin.adv.dagger.basic.views.BasicActivity;
import dagger.Component;

@Component(modules = {BasicModule.class})
public interface BasicComponent {

    void inject(BasicActivity activity);
}
