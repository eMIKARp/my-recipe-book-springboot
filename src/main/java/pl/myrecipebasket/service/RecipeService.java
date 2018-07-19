package pl.myrecipebasket.service;

import java.util.ArrayList;
import java.util.Comparator;
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

		public List<Recipe> getAllSharedRecipesWithinCategory(String cName){
				
				List<Recipe> sharedRecipes = recipeRepository.findAllByIsShared(true);
			    List<Recipe> recipesToShow = new ArrayList<>();
			    if (!cName.equals("all")) {
					Category category = categoryRepository.findBycName(cName);
				    for (Recipe recipe: sharedRecipes) {
				    	if (recipe.getrCategories().contains(category)) {
				    		recipesToShow.add(recipe);
				    	}
				    }	
				} else {
			    	recipesToShow.addAll(sharedRecipes);
			    }
				
			    recipesToShow.sort(new VoteComparator());
			    
				return recipesToShow;
		}
		
		public List<Recipe> getAllUsersRecipesWhithinCategory(String cName, User user){
			
			List<Recipe> userRecipes = user.getFavRecipes();
			List<Recipe> recipesToShow = new ArrayList<>();
			if (!cName.equals("all")) {
				Category category = categoryRepository.findBycName(cName);
				for (Recipe recipe: userRecipes) {
					if (recipe.getrCategories().contains(category)) {
						recipesToShow.add(recipe);
					}
				}	
			} else {
				recipesToShow.addAll(userRecipes);
			}
			
			return recipesToShow;
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
		
		public class VoteComparator implements Comparator<Recipe>{

			@Override
			public int compare(Recipe r1, Recipe r2) {
				int r1Votes = r1.getUpVote() - r1.getDownVote();
				int r2Votes = r2.getUpVote() - r2.getDownVote();
				
				if (r1Votes < r2Votes) {
					return 1;
				} else if (r1Votes > r2Votes) {
					return -1;
				} else return 0;
			}
			
		}
		
}
