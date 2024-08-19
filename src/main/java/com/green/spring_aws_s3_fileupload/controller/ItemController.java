package com.green.spring_aws_s3_fileupload.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.green.spring_aws_s3_fileupload.service.ItemService;
import com.green.spring_aws_s3_fileupload.utils.ItemAndFileSaveDTO;
import com.green.spring_aws_s3_fileupload.utils.FileUploadUtil;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class ItemController {
	
	
	private final ItemService itemService;
	
	@GetMapping("/admin/items/new")
	public String fileupload() {
		return "views/item/fileupload";
	}
	
	@PostMapping("/admin/fileupload")
	@ResponseBody
	public Map<String,String> fileupload(@RequestParam("itemFile")MultipartFile itemFile) throws IOException {
		return itemService.s3TempUpload(itemFile);
	}
	
	/*
	 * 상품등록 처리- 이미지는 s3의 temp -> upload로 이동시키고 변경된 주소의 값을 db에 저장
	 * 
	 * @return
	 */
	
	@PostMapping("/admin/items")
	public String save(ItemAndFileSaveDTO dto) {
		System.out.println(">>>"+dto);
		itemService.saveProcess(dto);
		return "redirect:/admin/items/new";
	}
	
}
