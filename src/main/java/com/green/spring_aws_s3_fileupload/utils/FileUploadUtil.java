package com.green.spring_aws_s3_fileupload.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class FileUploadUtil {
	
	//S3의 정보가 있어야함
	private final S3Client s3Client;
	
	@Value("${spring.cloud.aws.s3.buket}")
	private String bucket;
	@Value("${spring.cloud.aws.s3.upload-temp}")
	private String temp;
	@Value("${spring.cloud.aws.s3.upload-src}")
	private String upload;
	
	
	public Map<String,String> s3TempUpload(MultipartFile itemFile) throws IOException {
		//System.out.println(">>>"+s3Client);
		String orgFileName=itemFile.getOriginalFilename();
		String newFileName=newFileName(orgFileName);
		String bucketkey=temp+newFileName;
		
		InputStream is=itemFile.getInputStream();
		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
				.bucket(bucket)
				.key(bucketkey)
				.contentType(itemFile.getContentType())
				.acl(ObjectCannedACL.PUBLIC_READ)
				.build();
		RequestBody requestBody=RequestBody.fromInputStream(is, itemFile.getSize());
		//S3에 파일 업로드 처리
		s3Client.putObject(putObjectRequest, requestBody);
		
		String url = s3Client.utilities()
		.getUrl(builder->builder.bucket(bucket).key(bucketkey))
		.toString().substring(6); // https://이미지주소
		
		Map<String,String> resultMap = new HashMap<>();
		resultMap.put("url",url);
		resultMap.put("bucketkey",bucketkey);
		resultMap.put("orgName",orgFileName);
		
		return resultMap;
	}
	
	private String newFileName(String orgFileName) {
		int index=orgFileName.lastIndexOf(".");//위피
		return UUID.randomUUID().toString()
				+ orgFileName.substring(index); //".png"
	}

	public List<String> s3TempToImages(List<String> tempKeys) {
		
		List<String> uploadKeys=new ArrayList<>();
		tempKeys.forEach(tempkey->{
			
			String[] str = tempkey.split("/");
			String destinationkey=upload+str[str.length-1];
			
			CopyObjectRequest copyObjectRequest=CopyObjectRequest.builder()
					.sourceBucket(bucket)
					.sourceBucket(tempkey)
					.destinationBucket(bucket)
					.destinationBucket(destinationkey)
					.build();
			
			s3Client.copyObject(copyObjectRequest);
			s3Client.deleteObject(builder->builder.bucket(bucket).bucket(tempkey));
			
			String url = s3Client.utilities()
					.getUrl(builder->builder.bucket(bucket).key(destinationkey))
					.toString().substring(6);
				
			uploadKeys.add(url);
		});
		
		return uploadKeys;
	}
	
}
