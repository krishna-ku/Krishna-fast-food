package com.restaurant.controller;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
	 * get all bills
	 * Service URL - \bills
	 * @return
	 */
	@GetMapping("bills")
	public ResponseEntity<List<String>> getAllBills() {

		List<String> bills = billService.getAllPdf();

		return new ResponseEntity<>(bills, HttpStatus.OK);
	}

	/**
	 * download bill as pdf 
	 * Service URL - 
	 * @param bill
	 * @return
	 * @throws MalformedURLException
	 */
	@PostMapping("/download/{bill}")
	public ResponseEntity<Resource> downloadBillPDF(@PathVariable String bill) throws MalformedURLException {
		Resource resource = billService.downloadBill(bill);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename())
				.contentType(MediaType.APPLICATION_PDF).body(resource);
	}
}
