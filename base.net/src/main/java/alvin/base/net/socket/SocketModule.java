package alvin.adv.net.socket;

import alvin.adv.net.socket.jnative.NativeModule;
import alvin.adv.net.socket.netty.NettyModule;
import alvin.adv.net.socket.views.SocketActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {
        NativeModule.class,
        NettyModule.class
})
public interface SocketModule {

    @ContributesAndroidInjector
    SocketActivity activity();
}
