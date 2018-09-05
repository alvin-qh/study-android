package alvin.adv.mvp;

import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowManager;

import javax.inject.Singleton;

import alvin.adv.mvp.department.DepartmentModule;
import alvin.adv.mvp.domain.MainDatabase;
import alvin.adv.mvp.main.MainModule;
import alvin.adv.mvp.namecard.NameCardModule;
import alvin.lib.common.dbflow.repositories.TransactionManager;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxType;
import alvin.lib.common.utils.SystemServices;
import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module(includes = {
        MainModule.class,
        NameCardModule.class,
        DepartmentModule.class
})
public class ApplicationModule {

    @Singleton
    @Provides
    TransactionManager transactionManager() {
        return new TransactionManager(FlowManager.getDatabase(MainDatabase.class));
    }

    @Singleton
    @Provides
    Context context(final Application application) {
        return application;
    }

    @Singleton
    @Provides
    SystemServices systemServices(final Context context) {
        return new SystemServices(context);
    }

    @Singleton
    @Provides
    @RxType.IO
    RxDecorator.Builder rxDecoratorBuilder() {
        return RxDecorator.newBuilder()
                .subscribeOn(Schedulers::io)
                .observeOn(AndroidSchedulers::mainThread);
    }
}
