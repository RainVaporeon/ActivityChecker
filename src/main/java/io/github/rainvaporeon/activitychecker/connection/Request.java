package io.github.rainvaporeon.activitychecker.connection;

import java.util.Map;

public interface Request {

    Map<String, String> getHeaders();

    String getRequestContent();

}
