package alvin.base.net.socket.jnative;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

import alvin.base.net.socket.SocketContracts;
import alvin.base.net.socket.jnative.presenters.NativePresenter;
import alvin.base.net.socket.jnative.views.NativeActivity;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxSchedulers;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public interface NativeModule {

    @ContributesAndroidInjector(modules = {
            ViewModule.class,
            ProvidesModule.class
    })
    NativeActivity activity();

    @Module
    interface ViewModule {
        @Binds
        SocketContracts.View view(final NativeActivity activity);

        @Binds
        SocketContracts.Presenter presenter(final NativePresenter presenter);
    }

    @Module
    class ProvidesModule {

        @Send
        @Provides
        public RxDecorator rxSendDecorator() {
            return RxDecorator.newBuilder()
                    .subscribeOn(RxSchedulers::newSingleThread)
                    .observeOn(AndroidSchedulers::mainThread)
                    .build();
        }

        @Receiver
        @Provides
        public RxDecorator rxReceiverDecorator() {
            return RxDecorator.newBuilder()
                    .subscribeOn(Schedulers::io)
                    .observeOn(AndroidSchedulers::mainThread)
                    .build();
        }
    }


    @Qualifier
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface Receiver { }

    @Qualifier
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface Send { }
}
