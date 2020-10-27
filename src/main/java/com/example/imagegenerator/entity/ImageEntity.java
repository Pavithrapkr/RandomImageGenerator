package com.example.imagegenerator.entity;


import javax.persistence.Entity;

import com.example.imagegenerator.model.ImageDetailsModel;

import lombok.Data;

@Data
public class ImageEntity {
	
	private Integer id;
	
	private String url;
	
//	public static ImageEntity fromModel(ImageDetailsModel imageModel) {
//		ImageEntity imageEntity = new ImageEntity();
//
//		imageEntity.id = imageModel.getImageId();
//		imageEntity.url = imageModel.getUrl();
//
//		return imageEntity;
//	}
}
