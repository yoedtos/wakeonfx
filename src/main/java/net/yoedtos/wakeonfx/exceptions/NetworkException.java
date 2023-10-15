package net.yoedtos.wakeonfx.exceptions;

public class NetworkException extends Exception {
    public NetworkException() {
        super();
    }

    public NetworkException(String message) {
        super(message);
    }

    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
