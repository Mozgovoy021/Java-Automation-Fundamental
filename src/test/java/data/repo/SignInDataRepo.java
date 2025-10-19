package data.repo;

import data.SignInData;
import data.SignInNegativeData;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class SignInDataRepo {

    public static Stream<SignInNegativeData> negativeFromCsv() {
        String resource = "/login-negative.csv";
        InputStream is = SignInDataRepo.class.getResourceAsStream(resource);
        if (is == null) {
            throw new IllegalStateException("Resource not found: " + resource + " (поклади файл у src/test/resources)");
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            var list = br.lines()
                    .skip(1) // пропускаємо хедер
                    .filter(line -> !line.isBlank())
                    .map(line -> {
                        String[] p = line.split(",", -1);
                        String email = p.length > 0 ? p[0].trim() : "";
                        String password = p.length > 1 ? p[1].trim() : "";
                        String emailErr = p.length > 2 ? p[2].trim() : "";
                        String passErr = p.length > 3 ? p[3].trim() : "";
                        return new SignInNegativeData(email, password, emailErr, passErr);
                    })
                    .toList();
            return list.stream();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    public static Stream<SignInData> positiveData() {
        String expected = System.getProperty("expectedUbsUrl", "https://www.greencity.cx.ua/#/ubs");
        return Stream.of(
                new SignInData("dehoye6184@aupvs.com", "Test55555@", expected),
                new SignInData("dehoye6184@aupvs.com", "Test55555@", expected)
        );
    }
}
