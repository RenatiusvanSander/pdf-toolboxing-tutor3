package edu.remad.tutoring3.services.pdf.utilities;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import edu.remad.tutoring3.persistence.models.AddressEntity;
import edu.remad.tutoring3.persistence.models.InvoiceEntity;
import edu.remad.tutoring3.persistence.models.UserEntity;
import edu.remad.tutoring3.services.pdf.ContentLayoutData;
import edu.remad.tutoring3.services.pdf.constants.ContentLayoutDataConstants;
import edu.remad.tutoring3.services.pdf.documentinformation.DocumentInformationBuilder;
import edu.remad.tutoring3.services.pdf.exception.PdfUtilitiesException;

/**
 * Utility class for all Builders
 * 
 * @author edu.remad
 * @since 2026
 */
public final class PdfUtilities {

	private static final DateTimeFormatter GERMAN_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

	/**
	 * private Constructor
	 */
	private PdfUtilities() {
		// do not instantiate
	}

	/**
	 * Creates from invoice an {@link ContentLayoutData}
	 * 
	 * @param invoice invoice as {@link InvoiceEntity}
	 * @return {@link ContentLayoutData}
	 */
	public static ContentLayoutData createContentLayoutData(InvoiceEntity invoice) {
		try {
			return createMainContentLayoutData(invoice, GERMAN_DATE_FORMATTER, TIME_FORMATTER);
		} catch (RuntimeException e) {
			throw new PdfUtilitiesException("PdfUtilities: Error while creating ContentLayoutData.", e);
		}
	}

	/**
	 * Creates from invoice an {@link ContentLayoutData}
	 * 
	 * @param invoice               invoice as {@link InvoiceEntity}
	 * @param dateAndTimeFormatters several Date and Time Formatters for customizing
	 * @return {@link ContentLayoutData}
	 */
	public static ContentLayoutData createContentLayoutData(InvoiceEntity invoice, DateTimeFormatter dayFormatter,
			DateTimeFormatter timeFormatter) {
		try {
			return createMainContentLayoutData(invoice, dayFormatter, timeFormatter);
		} catch (RuntimeException e) {
			throw new PdfUtilitiesException("PdfUtilities: Error while creating ContentLayoutData.", e);
		}
	}

	private static ContentLayoutData createMainContentLayoutData(InvoiceEntity invoice, DateTimeFormatter dayFormatter,
			DateTimeFormatter timeFormatter) {
		try {
			UserEntity user = invoice.getUserId();
			AddressEntity address = user.getAddresses().get(0);

			ContentLayoutData contentLayoutData = new ContentLayoutData();
			contentLayoutData.setLogo(createLogo());
			contentLayoutData.setCustomerName(user.getName(), user.getFamilyName());
			contentLayoutData.setFont(PDType1Font.HELVETICA);
			contentLayoutData.setItalicFont(PDType1Font.HELVETICA_OBLIQUE);
			contentLayoutData.setFontColor(Color.BLACK);
			contentLayoutData.setStreetHouseNumber(address.getAddressStreet(), address.getAddressHouseNo());
			contentLayoutData.setLocationZipCode(String.valueOf(address.getAddressZipCode()), address.getPlace());
			contentLayoutData.setContactCompany(ContentLayoutDataConstants.CONTACT_COMPANY);
			contentLayoutData.setContactName(ContentLayoutDataConstants.CONTACT_NAME);
			contentLayoutData.setContactStreetHouseNo(ContentLayoutDataConstants.CONTACT_STREET_HOUSE_NO);
			contentLayoutData.setContactZipAndLocation(
					ContentLayoutDataConstants.CONTACT_ZIP + " " + ContentLayoutDataConstants.CONTACT_LOCATION);
			contentLayoutData.setContactMobile(ContentLayoutDataConstants.CONTACT_MOBILE);
			contentLayoutData.setContactEmail(ContentLayoutDataConstants.CONTACT_EMAIL);
			contentLayoutData.setInvoiceNo(String.valueOf(invoice.getNo()));
			contentLayoutData.setDayFormatter(dayFormatter != null ? dayFormatter : getGermanDateFormatter());
			contentLayoutData.setTimeFormatter(timeFormatter != null ? timeFormatter : getTimeFormatter());
			contentLayoutData.setTableHeaderColor(ContentLayoutDataConstants.TABLE_HEADER_COLOR);
			contentLayoutData.setTableBodyColor(ContentLayoutDataConstants.TABLE_BODY_COLOR);
			contentLayoutData.setPaymentMethods(ContentLayoutDataConstants.PAYMENT_METHODS);
			contentLayoutData.setPaymentMethodColor(ContentLayoutDataConstants.PAYMENT_METHOD_COLOR);
			contentLayoutData.setTutoringAppointmentDate(invoice.getTutoringDate().format(dayFormatter));
			contentLayoutData.setInvoiceCreationDate(invoice.getCreationDate().format(dayFormatter));
			contentLayoutData.setCapitalFontSize(ContentLayoutDataConstants.CAPITAL_FONT_SIZE);
			contentLayoutData.setTextFontSize(ContentLayoutDataConstants.TEXT_FONT_SIZE);
			contentLayoutData.setPaymentMethodFontSize(ContentLayoutDataConstants.PAYMENT_METHOD_FONT_SIZE);
			contentLayoutData.setbottomLine(ContentLayoutDataConstants.BOTTOM_LINE);
			contentLayoutData.setBottomLineFontSize(ContentLayoutDataConstants.BOTTOM_LINE_FONT_SIZE);
			contentLayoutData.setBottomLineFontColor(ContentLayoutDataConstants.BOTTOM_LINE_FONT_COLOR);
			contentLayoutData.setBottomLineWidth(ContentLayoutDataConstants.BOTTOM_LINE_WIDTH);
			contentLayoutData.setBottomRectColor(ContentLayoutDataConstants.BOTTOM_RECT_COLOR);
			contentLayoutData.setBottomRect(ContentLayoutDataConstants.BOTTOM_RECT);
			contentLayoutData.setAuthoSign(ContentLayoutDataConstants.AUTHO_SIGN);
			contentLayoutData.setAuthoSignColor(ContentLayoutDataConstants.AUTHO_SIGN_COLOR);
			contentLayoutData.setTableCellWidths(ContentLayoutDataConstants.TABLE_CELL_WIDTHS);
			contentLayoutData.setTableCellHeight(ContentLayoutDataConstants.TABLE_CELL_HEIGHT);
			contentLayoutData.setTableHeaders(ContentLayoutDataConstants.TABLE_HEADERS);
			contentLayoutData.setTableRows(createTableRows(invoice));
			contentLayoutData.setPageWidth((int) ContentLayoutDataConstants.PAGE.getTrimBox().getWidth());
			contentLayoutData.setPageHeight((int) ContentLayoutDataConstants.PAGE.getTrimBox().getHeight());
			contentLayoutData.setInvoiceNoLabel(ContentLayoutDataConstants.INVOICE_NO_LABEL);
			contentLayoutData.setInvoiceDateLabel(ContentLayoutDataConstants.INVOICE_DATE_LABEL);
			contentLayoutData.setInvoicePerformanceDateLabel(ContentLayoutDataConstants.INVOICE_PERFORMANCE_DATE_LABEL);
			contentLayoutData
					.setValueAddedTaxDisclaimerText(ContentLayoutDataConstants.VALUE_ADDED_TAX_DISCLAIMER_TEXT);
			contentLayoutData.setDocumentInformationCreator(ContentLayoutDataConstants.DOCUMENT_INFORMATION_CREATOR);
			contentLayoutData.getDocumentInformationCreator();
			contentLayoutData.setDocumentInformationKeywords(
					new String[] { ContentLayoutDataConstants.DOCUMENT_INFORMATION_KEYWORD_INVOICE,
							String.valueOf(invoice.getNo()), contentLayoutData.getCustomerName() });
			contentLayoutData.setHasMainContentLayoutData(true);
			contentLayoutData.setSplitDelimiter("\\.");

			return contentLayoutData;
		} catch (RuntimeException e) {
			throw new PdfUtilitiesException("PdfUtilities: Error while creating ContentLayoutData.", e);
		}
	}

	/**
	 * Create table rows
	 * 
	 * @param invoice invoice data as {@link InvoiceEntity}
	 * @return table rows
	 */
	public static List<Map<String, String>> createTableRows(InvoiceEntity invoice) {
		BigDecimal price = invoice.getPriceId().getPrice().setScale(2, RoundingMode.HALF_UP);
		List<Map<String, String>> tableRows = new ArrayList<>();
		Map<String, String> row1 = new LinkedHashMap<>();
		row1.put(ContentLayoutDataConstants.TABLE_HEADERS.get(0), "1");
		row1.put(ContentLayoutDataConstants.TABLE_HEADERS.get(1),
				invoice.getServiceContractId().getServiceContractName());
		row1.put(ContentLayoutDataConstants.TABLE_HEADERS.get(2), String.valueOf(price));
		row1.put(ContentLayoutDataConstants.TABLE_HEADERS.get(3), "1");
		row1.put(ContentLayoutDataConstants.TABLE_HEADERS.get(4), String.valueOf(price) + "EUR");
		tableRows.add(row1);

		return tableRows;
	}

	/**
	 * Creates custom logo file
	 * 
	 * @return {@link File}
	 */
	public static File createLogo() {
		return new File(ContentLayoutDataConstants.LOGO_FILE_PATH);
	}

	/**
	 * Creates custom logo file
	 * 
	 * @return {@link File}
	 */
	public static File createCustomLogo() {
		return new File(ContentLayoutDataConstants.LOGO_FILE_PATH);
	}

	/**
	 * Creates custom logo file
	 * 
	 * @param filePath string encoded full file path
	 * @return {@link File}
	 */
	public static File createCustomLogo(String filePath) {
		return new File(filePath);
	}

	/**
	 * Creates data of content layout
	 * 
	 * @param invoices invoices massaged to {@link ContentLayoutData}
	 * @return list of {@link ContentLayoutData}
	 */
	public static List<ContentLayoutData> createContentLayoutDatas(List<InvoiceEntity> invoices) {
		if (invoices == null || invoices.isEmpty()) {
			throw new PdfUtilitiesException(
					"Error: createContentLayoutDatas was called either with null or list of type ContentLayoutData was empty.");
		}

		List<ContentLayoutData> contentLayoutDatas = new ArrayList<>();
		for (InvoiceEntity invoice : invoices) {
			contentLayoutDatas.add(createContentLayoutData(invoice));
		}

		return contentLayoutDatas;
	}

	/**
	 * Loads PDF ByteArray To PDDocument
	 * 
	 * @param pdfByteArray array of bytes
	 * @return {@link PDDocument}
	 */
	public static PDDocument pdfByteArrayToPDDocument(byte[] pdfByteArray) {
		try {
			return PDDocument.load(pdfByteArray);
		} catch (IOException e) {
			throw new PdfUtilitiesException("PdfUtilities: PDF ByteArray was not converted to PDDocument.", e);
		}
	}

	/**
	 * populates DocumentInformationBuilder
	 * 
	 * @param contentLayout {@link ContentLayoutData}
	 * @return {@link DocumentInformationBuilder}
	 */
	public static DocumentInformationBuilder populateDocumentInformationBuilder(ContentLayoutData contentLayout) {
		DocumentInformationBuilder builder = new DocumentInformationBuilder();
		builder.setAuthor(contentLayout.getContactName());
		builder.setInvoiceNumber(Long.getLong(contentLayout.getInvoiceNo()));
		builder.setCreator(contentLayout.getCreator());
		builder.setSubject(contentLayout.getInvoiceNoLabel() + " " + contentLayout.getInvoiceNo());
		builder.setCreationDate(DocumentInformationUtilities.extractCreationDate(contentLayout));
		builder.setKeywords(contentLayout.getDocumentInformationKeywords());

		return builder;
	}

	/**
	 * Converts LocalDateTime to string-encoded date.
	 * 
	 * @param date          a date as {@link LocalDateTime}
	 * @param timeFormatter the formatter for the time as {@link DateTimeFormatter}
	 * @return String-encoded Date
	 */
	public static String convertLocalDateTimeToStringDate(LocalDateTime date, DateTimeFormatter timeFormatter) {
		return date.format(timeFormatter);
	}

	/**
	 * @return German Date Formatter for days with Pattern {@code dd.MM.yyyy} as
	 *         {@link DateTimeFormatter}
	 */
	public static DateTimeFormatter getGermanDateFormatter() {
		return GERMAN_DATE_FORMATTER;
	}

	/**
	 * @return German Time Formatter for hours and minutes with Pattern
	 *         {@code HH:mm} as {@link DateTimeFormatter}
	 */
	public static DateTimeFormatter getTimeFormatter() {
		return TIME_FORMATTER;
	}
	
}
