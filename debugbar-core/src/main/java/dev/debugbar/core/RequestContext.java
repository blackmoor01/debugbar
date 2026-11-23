package dev.debugbar.core;

/**
 * Binds the current RequestProfile to the thread handling the request.
 *
 * Known limitation (v0.1): queries run on other threads
 * (async, @Asynchronous, CompletableFuture, parallel streams)
 * are not attributed to the request.
 * 
 * @author klvna
 */

public final class RequestContext {
    
    private static final ThreadLocal<RequestProfile> CURRENT = new ThreadLocal<>();
    
    private RequestContext(){}
    
    public static void begin(RequestProfile profile){
        CURRENT.set(profile);
    }
    
    public static RequestProfile current(){
        return CURRENT.get();
    }
    
    public static void end(){
        CURRENT.remove();
    }
    
}
