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

/**
 * 
 * Object <b>{@code RecipeService}</b> is a intermediate layer between 
 * persistence layer ({@code RecipeRepository} & {@code UserRepository} &
 * {@code CategoryRepository}) and view layer ({@code CommentController} &
 * {@code HomeController} & {@code RecipeController} & {@code VoteController}). 
 * Contains all {@code Recipe} related business logic of 
 * <em>MyRecipeBasket</em> application.
 * 
 * @see pl.myrecipebasket.repository.RecipeRepository
 * @see pl.myrecipebasket.repository.UserRepository
 * @see pl.myrecipebasket.repository.CategoryRepository
 * @see pl.myrecipebasket.web.controller.CommentController
 * @see pl.myrecipebasket.web.controller.HomeController
 * @see pl.myrecipebasket.web.controller.RecipeController
 * @see pl.myrecipebasket.web.controller.VoteController
 * 
 */

@Service
public class RecipeService {
		
		/**
		 * {@code RecipeRepository} field declaration
		 */
	
		RecipeRepository recipeRepository;
		
		/**
		 * {@code UserRepository} field declaration
		 */
		
		UserRepository userRepository;
		
		/**
		 * {@code CategoryRepository} field declaration
		 */
		
		CategoryRepository categoryRepository;
		
		/**
		 * This method injects {@code RecipeRepository} object into {@code RecipeService}
		 * and enables {@code RecipeService} to perform CRUD operations on {@code Recipe}
		 * database table.
		 * 
		 * @param recipeRepository {@code RecipeRepository} object
		 */
		
		@Autowired
		public void setRecipeRepository(RecipeRepository recipeRepository) {
			this.recipeRepository=recipeRepository;
		}
		
		/**
		 * This method injects {@code UserRepository} object into {@code RecipeService}
		 * and enables {@code RecipeService} to perform CRUD operations on {@code User}
		 * database table.
		 * 
		 * @param userRepository {@code UserRepository} object
		 */
		
		@Autowired
		public void setUserRepository(UserRepository userRepository) {
			this.userRepository=userRepository;
		}
		
		/**
		 * This method injects {@code CategortRepository} object into {@code RecipeService}
		 * and enables {@code RecipeService} to perform CRUD operations on {@code Category}
		 * database table.
		 * 
		 * @param categoryRepository {@code CategoryRepository} object
		 */
		
		@Autowired
		public void setCategoryRepository(CategoryRepository categoryRepository) {
			this.categoryRepository=categoryRepository;
		}
		
		/**
		 *
		 * This method persists {@code Recipe} object in <em>MyRecipeBasket</em> database
		 * 
		 * @param recipe {@code Recipe} object
		 */
		
		public void saveRecipe(Recipe recipe) {
			recipeRepository.save(recipe);
		}
		
		/**
		 * 
		 * This method retrieves from <em>MyRecipeBasket</em> database all 
		 * {@code Recipes} added by {@code User} provided as parameter of this method. 
		 * 
		 * @param user {@code User} whose {@code Recipes} are being looked for
		 * @return a list of {@code Recipe} objects
		 * 
		 */
	
		public List<Recipe> getRecipesAddedByUser(User user){
			return recipeRepository.findAllByUsrWhoAddedRecipe(user);
		}
		
		/**
		 * 
		 * This method retrieves from <em>MyRecipeBasket</em> database 
		 * single {@code Recipe} object which id property equals to one provided as
		 * parameter of this method.
		 * 
		 * @param id id property of {@code Recipe} object that is being looked for
		 * @return {@code Recipe} object
		 * 
		 */
		
		public Recipe getRecipeById(Long id) {
			return recipeRepository.findById(id);
		}
		
		/**
		 * 
		 * This method retrieves from <em>MyRecipeBasket</em> database all 
		 * shared {@code Recipes}. 
		 * 
		 * @return a list of {@code Recipe} objects
		 * 
		 */
		
		public List<Recipe> getAllSharedRecipes(){
			return recipeRepository.findAllByIsShared(true);
		}

		/**
		 * 
		 * This method retrieves from <em>MyRecipeBasket</em> database all 
		 * shared {@code Recipes} that belong to {@code Category} which name 
		 * equals to one provided as parameter of this method. 
		 * 
		 * @param cName name of {@code Category} within which shared {@code Recipes} 
		 * are being looked for
		 * @return a list of {@code Recipe} objects
		 */
		
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
		
		/**
		 *
		 * This method retrieves from <em>MyRecipeBasket</em> database all {@code Recipes} 
		 * that belong to {@code Category} which name equals to one provided as parameter
		 * to this method, and were added by particular {@code User} who also was 
		 * a parameter of this method. 
		 * 
		 * @param cName name of {@code Category} within which {@code Recipes} are being looked for
		 * @param user {@code User} whose {@code Recipes} are being looked for
		 * @return a list of {@code Recipe} objects
		 */
		
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
		
		/**
		 * This method removes {@code Recipe} from <em>MyRecipeBasket</em> database. 
		 * @param recipe {@code Recipe} object that is to be removed
		 */
		
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
		
		/**
		 *	Object <b>{@code VoyeComparator}</b> holds a comparison function that imposes
		 *	order in which two {@code Recipe} object should be sorted.
		 *
		 */
		
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
