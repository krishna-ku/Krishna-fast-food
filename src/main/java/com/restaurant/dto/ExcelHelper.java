package com.restaurant.dto;

import org.springframework.web.multipart.MultipartFile;

public class ExcelHelper {
	
	/**
	 * Check file is excel or not 
	 * @param file
	 * @return true or false
	 */

	public static boolean checkExcelFormat(MultipartFile file) {
		
		String contentType = file.getContentType();
		
		if(contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
		{
			return true;
		}
		else {
			return false;
		}
	}

}
