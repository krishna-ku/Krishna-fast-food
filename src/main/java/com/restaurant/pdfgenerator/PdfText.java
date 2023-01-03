//package com.restaurant.pdfgenerator;
//
//import java.awt.Color;
//import java.io.IOException;
//
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.font.PDFont;
//
//import lombok.Data;
//
//@Data
//public class PdfText {
//	PDDocument document;
//	PDPageContentStream contentStream;
//
//	public PdfText(PDDocument document, PDPageContentStream contentStream) {
//		this.document = document;
//		this.contentStream = contentStream;
//	}
//
//	void addSingleLineText(String text, int xPosition, int yPosition, PDFont font, float fontSize, Color color)
//			throws IOException {
//
//		contentStream.beginText();
//		contentStream.setFont(font, fontSize);
//		contentStream.setNonStrokingColor(color);
//		contentStream.newLineAtOffset(xPosition, yPosition);
//		contentStream.showText(text);
//		contentStream.endText();
//		contentStream.moveTo(0, 0);
//	}
//
//	void addMultiLineText(String[] textArray, float leading, int xPosition, int yPosition, PDFont font, float fontSize,
//			Color color) throws IOException {
//
//		contentStream.beginText();
//		contentStream.setFont(font, fontSize);
//		contentStream.setNonStrokingColor(color);
//		contentStream.setLeading(leading);
//		contentStream.newLineAtOffset(xPosition, yPosition);
//
//		for (String text : textArray) {
//
//			contentStream.showText(text);
//			contentStream.newLine();
//		}
//		contentStream.endText();
//		contentStream.moveTo(0, 0);
//	}
//
//	float getTextWidth(String text, PDFont font, float fontSize) throws IOException {
//
//		return font.getStringWidth(text) / 1000 * fontSize;
//	}
//}
