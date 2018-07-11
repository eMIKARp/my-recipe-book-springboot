package pl.myrecipebasket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.myrecipebasket.model.Recipe;
import pl.myrecipebasket.repository.RecipeRepository;

@Service
public class RecipeService {

		RecipeRepository recipeRepository;
		
		@Autowired
		public void setRecipeRepository(RecipeRepository recipeRepository) {
			this.recipeRepository=recipeRepository;
		}
		
		public void saveRecipe(Recipe recipe) {
			recipeRepository.save(recipe);
		}
		
}
