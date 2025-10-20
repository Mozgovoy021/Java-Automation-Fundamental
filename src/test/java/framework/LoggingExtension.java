package framework;

import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggingExtension implements BeforeEachCallback, AfterEachCallback, TestWatcher {

    private Logger log(ExtensionContext ctx) {
        return LoggerFactory.getLogger(ctx.getRequiredTestClass());
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        var log = log(context);
        var name = context.getDisplayName();
        var method = context.getRequiredTestMethod().getName();
        log.info("▶ START test: {} ({})", name, method);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        log(context).info("✔ PASS test: {}", context.getDisplayName());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        var log = log(context);
        log.error("✘ FAIL test: {} — {}", context.getDisplayName(), cause.toString(), cause);

        Object instance = context.getRequiredTestInstance();
        try {
            var driverField = instance.getClass().getSuperclass().getDeclaredField("driver");
            driverField.setAccessible(true);
            Object drv = driverField.get(instance);
            if (drv instanceof WebDriver wd && wd instanceof TakesScreenshot ts) {
                String tsName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
                Path dir = Path.of("target", "screenshots");
                Files.createDirectories(dir);
                Path file = dir.resolve(STR."\{context.getRequiredTestClass().getSimpleName()}__\{context.getRequiredTestMethod().getName()}__\{tsName}.png");
                Files.write(file, ts.getScreenshotAs(OutputType.BYTES));
                log.error("📸 Screenshot saved: {}", file.toAbsolutePath());
            }
        } catch (Exception ignore) {
        }
    }

    @Override
    public void afterEach(ExtensionContext context) {
        log(context).info("⏹ END test: {}", context.getDisplayName());
    }
}