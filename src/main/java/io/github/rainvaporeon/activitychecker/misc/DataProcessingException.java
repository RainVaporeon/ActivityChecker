package io.github.rainvaporeon.activitychecker.misc;

import javax.xml.crypto.Data;

public class DataProcessingException extends Exception {
    public DataProcessingException() { super(); }
    public DataProcessingException(String message) { super(message); }
    public DataProcessingException(Throwable cause) { super(cause); }
    public DataProcessingException(String message, Throwable cause) { super(message, cause); }
}
