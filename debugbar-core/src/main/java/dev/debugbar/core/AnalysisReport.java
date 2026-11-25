package dev.debugbar.core;

import java.util.List;

/**
 * Summary shown at the top of the panel for one request.
 */

public record AnalysisReport(
    int queryCount,
    long totalDbMs,
    long requestMs,
    double dbTimePercent,
    QueryRecord slowestQuery,
    List<QueryRecord> slowQueries,
    List<RepeatedQuery> duplicates,
    List<RepeatedQuery> nPlusOneSuspects
) {

    /** A group of queries that ran more than once. */
    public record RepeatedQuery(String sql, int count, long totalMs, List<String> callSite) {}
}
