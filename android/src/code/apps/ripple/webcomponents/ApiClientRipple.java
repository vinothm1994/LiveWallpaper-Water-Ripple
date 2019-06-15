package code.apps.ripple.webcomponents;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientRipple {
    public static final String BASE_URL = "http://68.66.193.71/ripple/";
    private static Retrofit a;

    public static Retrofit getClient() {
        OkHttpClient okHttpClient = getOkHttpClient();
        if (a == null) {
            a = new Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
        }
        return a;
    }

    public static OkHttpClient getOkHttpClient() {
        return new OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).build();
    }
}
