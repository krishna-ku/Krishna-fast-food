package com.restaurant.dto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.exception.BadRequestException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelHelper {

	/**
	 * Check file is excel or not
	 * 
	 * @param file
	 * @return true or false
	 */

	public static boolean checkExcelFormat(MultipartFile file) {

		String contentType = file.getContentType();

		if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
			return true;
		else
			return false;
	}

	/**
	 * Convert excel file into list
	 * 
	 * @param file
	 * @return true or false
	 */
	public static Set<MenuDTO> convertExcelFileToListOfMenus(InputStream inputStream) {

		Set<MenuDTO> set = new HashSet<>();
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheet("data");
			int rowNumber = 0;
			Iterator<Row> iterator = sheet.iterator();
			while (iterator.hasNext()) {
				Row row = iterator.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cells = row.iterator();
				int cId = 0;

				MenuDTO m = new MenuDTO();
				while (cells.hasNext()) {
					Cell cell = cells.next();

					switch (cId) {
					case 0:
						m.setName(cell.getStringCellValue());
						break;
					case 1:
						m.setPrice((float) cell.getNumericCellValue());
						break;
					case 2:
						m.setDescription(cell.getStringCellValue());
						break;
					default:
						break;
					}
					cId++;
				}
				set.add(m);
			}

		} catch (Exception e) {
			log.error("Error while converting excel file into list", e);
			throw new BadRequestException(e.getMessage());
		}
		return set;
	}

	public static String TYPE = "text/csv";
	static String[] HEADERs = { "name", "price", "description" };

	public static boolean hasCSVFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}
	
	/**
	 * convert CSV file into list
	 * @param is
	 * @return list
	 */
	public static List<MenuDTO> csvToMenus(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<MenuDTO> menus = new ArrayList<>();

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				MenuDTO menu = new MenuDTO(csvRecord.get("name"), Float.parseFloat(csvRecord.get("price")),
						csvRecord.get("description"));
				menus.add(menu);
			}

			return menus;
		} catch (IOException e) {
			log.error("Error while converting excel file into list", e);
			throw new BadRequestException(e.getMessage());
		}
	}

}
