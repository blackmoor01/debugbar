package dev.debugbar.core;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Everything collected for a single HTTP request.
 * Mutable while the request runs, then finished via {@link #finish(int)}.
 * 
 * @author klvna
 */

public class RequestProfile {
    
    private final String id = UUID.randomUUID().toString();
    private final String method;
    private final String uri;
    private final Instant startedAt = Instant.now();
    private final long startNanos = System.nanoTime();
    
    private final List<QueryRecord> queries = Collections.synchronizedList(new ArrayList<>());
    
    private volatile long totalRequestMs;
    private volatile int status;
    
    public RequestProfile(String method, String uri){
        this.method = method;
        this.uri = uri;
    }
    
    void addQuery(QueryRecord record){
        queries.add(record);
    }
    
    long elapsedSinceStartMs(){
        return (System.nanoTime() - startNanos) / 1_000_000;
    }
    
    public void finish(int status){
        this.status = status;
        this.totalRequestMs = elapsedSinceStartMs();
    }
    
    public String getId() { return id; }
    public String getMethod() { return method; }
    public String getUri() { return uri; }
    public Instant getStartedAt() { return startedAt; }
    public long getTotalRequestMs() { return totalRequestMs; }
    public int getStatus() { return status; }
    
    
    public List<QueryRecord> getQueries(){
        synchronized (queries) {
            return List.copyOf(queries);
        }
    }
    
}
