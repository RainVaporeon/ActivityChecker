package io.github.rainvaporeon.activitychecker.connection;

import io.github.rainvaporeon.activitychecker.misc.WeakHashSet;
import io.github.rainvaporeon.fishutils.logging.Loggers;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.lang.ref.Reference;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

// specifically for requests above 10
public class HttpClientImpl implements HttpClient {
    private final OkHttpClient client;

    HttpClientImpl() {
        this(null);
    }

    HttpClientImpl(Proxy px) {
        client = ClientPool.getClient(px);
    }

    @Override
    public Proxy proxy() {
        return client.proxy();
    }

    @Override
    public Response get(String target, Request request) {
        okhttp3.Request req = new okhttp3.Request.Builder()
                .url(target)
                .headers(Headers.of(request.getHeaders()))
                .get()
                .build();

        try (okhttp3.Response response = client.newCall(req).execute()) {
            int code = response.code();

            if (code == 429) {
                String reset = response.header("ratelimit-reset", "60");
                long delayMills = Integer.parseInt(reset) * 1000L;
                return Throttling.invokeLater(() -> get(target, request), delayMills);
            }

            ResponseBody body = response.body();
            if(body == null) return new SimpleResponse(code, "");
            return new SimpleResponse(code, body.string());
        } catch (IOException ex) {
            Loggers.getThreadLogger().error(STR."GET request failed whilst fetching \{target}: ", ex);
            return null;
        } finally {
            Reference.reachabilityFence(client);
        }
    }

    @Override
    public Response post(String target, Request request) {
        okhttp3.Request req = new okhttp3.Request.Builder()
                .url(target)
                .headers(Headers.of(request.getHeaders()))
                .post(RequestBody.create(request.getRequestContent().getBytes(StandardCharsets.UTF_8)))
                .build();

        try (okhttp3.Response response = client.newCall(req).execute()) {
            int code = response.code();

            if (code == 429) {
                String reset = response.header("ratelimit-reset", "30");
                long delayMills = Integer.parseInt(reset) * 1000L;
                return Throttling.invokeLater(() -> get(target, request), delayMills);
            }

            ResponseBody body = response.body();
            if(body == null) return new SimpleResponse(code, "");
            return new SimpleResponse(code, body.string());
        } catch (IOException ex) {
            Loggers.getThreadLogger().error(STR."POST request failed whilst fetching \{target}: ", ex);
            return null;
        } finally {
            Reference.reachabilityFence(client);
        }
    }

    private static class ClientPool {
        private static final WeakHashSet<OkHttpClient> cache = new WeakHashSet<>(4, 0.25f);

        private static OkHttpClient getClient(Proxy proxy) {
            for(OkHttpClient client : cache) {
                if(Objects.equals(client.proxy(), proxy)) return client;
            }
            OkHttpClient client = new OkHttpClient.Builder()
                    .proxy(proxy == null ? Proxy.NO_PROXY : proxy)
                    .build();
            cache.add(client);
            return client;
        }
    }
}
