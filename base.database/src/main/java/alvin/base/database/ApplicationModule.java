package alvin.base.database;

import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowManager;

import javax.inject.Singleton;

import alvin.base.database.dbflow.DBFlowModule;
import alvin.base.database.dbflow.domain.FlowDatabase;
import alvin.base.database.dbflow.views.DBFlowActivity;
import alvin.base.database.sqlite.SQLiteModule;
import alvin.base.database.sqlite.domain.SQLite;
import alvin.base.database.sqlite.views.SQLiteActivity;
import alvin.lib.common.dbflow.repositories.TransactionManager;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxType;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public interface ApplicationModule {
    String DB_NAME = "main_db";

    @Singleton
    @Provides
    @RxType.IO
    static RxDecorator.Builder rxDecoratorBuilder() {
        return RxDecorator.newBuilder()
                .observeOn(AndroidSchedulers::mainThread)
                .subscribeOn(Schedulers::io);
    }

    @Singleton
    @Provides
    static Context context(Application application) {
        return application;
    }

    @Singleton
    @Provides
    static SQLite.Builder sqliteBuilder(Context context) {
        return SQLite.newBuilder()
                .withDBName(DB_NAME + ".db")
                .withContext(context);
    }

    @Provides
    static TransactionManager transactionManager() {
        return new TransactionManager(FlowManager.getDatabase(FlowDatabase.class));
    }

    @ContributesAndroidInjector(modules = DBFlowModule.class)
    DBFlowActivity dbFlowActivity();

    @ContributesAndroidInjector(modules = SQLiteModule.class)
    SQLiteActivity sqLiteActivity();
}
