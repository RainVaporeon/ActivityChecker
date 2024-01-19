package io.github.rainvaporeon.activitychecker.connection;

import java.net.Proxy;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public interface HttpClient {

    Proxy proxy();

    Response get(String target, Request request);

    Response post(String target, Request request);

    default CompletableFuture<Response> getAsync(String target, Request request) {
        return CompletableFuture.supplyAsync(() -> this.get(target, request));
    }

    default CompletableFuture<Response> postAsync(String target, Request request) {
        return CompletableFuture.supplyAsync(() -> this.post(target, request));
    }

    static HttpClient mainHttpClient() {
        return new HttpClientImpl();
    }

    static HttpClient proxied(Proxy proxy) {
        return new HttpClientImpl(proxy);
    }
}
