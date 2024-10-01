package com.javaproject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaproject.entity.ImageData;
import com.javaproject.repository.StorageRepository;
import com.javaproject.util.ImageUtils;

@Service
public class StorageService {

	@Autowired
	private StorageRepository repository;
	
	public String uploadImage(MultipartFile file) {
		
		ImageData imageData = repository.save(ImageData.builder()
				.name(file.getOriginalFilename())
				.type(file.getContentType())
				// .imageData(file.getBytes()).build())
				.imageData(ImageUtils.compressImage(file.getBytes())).build());
		if(imageData!=null) {
			return "file uploaded successfully: " + file.getOriginalFilename();
		}
		return null;
	}
	
	public byte[] downloadImage(String fileName) {
		Optional<ImageData> dbImageData = repository.findByName(fileName);
		byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
		return images;
	}
}
