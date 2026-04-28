package edu.remad.tutoring3.services.pdf.exception;

/**
 * Exception for PDFCreationBuilder
 * 
 * @author edu.remad
 * @since 2026
 */
public class PDFCreationBuilderException extends RuntimeException {

	/** generated serial version UID */
	private static final long serialVersionUID = -3066049477218215753L;

	/**
	 * Constructor of {@link PDFCreationBuilderException}
	 */
	public PDFCreationBuilderException() {
	}

	/**
	 * Constructor of {@link PDFCreationBuilderException}
	 * 
	 * @param message error message
	 */
	public PDFCreationBuilderException(String message) {
		super(message);
	}

	/**
	 * Constructor of {@link PDFCreationBuilderException}
	 * 
	 * @param message error message
	 * @param cause {@link Throwable} with cause
	 */
	public PDFCreationBuilderException(String message, Throwable cause) {
		super(message, cause);
	}
}
