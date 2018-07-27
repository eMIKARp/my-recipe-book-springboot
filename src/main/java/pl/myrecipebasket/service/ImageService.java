package pl.myrecipebasket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.myrecipebasket.model.Image;
import pl.myrecipebasket.repository.ImageRepository;

@Service
public class ImageService {

	private ImageRepository imageRepository;
	
	@Autowired
	public void setImageRepository(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}

	public void saveImage(Image image) {
		imageRepository.save(image);
	}
	
}

