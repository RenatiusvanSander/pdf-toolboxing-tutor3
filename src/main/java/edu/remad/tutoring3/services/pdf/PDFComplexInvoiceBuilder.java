package edu.remad.tutoring3.services.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import edu.remad.tutoring3.persistence.models.InvoiceEntity;
import edu.remad.tutoring3.services.pdf.constants.ContentLayoutDataConstants;
import edu.remad.tutoring3.services.pdf.exception.PDFComplexInvoiceBuilderException;
import edu.remad.tutoring3.services.pdf.exception.PdfUtilitiesException;
import edu.remad.tutoring3.services.pdf.pagecontent.SinglePageContentLayouter;
import edu.remad.tutoring3.services.pdf.utilities.PdfUtilities;

/**
 * Builds a complex invoice
 * 
 * @author edu.remad
 * @since 2026
 */
public class PDFComplexInvoiceBuilder {

	private InvoiceEntity invoice;

	private DateTimeFormatter dayFormatFormatter;

	private DateTimeFormatter timeFormatFormatter;

	private PDDocument pdfDoucment;

	/**
	 * Constructor
	 * 
	 * @param invoice invoice to produce PDF
	 * @return {@link PDFComplexInvoiceBuilder}
	 */
	public PDFComplexInvoiceBuilder invoice(InvoiceEntity invoice) {
		this.invoice = invoice;

		return this;
	}

	/**
	 * Allows to define a {@link DateTimeFormatter} for day format
	 * 
	 * @param dayFormatFormatter formats the day / date on an invoice
	 * @return {@link PDFComplexInvoiceBuilder}
	 */
	public PDFComplexInvoiceBuilder dayFormatFormatter(DateTimeFormatter dayFormatFormatter) {
		this.dayFormatFormatter = dayFormatFormatter;

		return this;
	}

	/**
	 * Allows to define a {@link DateTimeFormatter} for time format
	 * 
	 * @param timeFormatFormatter formats the time / hours on an invoice
	 * @return {@link PDFComplexInvoiceBuilder}
	 */
	public PDFComplexInvoiceBuilder timeFormatFormatter(DateTimeFormatter timeFormatFormatter) {
		this.timeFormatFormatter = timeFormatFormatter;

		return this;
	}

	/**
	 * @return byte array of invoice PDF
	 */
	public byte[] build() {
		try {
			if (dayFormatFormatter != null || timeFormatFormatter != null) {
				return buildDocument(
						PdfUtilities.createContentLayoutData(invoice, dayFormatFormatter, timeFormatFormatter));
			} else {
				return buildDocument(PdfUtilities.createContentLayoutData(invoice));
			}
		} catch (PdfUtilitiesException | PDFComplexInvoiceBuilderException | NullPointerException e) {
			String invoicContent = "";

			if (Objects.isNull(invoice)) {
				invoicContent = "[]";
			} else {
				invoicContent = "[" + invoice.toString() + "]";
			}

			e.printStackTrace();
			throw new PDFComplexInvoiceBuilderException(
					"Invoice was not build, check object of InvoiceEntity: " + invoicContent, e);
		}
	}

	private byte[] buildDocument(ContentLayoutData contentLayout) {
		try (PDDocument document = pdfDoucment != null ? pdfDoucment : new PDDocument();
				ByteArrayOutputStream out = new ByteArrayOutputStream();) {

			if (pdfDoucment == null) {
				PDPage firstPage = ContentLayoutDataConstants.PAGE;
				document.addPage(firstPage);
				buildPageContent(contentLayout, document, firstPage);
			}

			document.setDocumentInformation(PdfUtilities.populateDocumentInformationBuilder(contentLayout).build());
			document.save(out);

			return out.toByteArray();
		} catch (PDFComplexInvoiceBuilderException | IOException e) {
			throw new PDFComplexInvoiceBuilderException("Invoice was not created.", e);
		}
	}

	private void buildPageContent(ContentLayoutData contentLayout, PDDocument document, PDPage firstPage) {
		try (PDPageContentStream contentStream = new PDPageContentStream(document, firstPage)) {
			new SinglePageContentLayouter(document, firstPage, contentLayout, contentStream).build();
		} catch (Exception e) {
			throw new PDFComplexInvoiceBuilderException("Invoice page content was not rendered", e);
		}
	}

	/**
	 * @return {@link PDPage}
	 */
	public PDPage buildPage() {
		try (PDDocument document = new PDDocument()) {
			ContentLayoutData contentLayout = PdfUtilities.createContentLayoutData(invoice);
			PDPage firstPage = ContentLayoutDataConstants.PAGE;
			document.addPage(firstPage);
			buildPageContent(contentLayout, document, firstPage);
			this.pdfDoucment = document;

			return firstPage;
		} catch (IOException e) {
			throw new PDFComplexInvoiceBuilderException("PDF Page was not created.", e);
		}
	}

}
