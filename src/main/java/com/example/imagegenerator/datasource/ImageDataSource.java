package com.example.imagegenerator.datasource;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.imagegenerator.model.ImageDetailsModel;

public interface ImageDataSource extends CrudRepository<ImageDetailsModel, Long> {
	Optional<ImageDetailsModel> findByImageId(Integer id);
}
