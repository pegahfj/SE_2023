package client.exceptions;

public class MapModelException extends Exception {
	private static final long serialVersionUID = 1L;

	public MapModelException(String message) {
		super(message);
	}
	public MapModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapModelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
