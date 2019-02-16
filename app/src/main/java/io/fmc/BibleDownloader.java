package io.fmc;

import com.google.gson.annotations.SerializedName;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class BibleDownloader {

    static String COMPLETE_SERVER_URL = "https://api.scripture.api.bible/v1/bibles";
    static String url = "https://api.scripture.api.bible/v1/bibles";
    static String urlBooks = "https://api.scripture.api.bible/v1/bibles/de4e12af7f28f599-01/books";
    static String urlRuth = "https://api.scripture.api.bible/v1/bibles/de4e12af7f28f599-01/" +
            "books/RUT/chapters";
    static String urlT = "http://fellowshipmission.church/";
    private static final String ENDPOINT = "https://api.github.com/repos/square/okhttp/contributors";
    private static final Moshi MOSHI = new Moshi.Builder().build();
    private static final JsonAdapter<List<BibleBooks>> CONTRIBUTORS_JSON_ADAPTER = MOSHI.adapter(
            Types.newParameterizedType(List.class, BibleBooks.class));

        private static final JsonAdapter<BooksResponse> BIBLE_RESPONSE_JSON_ADAPTER = MOSHI.adapter(BooksResponse.class);

//    static class Contributor {
//        String login;
//        int contributions;
//    }

    public static class BooksResponse {
        public List<BibleBooks> data;

        public BooksResponse(List<BibleBooks> bibleBooks) {
            this.data = bibleBooks;
        }

        @Override
        public String toString() {
            return "BooksResponse{" +
                    "bibleBooks=" + data +
                    '}';
        }
    }

    static class BibleBooks {
        String id;
        //String name;
//        int contributions;


        @Override
        public String toString() {
            return "BibleBooks{" +
                    "id='" + id + '\'' +
                    '}';
        }

    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        OkHttpClient client = new OkHttpClient();


        String loginString = BuildConfig.API_KEY;
        String password = "";
//        final String encodedApiKey =
//                Base64.encodeToString(String.format("%s:%s", loginString, password).getBytes("UTF-8"),
//                        Base64.NO_WRAP);


        String credential = Credentials.basic("",BuildConfig.API_KEY);
//        String credential = Credentials.basic("",BuildConfig.API_KEY);
//        String credential = Credentials.basic("jesse", "password1");

        // Create request for remote resource.
        Request request = new Request.Builder()
//                .header("Authorization",BuildConfig.API_KEY)
                .header("api-key", BuildConfig.API_KEY)
                .url(urlRuth)
                .build();

        // Execute the request and retrieve the response.
        try (Response response = client.newCall(request).execute()) {

            System.out.println("Authenticating for response: " + response);
            System.out.println("Challenges: " + response.challenges());
//            System.out.println(response.body().string());
            System.out.println(response.body().contentType());
            System.out.println(response.body().source());

            // Deserialize HTTP response to concrete type.
            ResponseBody body = response.body();
//            List<Contributor> contributors = CONTRIBUTORS_JSON_ADAPTER.fromJson(body.source());
            //List<BibleBooks> bBooks = CONTRIBUTORS_JSON_ADAPTER.fromJson(body.source());

            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<BooksResponse> jsonAdapter = moshi.adapter(BooksResponse.class);

            BooksResponse booksResponse = jsonAdapter.fromJson(body.source());

            System.out.println(booksResponse);
            // Sort list by the most contributions.
//            Collections.sort(contributors, (c1, c2) -> c2.contributions - c1.contributions);


            // Output list of contributors.
            for (BibleBooks bibleBooks : booksResponse.data) {
//                System.out.println(contributor.login + ": " + contributor.contributions);
                System.out.println(bibleBooks.id);
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
