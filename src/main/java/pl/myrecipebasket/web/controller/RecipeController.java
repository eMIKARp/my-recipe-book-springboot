package pl.myrecipebasket.web.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.myrecipebasket.model.Recipe;
import pl.myrecipebasket.model.User;
import pl.myrecipebasket.service.CategoryService;
import pl.myrecipebasket.service.RecipeService;
import pl.myrecipebasket.service.UserService;

/**
 * Object <b>{@code RecipeController}</b> is responsible for handling all 
 * {@code Recipe} related requests. Its main task is to receive input from {@code User}, 
 * process it using proper Services and display response by invoking appropriate
 * html.page.
 * 
 * @see pl.myrecipebasket.model.Recipe
 * @see pl.myrecipebasket.model.User
 *
 */

@Controller
public class RecipeController {

	/**
	 * {@code CategoryService} field declaration 
	 */
	
	CategoryService categoryService;
	
	/**
	 * {@code RecipeService} field declaration 
	 */
	
	RecipeService recipeService;
	
	/**
	 * {@code UserService} field declaration 
	 */
	
	UserService userService;
	
	/**
	 * {@code Authentication} field declaration
	 */
	
	Authentication authentication;
	
	/**
	 * {@code loggedUserUsername} field declaration 
	 */
	
	String loggedUserUsername;
	
	/**
	 * This method return logged {@code User} name
	 * @return loggedUserUsername; 
	 */
	
	
	public String getLoggedUserUsername() {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		loggedUserUsername=authentication.getName();
		return loggedUserUsername;
	}

	/**
	 * This method injects {@code UserService} object into {@code RecipeController}
	 * and enables {@code RecipeController} to have access to all {@code User} related 
	 * business logic of <em>MyRecipeBasket</em> application
	 * 
	 * @param UserService - {@code UserService} object
	 * 
	 * @see pl.myrecipebasket.model.User
	 * 
	 */
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * This method injects {@code CategoryService} object into {@code RecipeController}
	 * and enables {@code RecipeController} to have access to all {@code Category} related 
	 * business logic of <em>MyRecipeBasket</em> application
	 * 
	 * @param CategoryService - {@code CategoryService} object
	 * 
	 * @see pl.myrecipebasket.model.Category
	 * 
	 */
	
	@Autowired
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	/**
	 * This method injects {@code RecipeService} object into {@code RecipeController}
	 * and enables {@code RecipeController} to have access to all {@code Recipe} related 
	 * business logic of <em>MyRecipeBasket</em> application
	 * 
	 * @param RecipeService - {@code RecipeService} object
	 * 
	 * @see pl.myrecipebasket.model.Recipe
	 * 
	 */
	
	@Autowired
	public void setRecipeService(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	/**
	 * This method serves {@code add.html} page as a response to {@code GET} request send 
	 * by {@code User} to URI ("/add")
	 *  
	 * @param model an object which stores and transfers attributes between controllers and html pages
	 * @return {@code add.html} page
	 */
	
	@GetMapping("/add")
	public String addRecipe(Model model) {
		
		model.addAttribute("recipe", new Recipe());
		model.addAttribute("catToChooseFrom", categoryService.getCategories());
		
		return "add";
	}
	
	/**
	 * This method receives {@code Recipe} related data provided by {@code User} 
	 * via {@code add.html} form and persist this data in <em>MyRecipeBasket</em>
	 * database using appropriate services.
	 * 
	 * @param recipe {@code Recipe} that {@code User} wants to add 
	 * @param model an object which stores and transfers attributes between controllers and html pages
	 * @return {@code recipeadded.html} page
	 */
	
	@PostMapping("/add")
	public String submitRecipe(@ModelAttribute Recipe recipe, Model model) {
		
			Recipe recipeToAdd = new Recipe();
			recipeToAdd.setrName(recipe.getrName());
			recipeToAdd.setrDescription(recipe.getrDescription());
			recipeToAdd.setrUrl(recipe.getrUrl());
			recipeToAdd.setDate(new Timestamp(new Date().getTime()));
			
			User usrWhoAddedRecipe = userService.findByUsername(getLoggedUserUsername());
			recipeToAdd.setUsrWhoAddedRecipe(usrWhoAddedRecipe);
			recipeToAdd.setrCategories(recipe.getrCategories());
			
			List<Recipe> ownRecipes = usrWhoAddedRecipe.getOwnRecipes();
			List<Recipe> favRecipes = usrWhoAddedRecipe.getFavRecipes();
			ownRecipes.add(recipeToAdd);
			favRecipes.add(recipeToAdd);
			usrWhoAddedRecipe.setOwnRecipes(ownRecipes);
			usrWhoAddedRecipe.setFavRecipes(favRecipes);
			
		recipeService.saveRecipe(recipeToAdd);
		userService.saveUser(usrWhoAddedRecipe);
		return "redirect:recipeadded";
	}
	
	/**
	 * This method serves {@code recipeadded.html} page as a response to {@code GET} request send 
	 * by {@code User} to URI ("/recipeadded")
	 *  
	 * @param model an object which stores and transfers attributes between controllers and html pages
	 * @return {@code recipeadded.html} page
	 */
	
	
	@GetMapping("/recipeadded")
	public String addRecipeSuccess(Model model) {
		return "recipeadded";
	}
	
	/**
	 * This method changes changes {@code isShared} field of {@code Recipe} that
	 * have not been previously shared by {@code User} who created it
	 * 
	 * @param recipeId {@code Recipe} which {@code User} wants to share with other {@code Users}
	 * @param model an object which stores and transfers attributes between controllers and html pages
	 * @return {@code recipeshared.html} page
	 */
	
	@GetMapping("/share")
	public String shareRecipe(@RequestParam long recipeId, Model model) {
		Recipe recipe = recipeService.getRecipeById(recipeId);
		if (recipe.isShared()!=true) {
			recipe.setShared(true);
			recipeService.saveRecipe(recipe);
		}
		return "redirect:recipeshared";
	}
	
	/**
	 * 
	 * This method adds {@code Recipe} to currently logged {@code Users} list of favorite 
	 * {@code Recipes}   
	 * 
	 * @param recipeId {@code Recipe} which {@code User} wants to add to favorites
	 * @param model an object which stores and transfers attributes between controllers and html pages 
	 * @return {@code homepage.html} page
	 * 
	 */
	
	@RequestMapping("/favourite")
	public String addRecipeToFav(@RequestParam long recipeId, Model model) {
		Recipe recipe = recipeService.getRecipeById(recipeId);
		User usrWhoAddedRecipeToFav = userService.findByUsername(getLoggedUserUsername());
		
		if (usrWhoAddedRecipeToFav!=null&&
				usrWhoAddedRecipeToFav.getId()!=recipe.getUsrWhoAddedRecipe().getId()&&
				!usrWhoAddedRecipeToFav.getFavRecipes().contains(recipe)){
					usrWhoAddedRecipeToFav.getFavRecipes().add(recipe);
					userService.saveUser(usrWhoAddedRecipeToFav);
				}
				
		return "redirect:homepage";
	}
	
	/**
	 * 
	 * This method removes {@code Recipe} from currently logged {@code Users} list of favorite 
	 * {@code Recipes}   
	 * 
	 * @param recipeId {@code Recipe} which {@code User} wants to remove from favorites
	 * @param model an object which stores and transfers attributes between controllers and html pages 
	 * @return {@code reciperemoved.html} page
	 */
	
	@GetMapping("/remove")
	public String removeRecipe(@RequestParam long recipeId, Model model) {
		
		Recipe recipe = recipeService.getRecipeById(recipeId);
		User loggedUser = userService.findByUsername(getLoggedUserUsername());

		if (loggedUser!=null) {
			loggedUser.getFavRecipes().remove(recipe);
			userService.saveUser(loggedUser);
		}
	   	
		return "redirect:reciperemoved";
	}
	
	/**
	 * This method serves {@code reciperemoved.html} page as a response to {@code GET} 
	 * request send by {@code User} to URI ("/reciperemoved")
	 * 
	 * @return {@code reciperemoved.html} page
	 */
	
	@GetMapping("/reciperemoved")
	public String recipeRemoved() {
		return "reciperemoved";
	}
	
	/**
	 * This method serves {@code recipeshared.html} page as a response to {@code GET} 
	 * request send by {@code User} to URI ("/recipeshared")
	 * 
	 * @return {@code reciperemoved.html} page
	 */
	
	@GetMapping("/recipeshared")
	public String recipeShared() {
		return "recipeshared";
	}
	
	/**
	 * This method serves {@code modifyforbidden.html} page as a response to {@code GET} 
	 * request send by {@code User} to URI ("/modifyforbidden")
	 * 
	 * @return {@code reciperemoved.html} page
	 */
	
	@GetMapping("/modifyforbidden")
	public String forbidModify() {
		return "modifyforbidden";
	}
	
	/**
	 * This method serves {@code modify.html} page or {@code modifyforbidden.html} as a 
	 * response to {@code GET} request send to URI ("/modify") 
	 * 
	 * @param recipeId {@code Recipe} which {@code User} wants to modify
	 * @param model an object which stores and transfers attributes between controllers and html pages
	 * @return {@code modify.html} page or {@code modifyforbidden.html} page
	 */
	
	@GetMapping("/modify")
	public String modifyRecipe(@RequestParam long recipeId, Model model) {
		Recipe recipe = recipeService.getRecipeById(recipeId);
		if (recipe.getUsrWhoAddedRecipe().getUsername().equals(getLoggedUserUsername())) {

			model.addAttribute("recipe", recipe);
			model.addAttribute("catToChooseFrom", categoryService.getCategories());
			
			return "modify";
			
		} else {
			return "modifyforbidden";
		}
		
	}
	
	/**
	 * This method modifies existing {@code Recipe}
	 * 
	 * @param recipe a {@code Recipe} object which holds details of change to be implemented 
	 * @param recipeId an id of {@code Recipe} that {@code User} wants to modify
	 * @param model an object which stores and transfers attributes between controller and html.pages
	 * @return {@code userpage.html} page
	 */
	
	@PostMapping("/modify")
	public String modifyRecipe(@ModelAttribute Recipe recipe, @RequestParam long recipeId, Model model) {
		
		Recipe recipeToModify = recipeService.getRecipeById(recipeId);
		recipeToModify.setDate(new Timestamp(new Date().getTime()));
		recipeToModify.setrName(recipe.getrName());
		recipeToModify.setrUrl(recipe.getrUrl());
		recipeToModify.setrDescription(recipe.getrDescription());
		recipeToModify.setrCategories(recipe.getrCategories());
		recipeService.saveRecipe(recipeToModify);
		
		return "redirect:userpage";
	}
	
}
