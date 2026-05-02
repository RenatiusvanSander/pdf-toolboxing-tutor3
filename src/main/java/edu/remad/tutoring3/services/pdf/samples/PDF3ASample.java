package edu.remad.tutoring3.services.pdf.samples;

import java.io.IOException;
import java.io.InputStream;
import java.util.GregorianCalendar;

import org.apache.jempbox.xmp.XMPMetadata;
import org.apache.jempbox.xmp.XMPSchemaBasic;
import org.apache.jempbox.xmp.XMPSchemaDublinCore;
import org.apache.jempbox.xmp.pdfa.XMPSchemaPDFAId;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDMarkInfo;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;

import edu.remad.tutoring3.services.pdf.constants.ContentLayoutDataConstants;

/**
 * sample for {@link PDF3A}
 * 
 * @author edu.remad
 * @since 2026
 */
public final class PDF3ASample {

	/**
	 * private Constructor
	 */
	private PDF3ASample() {
		// do not instantiate
	}

	/**
	 * runs sample
	 * 
	 * @param args arguments
	 * @throws Exception Exception
	 */
	public static void main(String[] args) throws Exception {
		try (PDDocument document = new PDDocument()) {
			PDPage pageA4 = ContentLayoutDataConstants.PAGE;
			document.addPage(pageA4);

			// create a page with the message where needed
			PDPageContentStream contentStream = new PDPageContentStream(document, pageA4);
			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12f);
			contentStream.newLineAtOffset(100, 700);
			contentStream.showText("hhhhhhh");
			contentStream.endText();
			contentStream.saveGraphicsState();
			contentStream.close();

			PDMetadata metaData = new PDMetadata(document);
			PDDocumentCatalog catalog = document.getDocumentCatalog();
			catalog.setMetadata(metaData);

			XMPMetadata xmpMeta = new XMPMetadata();
			XMPSchemaPDFAId pdfaid = new XMPSchemaPDFAId(xmpMeta);
			xmpMeta.addSchema(pdfaid);

			XMPSchemaDublinCore dublinCore = xmpMeta.addDublinCoreSchema();
			dublinCore.addCreator("fff");
			dublinCore.setAbout("ddd");

			XMPSchemaBasic xmpSchemaBasic = xmpMeta.addBasicSchema();
			xmpSchemaBasic.setAbout("");
			xmpSchemaBasic.setCreatorTool("Tutoring");
			xmpSchemaBasic.setCreateDate(GregorianCalendar.getInstance());

			PDDocumentInformation docuemntInformation = new PDDocumentInformation();
			docuemntInformation.setProducer("Remy");
			docuemntInformation.setAuthor("Meier");
			document.setDocumentInformation(docuemntInformation);

			PDMarkInfo markinfo = new PDMarkInfo();
			markinfo.setMarked(true);
			document.getDocumentCatalog().setMarkInfo(markinfo);

			pdfaid.setPart(3);
			pdfaid.setConformance("A");
			pdfaid.setAbout("");

			metaData.importXMPMetadata(xmpMeta.asByteArray());

			InputStream colorProfileRgbV4IccPreference = PDF3ASample.class
					.getResourceAsStream("/colorprofiles/sRGB_v4_ICC_preference.icc");

			PDOutputIntent outputIntent = new PDOutputIntent(document, colorProfileRgbV4IccPreference);
			outputIntent.setInfo("sRGB v4 Preference");
			outputIntent.setOutputCondition("sRGB v4 Preference");
			outputIntent.setOutputConditionIdentifier("sRGB v4 Preference");
			outputIntent.setRegistryName("http://www.color.org");

			document.save("C:\\Users\\remad\\pdf3a-versuch.pdf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}