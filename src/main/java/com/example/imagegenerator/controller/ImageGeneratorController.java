package com.example.imagegenerator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.imagegenerator.entity.ImageEntity;
import com.example.imagegenerator.service.ImageGeneratorService;


@RestController
public class ImageGeneratorController {
	
	@Autowired
	private ImageGeneratorService imageGeneratorService;
	
	@GetMapping(value= "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] generateImage(@PathVariable Integer id) {
		return imageGeneratorService.generateImageById(id);		
	}
	
	@GetMapping("/images")
	@ResponseBody
	public List<ImageEntity> getAllImages(){
		return imageGeneratorService.getAllImages();
	}
}
