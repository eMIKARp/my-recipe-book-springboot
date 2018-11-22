package pl.myrecipebasket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.myrecipebasket.model.Category;
import pl.myrecipebasket.repository.CategoryRepository;

/**
 * 
 * Object <b>{@code CategoryService}</b> is a intermediate layer between 
 * persistence layer ({@code CategoryRepository}) and view layer ({@code HomeController} 
 * and {@code RecipeController}). Contains all {@code Category} related business 
 * logic of <em>MyRecipeBasket</em> application.
 * 
 * @see pl.myrecipebasket.repository.CategoryRepository
 * @see pl.myrecipebasket.web.controller.HomeController
 * @see pl.myrecipebasket.web.controller.RecipeController
 * 
 */

@Service
public class CategoryService {

	/**
	 * {@code CategoryRepository} field declaration
	 */
	
	private CategoryRepository categoryRepository;
	
	/**
	 * This method injects {@code CategoryRepository} object into {@code CategoryService}
	 * and enables {@code CategoryService} to perform CRUD operations on {@code Category}
	 * database table.
	 * 
	 * @param categoryRepository {@code CategoryRepository} object
	 */
	
	@Autowired
	public void setCategoryRepository(CategoryRepository categoryRepository) {
		this.categoryRepository=categoryRepository;
	}
	
	/**
	 * This method retrieves all {@code Category} objects present in 
	 * <em>MyRecipeBasket</em> database
	 * 
	 * @return a list of {@code Category} objects
	 */
	
	public List<Category> getCategories(){
		return categoryRepository.findAll();
	}
}
