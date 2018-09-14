package alvin.base.net.socket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import alvin.base.net.socket.common.config.NetworkConfig;
import alvin.base.net.socket.network.NativeProtocol;
import alvin.base.net.socket.network.NativeSocket;
import alvin.base.net.socket.network.NettyProtocol;
import alvin.base.net.socket.network.NettySocket;
import alvin.base.net.socket.presenters.SocketNativePresenter;
import alvin.base.net.socket.presenters.SocketNettyPresenter;
import alvin.base.net.socket.views.SocketNativeActivity;
import alvin.base.net.socket.views.SocketNettyActivity;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxSchedulers;
import alvin.lib.common.rx.RxType;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static alvin.base.net.socket.SocketContracts.NativePresenter;
import static alvin.base.net.socket.SocketContracts.NativeView;

@Module
public interface SocketModule {

    @ContributesAndroidInjector(modules = {
            CommonModule.class,
            NativeActivityModule.class})
    SocketNativeActivity socketNativeActivity();

    @Module
    interface CommonModule {
        int RETRY_TIMES = 3;

        @Provides
        @RxType.New
        static RxDecorator.Builder singleRxDecoratorBuilder() {
            return RxDecorator.newBuilder()
                    .subscribeOn(Schedulers::newThread)
                    .observeOn(AndroidSchedulers::mainThread)
                    .retryTimes(RETRY_TIMES);
        }

        @Provides
        @RxType.SinglePool
        static RxDecorator.Builder ioRxReceiverDecoratorBuilder() {
            return RxDecorator.newBuilder()
                    .subscribeOn(RxSchedulers::singlePool)
                    .observeOn(AndroidSchedulers::mainThread)
                    .retryTimes(RETRY_TIMES);
        }

        @Provides
        static NetworkConfig networkConfig() {
            return new NetworkConfig();
        }

        @Provides
        static ObjectMapper mapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper;
        }
    }

    @Module
    interface NativeActivityModule {

        @Binds
        NativeView view(SocketNativeActivity activity);

        @Binds
        NativePresenter presenter(SocketNativePresenter presenter);

        @Provides
        static NativeSocket nativeSocket(ObjectMapper mapper, NetworkConfig config) {
            return new NativeSocket(new NativeProtocol(mapper), config);
        }
    }

    @ContributesAndroidInjector(modules = {CommonModule.class, NettyActivityModule.class})
    SocketNettyActivity activity();

    @Module
    interface NettyActivityModule {

        @Binds
        NativeView view(final SocketNettyActivity activity);

        @Binds
        NativePresenter presenter(final SocketNettyPresenter presenter);

        @Provides
        static NettySocket nettySocket(ObjectMapper mapper, NetworkConfig config) {
            return new NettySocket(config, new NettyProtocol(mapper));
        }
    }
}
