package io.fmc.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class DataModule {

    // https://api.scripture.api.bible/v1/bibles/de4e12af7f28f599-01/books/RUT/chapters
    private static String URL_RUTH = "https://api.scripture.api.bible";

    @Provides
    public OkHttpClient provideApiClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addNetworkInterceptor(new BibleApiInterceptor())
                .build();
    }

    @Provides
    public Retrofit provideRetrofit(OkHttpClient apiClient) {
        return new Retrofit.Builder()
                .client(apiClient)
                .baseUrl(URL_RUTH)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    public BibleApi provideBibleApi(Retrofit retrofit) {
        return retrofit.create(BibleApi.class);
    }

}
