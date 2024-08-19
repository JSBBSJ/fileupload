package com.green.spring_aws_s3_fileupload.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.green.spring_aws_s3_fileupload.service.ItemService;
import com.green.spring_aws_s3_fileupload.utils.ItemAndFileSaveDTO;
import com.green.spring_aws_s3_fileupload.utils.FileUploadUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemServiceProcess implements ItemService{
	
	private final FileUploadUtil fileUploadUtil;
	
	@Override
	public void saveProcess(ItemAndFileSaveDTO dto) {
		//이미지는 temp-> upload	
		List<String> uploadKeys = fileUploadUtil.s3TempToImages(dto.getBucketkey());
		//상품정보 먼저 저장하세요
		//DB저장하세요-파일은 여러개 파일 테이블
	}

	@Override
	public Map<String, String> s3TempUpload(MultipartFile itemFile) throws IOException {
		
		return fileUploadUtil.s3TempUpload(itemFile);
	}
	
	
	
	
}
