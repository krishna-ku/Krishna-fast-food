package com.restaurant.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl {

	private static final String fileDirectory = "D:\\Eclips IDE\\RestaurantApp\\src\\main\\resources\\bills\\";

	/**
	 * get all pdf name as string from local storage
	 * @return List<String>
	 */
	public List<String> getAllPdf() {

		List<String> fileNames = new ArrayList<>();
		try {
			Files.walk(Paths.get(fileDirectory)).filter(Files::isRegularFile).filter(p -> p.toString().endsWith(".pdf"))
					.forEach(p -> fileNames.add(p.getFileName().toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fileNames;
	}
	
	public Resource downloadBill(String bill) throws MalformedURLException {
	    File file = new File(fileDirectory, bill);
	    Resource resource = new UrlResource(file.toURI());

	    if (file.exists() && resource.exists()) {
//	    	HttpHeaders headers=new HttpHeaders();
//	    	headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + bill);
//	    	headers.setContentType(MediaType.APPLICATION_PDF);
	        return resource;
	    } else {
	        return null;
	    }
	}

}
