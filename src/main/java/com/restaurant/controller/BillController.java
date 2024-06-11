package com.restaurant.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.service.impl.BillServiceImpl;

@RestController
public class BillController {

	@Autowired
	private BillServiceImpl billService;

	/**
	 * get all bills Service URL - \bills
	 * 
	 * @return
	 */
	@GetMapping("/bills")
	public ResponseEntity<List<String>> getAllBills() {

		List<String> bills = billService.getAllPdf();

		return new ResponseEntity<>(bills, HttpStatus.OK);
	}

	/**
	 * download bill as pdf Service URL -
	 * 
	 * @param bill
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException 
	 * @throws MalformedURLException
	 */
	@GetMapping("/download/{bill}")
	public ResponseEntity<byte[]> downloadBillPDF(@PathVariable String bill) throws URISyntaxException, IOException {

		byte[] byteArray = billService.downloadBill(bill);		
		
		if (byteArray != null) {
//			HttpHeaders headers = new HttpHeaders();
//			headers.setLocation(new URI(url));
//			Map<String, String> map=new HashMap<>();
			
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(byteArray);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

//		Resource resource = billService.downloadBill(bill);
//		return ResponseEntity.ok()
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename())
//				.contentType(MediaType.APPLICATION_PDF).body(resource);
	}
}
