package alvin.base.net.http;

import alvin.base.net.http.rx.RxModule;
import alvin.base.net.http.task.TaskModule;
import alvin.base.net.http.views.HttpActivity;
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
