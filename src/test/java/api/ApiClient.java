package api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ApiClient {
    private final OkHttpClient client = HttpClientProvider.get();

    public Response send(Request request) throws IOException {
        return client.newCall(request).execute();
    }
}