package pl.myrecipebasket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.myrecipebasket.model.Image;
import pl.myrecipebasket.repository.ImageRepository;

/**
 * 
 * Object <b>{@code ImageService}</b> is a intermediate layer between 
 * persistence layer ({@code ImageRepository}) and view layer ({@code CommentController}). 
 * Contains all {@code Image} related business logic of <em>MyRecipeBasket</em> application.
 * 
 * @see pl.myrecipebasket.repository.ImageRepository
 * @see pl.myrecipebasket.web.controller.CommentController
 * 
 */

@Service
public class ImageService {

	/**
	 *	{@code ImageRepository} field declaration 
	 */
	
	private ImageRepository imageRepository;
	
	/**
	 * This method injects {@code ImageRepository} object into {@code ImageService}
	 * and enables {@code ImageService} to perform CRUD operations on 
	 * {@code Image} database table.
	 * 
	 * @param imageRepository {@code ImageRepository} object
	 */
	
	@Autowired
	public void setImageRepository(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}

	/**
	 * 
	 * This method persists {@code Image} object in 
	 * <em>MyRecipeBasket</em> database
	 * 
	 * @param image {@code Image} object
	 */
	
	public void saveImage(Image image) {
		imageRepository.save(image);
	}
	
	/**
	 * 
	 * This method retrieves from <em>MyRecipeBasket</em> database 
	 * single {@code Image} object which id property equals to one provided as
	 * parameter of this method.
	 * 
	 * @param id id property of {@code Image} object that is being looked for
	 * @return {@code Image} object
	 */
	
	public Image getImage(Long id) {
		return imageRepository.getOne(id);
	}
	
}

