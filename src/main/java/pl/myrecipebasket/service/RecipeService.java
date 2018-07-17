package pl.myrecipebasket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.myrecipebasket.model.Category;
import pl.myrecipebasket.model.Recipe;
import pl.myrecipebasket.model.User;
import pl.myrecipebasket.repository.CategoryRepository;
import pl.myrecipebasket.repository.RecipeRepository;
import pl.myrecipebasket.repository.UserRepository;

@Service
public class RecipeService {

		RecipeRepository recipeRepository;
		UserRepository userRepository;
		CategoryRepository categoryRepository;
		
		@Autowired
		public void setRecipeRepository(RecipeRepository recipeRepository) {
			this.recipeRepository=recipeRepository;
		}
		
		@Autowired
		public void setUserRepository(UserRepository userRepository) {
			this.userRepository=userRepository;
		}
		@Autowired
		public void setCategoryRepository(CategoryRepository categoryRepository) {
			this.categoryRepository=categoryRepository;
		}
		
		public void saveRecipe(Recipe recipe) {
			recipeRepository.save(recipe);
		}
		
	
		public List<Recipe> getRecipesAddedByUser(User user){
			return recipeRepository.findAllByUsrWhoAddedRecipe(user);
		}
		
		public Recipe getRecipeById(Long id) {
			return recipeRepository.findById(id);
		}
		
		public List<Recipe> getAllSharedRecipes(){
			return recipeRepository.findAllByIsShared(true);
		}
		
		public void removeRecipe(Recipe recipe) {
			
			for (Category category: recipe.getrCategories()) {
				category.getcRecipes().remove(recipe);
				categoryRepository.save(category);
			}
			
			for (User user: recipe.getUsrWhoAddedRecipeToFavourites()) {
				user.getFavRecipes().remove(recipe);
				userRepository.save(user);
			}
			
			User user = userRepository.findByUsername(recipe.getUsrWhoAddedRecipe().getUsername());
			user.getOwnRecipes().remove(recipe);
			userRepository.save(user);
	
		}
		
}
