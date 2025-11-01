package api.support;

import api.HttpClientProvider;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public final class ApiGetHelper {
    private static final OkHttpClient CLIENT = HttpClientProvider.get();

    private ApiGetHelper() {
    }

    public static Response get(String baseUrl, String path, Map<String, String> query, String bearerToken) throws IOException {

        HttpUrl base = HttpUrl.parse(baseUrl);
        if (base == null) throw new IllegalArgumentException("Invalid baseUrl: " + baseUrl);

        HttpUrl.Builder url = base.newBuilder().addEncodedPathSegments(stripLeadingSlash(path));

        if (query != null) {
            query.forEach(url::addQueryParameter);
        }

        Request.Builder rb = new Request.Builder().url(url.build()).get().addHeader("Accept", "application/json");

        if (bearerToken != null && !bearerToken.isBlank()) {
            rb.addHeader("Authorization", "Bearer " + bearerToken);
        }

        return CLIENT.newCall(rb.build()).execute();
    }

    private static String stripLeadingSlash(String p) {
        return p.startsWith("/") ? p.substring(1) : p;
    }
}