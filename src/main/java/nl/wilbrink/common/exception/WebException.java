package nl.wilbrink.common.exception;

public class WebException extends RuntimeException {

    private int httpStatus;

    private String message;

    private Throwable cause;

    public WebException(String message, int httpStatus) {
        this(message, httpStatus, null);
    }

    public WebException(String message, int httpStatus, Throwable cause) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.cause = cause;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

}
