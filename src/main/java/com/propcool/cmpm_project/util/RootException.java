package com.propcool.cmpm_project.util;

public class RootException extends RuntimeException {
    public RootException() {
    }

    public RootException(String message) {
        super(message);
    }

    public RootException(String message, Throwable cause) {
        super(message, cause);
    }

    public RootException(Throwable cause) {
        super(cause);
    }
}
