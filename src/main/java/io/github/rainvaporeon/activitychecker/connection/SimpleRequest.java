package io.github.rainvaporeon.activitychecker.connection;

import java.util.Map;

public class SimpleRequest implements Request {
    private final Map<String, String> headers;
    private final String content;

    public SimpleRequest(Map<String, String> headers, String content) {
        this.headers = headers;
        this.content = content;
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public void removeHeader(String key) {
        this.headers.remove(key);
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public String getRequestContent() {
        return content;
    }
}
