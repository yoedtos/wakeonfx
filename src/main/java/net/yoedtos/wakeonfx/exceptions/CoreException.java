package net.yoedtos.wakeonfx.exceptions;

public class CoreException extends Exception {
    public CoreException() {
        super();
    }

    public CoreException(String message) {
        super(message);
    }

    public CoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
