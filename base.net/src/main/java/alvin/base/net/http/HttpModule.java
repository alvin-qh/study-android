package alvin.adv.net.http;

import alvin.adv.net.http.rx.RxModule;
import alvin.adv.net.http.task.TaskModule;
import alvin.adv.net.http.views.HttpActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {
        TaskModule.class,
        RxModule.class
})
public interface HttpModule {

    @ContributesAndroidInjector
    HttpActivity activity();
}
