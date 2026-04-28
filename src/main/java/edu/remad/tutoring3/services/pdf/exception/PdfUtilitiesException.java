package edu.remad.tutoring3.services.pdf.exception;

/**
 * Exception for PdfUtilities
 * 
 * @author edu.remad
 * @since 2026
 */
public class PdfUtilitiesException extends RuntimeException {

	/** generated UID */
	private static final long serialVersionUID = 674272294969224176L;

	/**
	 * Constructor for {@link PdfUtilitiesException}
	 */
	public PdfUtilitiesException() {}
	
	/**
	 * Constructor for {@link PdfUtilitiesException}
	 * 
	 * @param message error message
	 */
	public PdfUtilitiesException (String message) {
		super(message);
	}
	
	/**
	 * Constructor for {@link PdfUtilitiesException}
	 * 
	 * @param message error message
	 * @param cause {@link Throwable} with cause
	 */
	public PdfUtilitiesException (String message, Throwable cause) {
		super(message, cause);
	}
	
}
