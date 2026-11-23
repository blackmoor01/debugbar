package dev.debugbar.core;

import java.util.List;

/**
 * One SQL statement executed during a request.
 * 
 * @author klvna
 */
public record QueryRecord(
        String sql,
        List<List<Object>> params,
        long elapsedMs,
        boolean success,
        boolean batch,
        int batchSize,
        List<String> callSite,
        long startedAtMs) {  
}
