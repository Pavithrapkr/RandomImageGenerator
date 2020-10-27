package com.example.imagegenerator.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.imagegenerator.datasource.ImageDataSource;
import com.example.imagegenerator.entity.ImageEntity;
import com.example.imagegenerator.model.ImageDetailsModel;

@Service
public class ImageGeneratorServiceImpl implements ImageGeneratorService {
	
	private final String BASE_URL = "https://picsum.photos/500/500";
	
	@Autowired
	private ImageDataSource imageDataSource;

	@Override
	public byte[] generateImageById(Integer id) {
		URL imageUrl = this.getImageUrl(id);
		return this.getImage(imageUrl);
	}

	@Override
	public List<ImageEntity> getAllImages() {
		Iterable<ImageDetailsModel> images = imageDataSource.findAll();
		return StreamSupport.stream(images.spliterator(), false)
				.map(imageModel -> {
					ImageEntity imageEntity = new ImageEntity();
					imageEntity.setId(imageModel.getImageId());
					imageEntity.setUrl(imageModel.getUrl());
					return imageEntity;
				})
				.collect(Collectors.toList());
	}

	private URL getImageUrl(Integer id) {
		Optional<ImageDetailsModel> optionalModel = imageDataSource.findByImageId(id);
		
		if(optionalModel.isPresent()) {
			ImageDetailsModel model = optionalModel.get();
			String url = model.getUrl();
			try {
				return new URL(url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}else {
			return this.generateRandomUrl(id);
		}
		return null;		
	}
	
	private URL generateRandomUrl(Integer id) {
		URL url = null; 
		try {
			url = this.getFinalURL(new URL(BASE_URL));
			this.saveUrl(id, url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	private void saveUrl(Integer id, URL url) {
		ImageDetailsModel imageModel = new ImageDetailsModel();
		imageModel.setImageId(id);
		imageModel.setUrl(url.toString());
		imageDataSource.save(imageModel);
	}

	public URL getFinalURL(URL url) {
		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setInstanceFollowRedirects(false);
			con.connect();
			int resCode = con.getResponseCode();
			if (resCode == HttpURLConnection.HTTP_SEE_OTHER || resCode == HttpURLConnection.HTTP_MOVED_PERM
					|| resCode == HttpURLConnection.HTTP_MOVED_TEMP) {
				String Location = con.getHeaderField("Location");
				if (Location.startsWith("/")) {
					Location = url.getProtocol() + "://" + url.getHost() + Location;
				}
				return getFinalURL(new URL(Location));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return url;
	}

	private byte[] getImage(URL imageUrl) {
		byte[] imageBytes = {};
		try {
			BufferedImage image = ImageIO.read(imageUrl);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	
			ImageIO.write(image, "jpg", outputStream);
			imageBytes = outputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageBytes;
	}
}
