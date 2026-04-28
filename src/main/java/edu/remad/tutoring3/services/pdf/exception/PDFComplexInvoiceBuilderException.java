package edu.remad.tutoring3.services.pdf.exception;

/**
 * Exception for PDFComplexInvoiceBuilder
 * 
 * @author edu.remad
 * @since 2026
 */
public class PDFComplexInvoiceBuilderException extends RuntimeException {

	/** generated serial version UID */
	private static final long serialVersionUID = 2265459715538240129L;

	/**
	 * Constructor of {@link PDFComplexInvoiceBuilderException}
	 */
	public PDFComplexInvoiceBuilderException() {}
	
	/**
	 * Constructor of {@link PDFComplexInvoiceBuilderException}
	 * 
	 * @param message error message
	 */
	public PDFComplexInvoiceBuilderException (String message) {
		super(message);
	}
	
	/**
	 * Constructor of {@link PDFComplexInvoiceBuilderException}
	 * 
	 * @param message error message
	 * @param cause {@link Throwable} with cause
	 */
	public PDFComplexInvoiceBuilderException (String message, Throwable cause) {
		super(message, cause);
	}
	
}
