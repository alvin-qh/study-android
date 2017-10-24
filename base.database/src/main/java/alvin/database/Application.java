package alvin.database;

import com.raizlabs.android.dbflow.config.FlowManager;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize DB Flow
        FlowManager.init(this);
    }
}
