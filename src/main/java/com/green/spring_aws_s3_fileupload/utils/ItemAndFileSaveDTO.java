package com.green.spring_aws_s3_fileupload.utils;

import java.util.List;

import lombok.Data;

@Data
public class ItemAndFileSaveDTO {
	
	private List<String> bucketkey;//이미지 버킷키
	private List<String> orgName;//이미지 이름
	private String name;//제품이름
	private String price;//가격
	
}
