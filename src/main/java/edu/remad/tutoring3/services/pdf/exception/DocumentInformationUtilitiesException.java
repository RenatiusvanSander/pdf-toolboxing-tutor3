package edu.remad.tutoring3.services.pdf.exception;

/**
 * Exception for DocumentInformationUtilities
 * 
 * @author edu.remad
 * @since 2026
 */
public class DocumentInformationUtilitiesException extends RuntimeException {
	
	/** generated serial version UID */
	private static final long serialVersionUID = 2717040849180165053L;
	
	/**
	 * Constructor
	 * 
	 * @param errorMessage error message
	 */
	public DocumentInformationUtilitiesException(String errorMessage) {
		super(errorMessage);
	}

}
