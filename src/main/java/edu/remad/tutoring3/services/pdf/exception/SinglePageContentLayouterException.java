package edu.remad.tutoring3.services.pdf.exception;

import edu.remad.tutoring3.services.pdf.pagecontent.SinglePageContentLayouter;

/**
 * Exception for {@link SinglePageContentLayouter}
 * 
 * @author edu.remad
 * @since 2026
 */
public class SinglePageContentLayouterException extends RuntimeException {

	/** generated serial version UID */ 
	private static final long serialVersionUID = 307742577599785730L;

	/**
	 * Constructor for {@link SinglePageContentLayouterException}
	 */
	public SinglePageContentLayouterException() {}
	
	/**
	 * Constructor for {@link SinglePageContentLayouterException}
	 * 
	 * @param message error message
	 */
	public SinglePageContentLayouterException (String message) {
		super(message);
	}
	
	/**
	 * Constructor for {@link SinglePageContentLayouterException}
	 * 
	 * @param message error message
	 * @param cause {@link Throwable} with cause
	 */
	public SinglePageContentLayouterException (String message, Throwable cause) {
		super(message, cause);
	}
}
