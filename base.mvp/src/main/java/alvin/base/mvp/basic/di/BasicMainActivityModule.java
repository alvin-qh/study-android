package alvin.base.mvp.basic.di;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;

import alvin.base.mvp.common.Constract;
import alvin.base.mvp.db.MessageDatabase;
import dagger.Module;
import dagger.Provides;

@Module
public class BasicMainActivityModule {
    private final Constract.View view;

    public BasicMainActivityModule(Constract.View view) {
        this.view = view;
    }

    @Provides
    Constract.View view() {
        return view;
    }

    @Provides
    DatabaseDefinition databaseDefinition() {
        return FlowManager.getDatabase(MessageDatabase.class);
    }
}
