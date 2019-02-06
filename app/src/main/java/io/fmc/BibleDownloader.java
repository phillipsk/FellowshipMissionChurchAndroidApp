package io.fmc;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class BibleDownloader {

    static String COMPLETE_SERVER_URL = "https://api.scripture.api.bible/v1/bibles";
    static String url = "https://api.scripture.api.bible/v1/bibles";
    private static final String ENDPOINT = "https://api.github.com/repos/square/okhttp/contributors";
    private static final Moshi MOSHI = new Moshi.Builder().build();
    private static final JsonAdapter<List<Contributor>> CONTRIBUTORS_JSON_ADAPTER = MOSHI.adapter(
            Types.newParameterizedType(List.class, Contributor.class));

    static class Contributor {
        String login;
        int contributions;
    }

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        // Create request for remote resource.
        Request request = new Request.Builder()
                .url(ENDPOINT)
                .build();

        // Execute the request and retrieve the response.
        try (Response response = client.newCall(request).execute()) {
            // Deserialize HTTP response to concrete type.
            ResponseBody body = response.body();
            List<Contributor> contributors = CONTRIBUTORS_JSON_ADAPTER.fromJson(body.source());

            // Sort list by the most contributions.
            Collections.sort(contributors, (c1, c2) -> c2.contributions - c1.contributions);

            // Output list of contributors.
            for (Contributor contributor : contributors) {
                System.out.println(contributor.login + ": " + contributor.contributions);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




/*    public static BibleApi createClientApi(){

        OkHttpClient client = new OkHttpClient();

//        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        }
    }*/

/*//    https://www.myawesomesite.com/turtles/types?type=1&sort=relevance#section-name

        private final static  String = "https://api.scripture.api.bible/v1/bibles/de4e12af7f28f599-01/books/gen?include-chapters=true"
//    Intent intent = getIntent();
//    String value = intent.getExtras().getString("value");
    Uri.Builder builder = new Uri.Builder();
    builder.scheme("http").authority("www.lapi.transitchicago.com")
    .appendPath("api")
    .appendPath("1.0")
    .appendPath("ttarrivals.aspx")
    .appendQueryParameter("key", "[redacted]")
    .appendQueryParameter("mapid", value);*/
}
