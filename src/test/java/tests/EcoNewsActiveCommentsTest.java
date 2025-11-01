package tests;

import api.support.ApiGetHelper;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EcoNewsActiveCommentsTest {

    private static final String BASE = System.getProperty("base.main",
            "https://greencity.greencity.cx.ua");
    private static final String ECO_NEWS_ID = System.getProperty("ecoNewsId", "1");
    private static final String TOKEN = System.getProperty("auth.token", "");

    @Test
    void getActiveComments_shouldReturn200() throws IOException {
        String path = "/eco-news/" + ECO_NEWS_ID + "/comments";
        try (Response res = ApiGetHelper.get(BASE, path, Map.of("active", "true"),
                emptyToNull(TOKEN))) {

            assertEquals(200, res.code(), "Expected 200 for active=true");
            assertNotNull(res.body());
            assertFalse(res.body().string().isBlank(), "Response body should not be blank");
        }
    }

    @Test
    void getActiveComments_withBadParam_shouldReturn400() throws IOException {
        String path = "/eco-news/" + ECO_NEWS_ID + "/comments";
        try (Response res = ApiGetHelper.get(BASE, path, Map.of("active", "notBoolean"),
                emptyToNull(TOKEN))) {

            assertEquals(400, res.code(), "Expected 400 for active=notBoolean");
        }
    }

    private static String emptyToNull(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }
}