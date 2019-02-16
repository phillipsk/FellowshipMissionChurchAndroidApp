package io.fmc;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class Downloader {

    public static void main(String[] args) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", BuildConfig.API_KEY); // <-- this is the important line

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });


        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.addNetworkInterceptor(logging);

        OkHttpClient client = httpClient.build();
    }
}
//        client.newCall()

//        Request request = new Request.Builder()
//                .header("Authorization",BuildConfig.API_KEY)
//                .url(urlRuth)
//                .build();

/*        // Execute the request and retrieve the response.
        try (Response response = client.newCall().newCall(request).execute()) {

            System.out.println("Authenticating for response: " + response);
            System.out.println("Challenges: " + response.challenges());
//            System.out.println(response.body().string());
            System.out.println(response.body().contentType());
            System.out.println(response.body().source());

            // Deserialize HTTP response to concrete type.
            ResponseBody body = response.body();
//            List<Contributor> contributors = CONTRIBUTORS_JSON_ADAPTER.fromJson(body.source());
            List<BibleBooks> bBooks = CONTRIBUTORS_JSON_ADAPTER.fromJson(body.source());

            // Sort list by the most contributions.
//            Collections.sort(contributors, (c1, c2) -> c2.contributions - c1.contributions);


            // Output list of contributors.
            for (BibleBooks bibleBooks : bBooks) {
//                System.out.println(contributor.login + ": " + contributor.contributions);
                System.out.println(bibleBooks.name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


