package io.fmc.network;

import java.io.IOException;

import io.fmc.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.Response;

class BibleApiInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request().newBuilder()
                .addHeader("api-key", BuildConfig.API_KEY)
                .build());
    }
}
