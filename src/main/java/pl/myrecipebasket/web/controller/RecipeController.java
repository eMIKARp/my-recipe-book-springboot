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

@Controller
public class RecipeController {

	CategoryService categoryService;
	RecipeService recipeService;
	UserService userService;
	Authentication authentication;
	String loggedUserUsername;
	
	public String getLoggedUserUsername() {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		loggedUserUsername=authentication.getName();
		return loggedUserUsername;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@Autowired
	public void setRecipeService(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@GetMapping("/add")
	public String addRecipe(Model model) {
		
		model.addAttribute("recipe", new Recipe());
		model.addAttribute("catToChooseFrom", categoryService.getCategories());
		
		return "add";
	}
	
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
	
	@GetMapping("/recipeadded")
	public String addRecipeSuccess(Model model) {
		return "recipeadded";
	}
	
	@GetMapping("/share")
	public String shareRecipe(@RequestParam long recipeId, Model model) {
		Recipe recipe = recipeService.getRecipeById(recipeId);
		if (recipe.isShared()!=true) {
			recipe.setShared(true);
			recipeService.saveRecipe(recipe);
		}
		return "redirect:recipeshared";
	}
	
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
	
	@GetMapping("/reciperemoved")
	public String recipeRemoved() {
		return "reciperemoved";
	}
	
	@GetMapping("/recipeshared")
	public String recipeShared() {
		return "recipeshared";
	}
	
	@GetMapping("/modifyforbidden")
	public String forbidModify() {
		return "modifyforbidden";
	}
	
	
	
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
