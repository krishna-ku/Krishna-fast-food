package com.restaurant.dto;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.entity.Menu;

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
	 * Convert file of excel into list
	 * 
	 * @param file
	 * @return true or false
	 */
	public static List<MenuDTO> convertExcelFileToListOfMenus(InputStream inputStream) {

		List<MenuDTO> list = new ArrayList<>();
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
					
//					Menu menu=new Menu();
//					if(menu.getName()==cell.getStringCellValue())
//						rowNumber++;
					
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
				list.add(m);
			}

		} catch (Exception e) {
			log.error("Error while converting excel file into list", e.getMessage());
		}
		return list;
	}

}
