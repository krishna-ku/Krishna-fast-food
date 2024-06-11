package com.restaurant.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;

@Service
public class BillServiceImpl {

	private static final String fileDirectory = "D:\\Eclips IDE\\RestaurantApp\\src\\main\\resources\\bills\\";

	@Autowired
	private AmazonS3 s3;

	/**
	 * get all pdf name as string from local storage
	 * 
	 * @return List<String>
	 */
	public List<String> getAllPdf() {
		List<String> fileNames = new ArrayList<>();

		ListObjectsV2Request request = new ListObjectsV2Request().withBucketName("restaurantbills");

		ListObjectsV2Result result;
		do {
			result = s3.listObjectsV2(request);
			for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
				String fileName = objectSummary.getKey();
				if (fileName.endsWith(".pdf")) {
					fileNames.add(fileName);
				}
			}
			request.setContinuationToken(result.getNextContinuationToken());
		} while (result.isTruncated());

//		ListObjectsV2Result result = s3.listObjectsV2(request);
//
//		for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
//			String fileName = objectSummary.getKey();
//			if (fileName.endsWith(".pdf"))
//			fileNames.add(fileName);
//		}

		return fileNames;
	}

	public byte[] downloadBill(String bill) throws IOException {

//		AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		Date expiration = new Date();
		long expTime = expiration.getTime() + 30000;
		expiration.setTime(expTime);

//		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest("restaurantbills", bill)
//				.withMethod(HttpMethod.GET).withExpiration(expiration);
		GetObjectRequest request=new GetObjectRequest("restaurantbills",bill);
		S3Object object = s3.getObject(request);
		
		return IOUtils.toByteArray(object.getObjectContent());

//		File file = new File(fileDirectory, bill);
//		Resource resource = new UrlResource(file.toURI());
//
//		if (file.exists() && resource.exists()) {
////	    	HttpHeaders headers=new HttpHeaders();
////	    	headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + bill);
////	    	headers.setContentType(MediaType.APPLICATION_PDF);
//			return resource;
//		} else {
//			return null;
//		}
	}

}
