package dev.debugbar.core;

import java.util.List;

/**
 * Runtime settings. Defaults are safe: disabled unless switched on explicitly.
 * @author klvna
 */
public record DebugBarConfig(
        boolean enabled,
        int maxStoredRequests,
        boolean captureStackTraces,
        int stackDepth,
        List<String> appPackages,
        long slowQueryMs,
        int nPlusOneThreshold,
        List<String> ignoredPaths) {
    
    public static final String ENDPOINT_PATH = "/__debugbar";
    
    public static DebugBarConfig defaults() {
        return new DebugBarConfig(
                false,
                100,
                true,
                5,
                List.of(),
                100,
                5,
                List.of(ENDPOINT_PATH, "/static/", "/resources/", "/favicon.ico")
        );
    }
    
    public DebugBarConfig withEnabled(boolean value){
        return new DebugBarConfig(value, maxStoredRequests, captureStackTraces, stackDepth, 
                appPackages, slowQueryMs, nPlusOneThreshold, ignoredPaths);
    }

    
}
