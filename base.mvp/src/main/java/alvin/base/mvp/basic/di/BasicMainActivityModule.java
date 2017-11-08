package alvin.base.mvp.basic.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class BasicMainActivityModule {
    private final Context context;

    public BasicMainActivityModule(Context context) {
        this.context = context;
    }

    @Provides
    Context context() {
        return context;
    }
}
