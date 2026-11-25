package dev.debugbar.core;

import java.util.List;
import java.util.stream.Collectors;

import dev.debugbar.core.DebugBarConfig;

/**
 * Finds the frames in the current stack that belong to the application,
 * skipping JDBC, Hibernate, proxies, the container and DebugBar itself.
 */

public class StackCapture {

    private static final List<String> FRAMEWORKERS_PREFIXES = List.of(
        "java.", "javax.", "jakarta.", "jdk.", "sun.", "com.sun.",
        "dev.debugbar.core.DebugBarQueryListener", "dev.debugbar.core.StackCapture",
        "dev.debugbar.web.", "dev.debugbar.jakarta.", "dev.debugbar.spring.",
        "net.ttddyy.",
        "org.hibernate.", "org.eclipse.", "org.springframework.", "org.apache.",
        "org.jboss.", "io.undertow.", "org.glassfish.", "fish.payara.", "com.zaxxer.",
        "org.h2.", "org.postgresql.", "com.mysql.", "org.junit."
    );

    private  StackCaputure() {}

    static List<String> capture(int depth, List<String> appPackages) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return java.util.Arrays.stream(stackTrace)
                .map(StackTraceElement::toString)
                .filter(frame -> isAppFrame(frame, appPackages))
                .limit(depth)
                .collect(Collectors.toList());
    }

    static List<String> capture(DebugBarConfig config) {
        if (!config.captureStackTraces()) {
            return List.of();
        }
        List<String> appPackages = config.appPackages();
        return StackWalker.getInstance().walk(frames -> frames
                .filter(f -> isApplicationFrame(f.getClassName(), appPackages))
                .limit(config.stackDepth())
                .map(f -> f.getClassName() + "." + f.getMethodName() + ":" + f.getLineNumber())
                .collect(Collectors.toList()));
    }


    static boolean isApplicationFrame(String className, List<String> appPackages) {
        if (className.contains("$$") || className.contains("$Proxy")) {
            return false; // CDI / Spring generated proxies
        }
        if (!appPackages.isEmpty()) {
            return appPackages.stream().anyMatch(className::startsWith);
        }
        return FRAMEWORK_PREFIXES.stream().noneMatch(className::startsWith);
    }

}
