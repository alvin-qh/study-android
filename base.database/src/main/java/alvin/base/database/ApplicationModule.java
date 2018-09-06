package alvin.base.database;

import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowManager;

import javax.inject.Singleton;

import alvin.base.database.dbflow.domain.FlowDatabase;
import alvin.base.database.sqlite.domain.SQLite;
import alvin.lib.common.dbflow.repositories.TransactionManager;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxType;
import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class ApplicationModule {
    public static final String DB_NAME = "main_db";

    @Singleton
    @Provides
    @RxType.IO
    RxDecorator.Builder rxDecoratorBuilder() {
        return RxDecorator.newBuilder()
                .observeOn(AndroidSchedulers::mainThread)
                .subscribeOn(Schedulers::io);
    }

    @Singleton
    @Provides
    Context context(Application application) {
        return application;
    }

    @Singleton
    @Provides
    SQLite.Builder sqliteBuilder(Context context) {
        return SQLite.newBuilder()
                .withDBName(DB_NAME + ".db")
                .withContext(context);
    }

    @Provides
    TransactionManager transactionManager() {
        return new TransactionManager(FlowManager.getDatabase(FlowDatabase.class));
    }
}
