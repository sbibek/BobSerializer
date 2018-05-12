package bobc.core.exception;

/**
 * Runtime exception class for Bobc
 * 
 * @author bibek.shrestha
 *
 */
public class BobcException extends RuntimeException {
	private static final long serialVersionUID = 4530370493652879301L;
	private Integer errorCode;

	public BobcException(Integer errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

}
