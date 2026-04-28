package edu.remad.tutoring3.services.pdf.exception;

/**
 * Exception for MaxMainMemoryBytes 
 * 
 * @author edu.remad
 * @since 2026
 */
public class MaxMainMemoryBytesException extends RuntimeException {

	/** generated serial version UID */
	private static final long serialVersionUID = -9021952377790852335L;

	/**
	 * Creates instance of {@link MaxMainMemoryBytesException}
	 * 
	 * @param message error message
	 */
	public MaxMainMemoryBytesException(String message) {
		super(message);
	}
}
