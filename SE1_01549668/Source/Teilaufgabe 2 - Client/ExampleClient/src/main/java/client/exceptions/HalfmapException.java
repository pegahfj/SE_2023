package client.exceptions;

public class HalfmapException extends Exception {
	private static final long serialVersionUID = 1L;

	public HalfmapException(String message) {
		super(message);
	}
	public HalfmapException(String message, Throwable cause) {
        super(message, cause);
    }

    public HalfmapException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}