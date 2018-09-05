package alvin.adv.net.socket.netty;

import alvin.adv.net.socket.SocketContracts;
import alvin.adv.net.socket.netty.presenters.NettyPresenter;
import alvin.adv.net.socket.netty.views.NettyActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface NettyModule {

    @ContributesAndroidInjector(modules = {
            ViewModule.class
    })
    NettyActivity activity();

    @Module
    interface ViewModule {
        @Binds
        SocketContracts.View view(final NettyActivity activity);

        @Binds
        SocketContracts.Presenter presenter(final NettyPresenter presenter);
    }
}
