package test;


public class DriveException extends BaseException {
    public DriveException() {
        super();
    }

    public DriveException(String message) {
        super(message);
    }

    public DriveException(String message, Throwable cause) {
        super(message, cause);
    }

    public DriveException(Throwable cause) {
        super(cause);
    }

    protected DriveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
