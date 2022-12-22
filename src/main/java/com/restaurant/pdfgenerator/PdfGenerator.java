//package com.restaurant.pdfgenerator;
//
//import java.text.Format;
//import java.text.SimpleDateFormat;
//
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.common.PDRectangle;
//import org.apache.pdfbox.pdmodel.font.PDFont;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
//
//import lombok.Data;
//
//@Data
//public class PdfGenerator {
//PDDocument document = new PDDocument();
//		PDPage firstPage = new PDPage(PDRectangle.A4);
//
//		document.addPage(first);
//
//		String name = "krishna kumar";
//		String callNo = "+91 11111111";
//
//		Format dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//		Format timeFormat = new SimpleDateFormat("HH:mm");
//
//		int pageWidth = (int) firstPage.getTrimBox().getWidth();
//		int pageHeight = (int) firstPage.getTrimBox().getHeight();
//
//		PDPageContentStream contentStream = new PDPageContentStream(document, firstPage);
//
//		PdfText pdfText = new PdfText(document, contentStream);
//
//		PDFont font = PDType1Font.HELVETICA;
//		PDFont italicFont = PDType1Font.HELVETICA_OBLIQUE;
//
//		PDImageXObject headImage = PDImageXObject.createFromFile("src/main/resources/image/Indian Tadka head.png",
//				document);
//
//		contentStream.drawImage(headImage, 0, pageHeight - 235, pageWidth, 239);
//		
//		String[] contactDetail =new String[] {"1234567890","0987654321"};
//		pdfText.addMultiLineText(contactDetail, 18, (int) (pageWidth-font.getStringWidth("1234567890")/1000*15-10),pageHeight-25,font,15, Color.black);
//		
//		pdfText.addSingleLineText("Delhi fast food", 25, pageHeight-150, font, 50, Color.red);
//
//		contentStream.close();
//		document.save("C:\\Users\\user\\Desktop\\restro.pdf");
//		document.close();
//		System.out.println("PDF Created");
//
//	}