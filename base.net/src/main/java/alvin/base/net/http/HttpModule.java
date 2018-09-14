package alvin.base.net.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.concurrent.TimeUnit;

import alvin.base.net.http.domain.services.WeatherService;
import alvin.base.net.http.presenters.HttpWithRxPresenter;
import alvin.base.net.http.presenters.HttpWithTaskPresenter;
import alvin.base.net.http.views.HttpWithRxActivity;
import alvin.base.net.http.views.HttpWithTaskActivity;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxType;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

@Module
public interface HttpModule {

    @ContributesAndroidInjector(modules = {
            CommonModule.class,
            TaskActivityModule.class})
    HttpWithTaskActivity httpWithTaskActivity();

    @ContributesAndroidInjector(modules = {
            CommonModule.class,
            RxAcitivityModule.class})
    HttpWithRxActivity httpWithRxActivity();

    @Module
    interface CommonModule {
        int TIMEOUT_SECOND = 30;

        @Provides
        static ObjectMapper objectMapper() {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper;
        }

        @Provides
        static OkHttpClient.Builder httpClientBuilder() {
            return new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS);
        }

        @Provides
        static WeatherService weatherService(ObjectMapper mapper, OkHttpClient.Builder httpBuilder) {
            return new WeatherService(mapper, httpBuilder);
        }
    }

    @Module
    interface TaskActivityModule {
        @Binds
        HttpContracts.View view(HttpWithTaskActivity activity);

        @Binds
        HttpContracts.Presenter presenter(HttpWithTaskPresenter presenter);
    }

    @Module
    interface RxAcitivityModule {
        int RETRY_TIMES = 3;

        @Binds
        HttpContracts.View view(HttpWithRxActivity activity);

        @Binds
        HttpContracts.Presenter presenter(HttpWithRxPresenter presenter);

        @Provides
        @RxType.IO
        static RxDecorator.Builder rxDecorator() {
            return RxDecorator.newBuilder()
                    .subscribeOn(Schedulers::io)
                    .observeOn(AndroidSchedulers::mainThread)
                    .retryTimes(RETRY_TIMES);
        }
    }
}
