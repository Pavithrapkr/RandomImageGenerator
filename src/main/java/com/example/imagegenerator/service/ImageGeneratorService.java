package com.example.imagegenerator.service;

import java.util.List;

import com.example.imagegenerator.entity.ImageEntity;

public interface ImageGeneratorService {

	public byte[] generateImageById(Integer id);
	
	public List<ImageEntity> getAllImages();
}
