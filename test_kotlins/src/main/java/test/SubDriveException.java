package test;


public class SubDriveException extends DriveException {
    public SubDriveException() {
        super();
    }

    public SubDriveException(String message) {
        super(message);
    }

    public SubDriveException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubDriveException(Throwable cause) {
        super(cause);
    }

    protected SubDriveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
