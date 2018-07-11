package pl.myrecipebasket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.myrecipebasket.model.Category;
import pl.myrecipebasket.repository.CategoryRepository;

@Service
public class CategoryService {

	private CategoryRepository categoryRepository;
	
	@Autowired
	public void setCategoryRepository(CategoryRepository categoryRepository) {
		this.categoryRepository=categoryRepository;
	}
	
	public List<Category> getCategories(){
		return categoryRepository.findAll();
	}
}
