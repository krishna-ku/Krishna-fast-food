package com.restaurant.pdfgenerator;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.restaurant.dto.Keywords;
import com.restaurant.entity.Order;
import com.restaurant.entity.OrderItem;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class PdfGenerator {

	/**
	 * Generate PDF of Order Detils and that pdf we send to user on their email
	 * 
	 * @param order
	 * @return
	 * @throws IOException
	 */
	public static byte[] createPdf(Order order) {

		try {
			PDDocument document = new PDDocument();
			PDPage firstPage = new PDPage(PDRectangle.A4);

			document.addPage(firstPage);

			Format dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Format timeFormat = new SimpleDateFormat("HH:mm");

			int pageWidth = (int) firstPage.getTrimBox().getWidth();
			int pageHeight = (int) firstPage.getTrimBox().getHeight();

			PDPageContentStream contentStream = new PDPageContentStream(document, firstPage);

			addTextInPdf pdfText = new addTextInPdf(document, contentStream);

			PDFont font = PDType1Font.HELVETICA;
			PDFont italicFont = PDType1Font.HELVETICA_OBLIQUE;

			PDImageXObject headImage = PDImageXObject.createFromFile("src/main/resources/image/Indian Tadka head.png",
					document);

			contentStream.drawImage(headImage, 0, pageHeight - 235, pageWidth, 239);

			String[] contactDetail = new String[] { "1234567890", "0987654321" };
			pdfText.addMultiLineText(contactDetail, 18,
					(int) (pageWidth - font.getStringWidth("1234567890") / 1000 * 15 - 10), pageHeight - 25, font, 15,
					Color.black);

			pdfText.addSingleLineText(Keywords.RESTAURANT_NAME, 25, pageHeight - 150, font, 50, Color.red);

			pdfText.addSingleLineText(Keywords.CUSTOMER_NAME + order.getUser().getFirstName(), 25, pageHeight - 250, font,
					16, Color.black);
			pdfText.addSingleLineText(Keywords.STATUS + order.getStatus(), 25, pageHeight - 274, font, 16, Color.black);

			String orderID = Keywords.ORDERID + order.getOrderNo();
			float textWidth = pdfText.getTextWidth(orderID, font, 16);
			pdfText.addSingleLineText(orderID, (int) (pageWidth - 25 - textWidth), pageHeight - 250, font, 16,
					Color.black);

			float dateTextWidth = pdfText.getTextWidth(Keywords.DATE + dateFormat.format(new Date()), font, 16);
			pdfText.addSingleLineText(Keywords.DATE + dateFormat.format(new Date()), (int) (pageWidth - 25 - dateTextWidth),
					pageHeight - 274, font, 16, Color.black);

//			String time = timeFormat.format(new Date());
			float timeTextWidth = pdfText.getTextWidth(Keywords.TIME + timeFormat.format(new Date()), font, 16);
			pdfText.addSingleLineText(Keywords.TIME + timeFormat.format(new Date()), (int) (pageWidth - 25 - timeTextWidth),
					pageHeight - 298, font, 16, Color.black);

			addTableInPdf table = new addTableInPdf(document, contentStream);

			int[] cellWidths = { 70, 160, 120, 90, 100 };
			table.setTable(cellWidths, 30, 25, pageHeight - 350);
			table.setTableFont(font, 16, Color.black);

			Color tableHeadColor = new Color(240, 93, 11);
			Color tableBodyColor = new Color(219, 218, 198);

			table.addCell(Keywords.SI_NO, tableHeadColor);
			table.addCell(Keywords.ITEMS, tableHeadColor);
			table.addCell(Keywords.PRICE, tableHeadColor);
			table.addCell(Keywords.QUANTITY, tableHeadColor);
			table.addCell(Keywords.TOTAL, tableHeadColor);

			int i = 1;
			float totalPrice = 0;
			List<OrderItem> orderItems = order.getOrderItems();
			for (int j = 0; j < order.getOrderItems().size(); j++) {
				table.addCell(String.valueOf(i), tableBodyColor);
				table.addCell(String.valueOf(orderItems.get(j).getMenu().getName()), tableBodyColor);
				table.addCell(String.valueOf(orderItems.get(j).getMenu().getPrice()), tableBodyColor);
				table.addCell(String.valueOf(orderItems.get(j).getItemQuantity()), tableBodyColor);
				table.addCell(
						String.valueOf(orderItems.get(j).getMenu().getPrice() * orderItems.get(j).getItemQuantity()),
						tableBodyColor);
				totalPrice += (orderItems.get(j).getMenu().getPrice() * (orderItems.get(j).getItemQuantity()));
				i++;
			}

			table.addCell("", null);
			table.addCell("", null);
			table.addCell(Keywords.SUB_TOTAL, null);
			table.addCell("", null);
			table.addCell(String.valueOf(totalPrice), null);

			table.addCell("", null);
			table.addCell("", null);
			table.addCell(Keywords.GST, null);
			table.addCell(Keywords.GST_VALUE, null);
			DecimalFormat decimalFormat = new DecimalFormat("#.##");
			table.addCell(String.valueOf(decimalFormat.format(totalPrice * Keywords.GST_PERCENTAGE)), null);

			table.addCell("", null);
			table.addCell("", null);
			table.addCell(Keywords.GRAND_TOTAL, tableHeadColor);
			table.addCell("", tableHeadColor);
			table.addCell(String.valueOf(totalPrice += totalPrice * Keywords.GST_PERCENTAGE), tableHeadColor);

			// method of payments
			String[] paymentMethods = { "Methods of payments we accept: ", Keywords.PAYMENT_METHODS };
			pdfText.addMultiLineText(paymentMethods, 15, 12, 180, italicFont, 20, new Color(122, 122, 122));

			// authorised signatory
			contentStream.setStrokingColor(Color.black);
			contentStream.setLineWidth(2);
			contentStream.moveTo(pageWidth - 250, 150);
			contentStream.lineTo(pageWidth - 25, 150);
			contentStream.stroke();

			String autoSign =Keywords.AUTHORIZED_SIGNATORY;
			float autoSignWidth = pdfText.getTextWidth(autoSign, italicFont, 16);
			int xpos = pageWidth - 250 + pageWidth - 25;
			pdfText.addSingleLineText(autoSign, (int) (xpos - autoSignWidth) / 2, 125, italicFont, 16, Color.black);

			String bottomLine = Keywords.BOTTOM_LINE;
			float bottomLineWidth = pdfText.getTextWidth(bottomLine, font, 20);
			pdfText.addSingleLineText(bottomLine, (int) ((pageWidth - bottomLineWidth) / 2), 50, italicFont, 20,
					Color.DARK_GRAY);

			Color bottomRectColor = new Color(255, 91, 0);
			contentStream.setNonStrokingColor(bottomRectColor);
			contentStream.addRect(0, 0, pageWidth, 30);
			contentStream.fill();

			contentStream.close();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//			document.save("C:\\Users\\user\\Desktop\\restro.pdf");
			document.save(byteArrayOutputStream);
			document.close();
			log.info("PDF created successfully");
			return byteArrayOutputStream.toByteArray();

		} catch (Exception e) {
			e.getMessage();
		}
		return null;

	}

	/**
	 * add single line text and multiple line text in PDF
	 * 
	 * @author user
	 *
	 */
	public static class addTextInPdf {
		PDDocument document;
		PDPageContentStream contentStream;

		public addTextInPdf(PDDocument document, PDPageContentStream contentStream) {
			this.document = document;
			this.contentStream = contentStream;
		}

		void addSingleLineText(String text, int xPosition, int yPosition, PDFont font, float fontSize, Color color)
				throws IOException {

			contentStream.beginText();
			contentStream.setFont(font, fontSize);
			contentStream.setNonStrokingColor(color);
			contentStream.newLineAtOffset(xPosition, yPosition);
			contentStream.showText(text);
			contentStream.endText();
			contentStream.moveTo(0, 0);
		}

		void addMultiLineText(String[] textArray, float leading, int xPosition, int yPosition, PDFont font,
				float fontSize, Color color) throws IOException {

			contentStream.beginText();
			contentStream.setFont(font, fontSize);
			contentStream.setNonStrokingColor(color);
			contentStream.setLeading(leading);
			contentStream.newLineAtOffset(xPosition, yPosition);

			for (String text : textArray) {

				contentStream.showText(text);
				contentStream.newLine();
			}
			contentStream.endText();
			contentStream.moveTo(0, 0);
		}

		float getTextWidth(String text, PDFont font, float fontSize) throws IOException {

			return font.getStringWidth(text) / 1000 * fontSize;
		}
	}

	/**
	 * add tables and rows in pdf
	 * 
	 * @author user
	 *
	 */
	public static class addTableInPdf {

		PDDocument document;
		PDPageContentStream contentStream;
		private int[] colWidths;
		private int cellHeight;
		private int yPosition;
		private int xPosition;
		private int colPosition = 0;
		private int xInitialPosition;
		private float fontSize;
		private PDFont font;
		private Color fontColor;

		public addTableInPdf(PDDocument document, PDPageContentStream contentStream) {
			this.document = document;
			this.contentStream = contentStream;
		}

		void setTable(int[] colWidths, int cellHeight, int xPosition, int yPosition) {
			this.colWidths = colWidths;
			this.cellHeight = cellHeight;
			this.xPosition = xPosition;
			this.yPosition = yPosition;
			xInitialPosition = xPosition;
		}

		void setTableFont(PDFont font, float fontSize, Color fontColor) {

			this.font = font;
			this.fontSize = fontSize;
			this.fontColor = fontColor;
		}

		void addCell(String text, Color fillColor) throws IOException {

			contentStream.setStrokingColor(1f);
			if (fillColor != null) {
				contentStream.setNonStrokingColor(fillColor);
			}
			contentStream.addRect(xPosition, yPosition, colWidths[colPosition], cellHeight);

			if (fillColor == null)
				contentStream.stroke();
			else
				contentStream.fillAndStroke();

			contentStream.beginText();
			contentStream.setNonStrokingColor(fontColor);

			if (colPosition == 4 || colPosition == 2) {
				float fontWidth = font.getStringWidth(text) / 1000 * fontSize;
				contentStream.newLineAtOffset(xPosition + colWidths[colPosition] - 20 - fontWidth, yPosition + 10);
			} else {
				contentStream.newLineAtOffset(xPosition + 20, yPosition + 10);
			}

			contentStream.showText(text);
			contentStream.endText();

			xPosition = xPosition + colWidths[colPosition];
			colPosition++;

			if (colPosition == colWidths.length) {
				colPosition = 0;
				xPosition = xInitialPosition;
				yPosition -= cellHeight;
			}

		}
	}
}
