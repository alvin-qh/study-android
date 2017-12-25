package alvin.base.dagger;

import javax.inject.Singleton;

import alvin.base.dagger.android.contributes.ContributesModule;
import alvin.base.dagger.android.subcomponent.SubcomponentModule;
import alvin.base.dagger.multibindings.MultibindingsModule;
import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        SubcomponentModule.class,
        ContributesModule.class,
        MultibindingsModule.class
})
public interface ApplicationComponent {
    void inject(Application application);
}
