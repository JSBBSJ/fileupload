package com.green.spring_aws_s3_fileupload.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.green.spring_aws_s3_fileupload.utils.ItemAndFileSaveDTO;

public interface ItemService {

	void saveProcess(ItemAndFileSaveDTO dto);

	Map<String, String> s3TempUpload(MultipartFile itemFile) throws IOException;

}
