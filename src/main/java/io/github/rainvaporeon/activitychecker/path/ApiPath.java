package io.github.rainvaporeon.activitychecker.path;

import io.github.rainvaporeon.activitychecker.connection.Request;

import java.util.Map;

public class ApiPath {
    private ApiPath() {}

    public static final String LIST = "https://api.wynncraft.com/v3/player";

    public static class Headers {
        private Headers() {}

        public static final Request DEFAULT = new Request() {
            private final Map<String, String> header = Map.of("User-Agent", "SpiritLight/1.0");;

            @Override
            public Map<String, String> getHeaders() {
                return header;
            }

            @Override
            public String getRequestContent() {
                return null;
            }
        };
    }
}
