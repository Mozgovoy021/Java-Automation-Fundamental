package api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.time.Duration;

public final class HttpClientProvider {
    private static final OkHttpClient CLIENT = build();

    private HttpClientProvider() {
    }

    private static OkHttpClient build() {
        HttpLoggingInterceptor log = new HttpLoggingInterceptor(System.out::println);
        log.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(10))
                .callTimeout(Duration.ofSeconds(15))
                .addInterceptor(log)
                .build();
    }

    public static OkHttpClient get() {
        return CLIENT;
    }
}