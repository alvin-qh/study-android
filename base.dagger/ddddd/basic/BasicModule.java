package alvin.adv.dagger.basic;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;

import alvin.adv.dagger.basic.presenters.BasicPresenter;
import alvin.adv.dagger.common.contracts.CommonContracts;
import alvin.adv.dagger.common.domain.db.MessageDatabase;
import alvin.lib.common.dbflow.repositories.TransactionManager;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;


@Module(includes = {BasicModule.BindModule.class})
public class BasicModule {
    private final CommonContracts.View view;

    public BasicModule(CommonContracts.View view) {
        this.view = view;
    }

    @Provides
    public CommonContracts.View view() {
        return view;
    }

    @Provides
    public DatabaseDefinition databaseDefinition() {
        return FlowManager.getDatabase(MessageDatabase.class);
    }

    @Provides
    public TransactionManager transactionManager(DatabaseDefinition db) {
        return new TransactionManager(db);
    }

    @Module
    public interface BindModule {
        @Binds
        CommonContracts.Presenter presenter(BasicPresenter presenter);
    }
}
