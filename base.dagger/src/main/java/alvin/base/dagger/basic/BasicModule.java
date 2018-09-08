package alvin.base.dagger.basic;

import com.raizlabs.android.dbflow.config.FlowManager;

import alvin.base.dagger.basic.domain.db.MessageDatabase;
import alvin.base.dagger.basic.presenters.MessagePresenter;
import alvin.base.dagger.basic.views.BasicActivity;
import alvin.lib.common.dbflow.repositories.TransactionManager;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxSchedulers;
import alvin.lib.common.rx.RxType;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;


@Module
public interface BasicModule {

    @ContributesAndroidInjector(modules = {BindModule.class, ProvidersModule.class})
    BasicActivity basicActivity();

    @Module
    interface BindModule {

        @Binds
        BasicContracts.View view(BasicActivity activity);

        @Binds
        BasicContracts.Presenter presenter(MessagePresenter presenter);
    }

    @Module
    class ProvidersModule {

        @Provides
        public TransactionManager tm() {
            return new TransactionManager(FlowManager.getDatabase(MessageDatabase.class));
        }

        @Provides
        @RxType.IO
        public RxDecorator.Builder rxDecoratorBuilder() {
            return RxDecorator.newBuilder()
                    .subscribeOn(RxSchedulers::database)
                    .observeOn(AndroidSchedulers::mainThread);
        }
    }
}
