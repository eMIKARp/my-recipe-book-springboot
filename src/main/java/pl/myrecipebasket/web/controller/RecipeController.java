package pl.myrecipebasket.web.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pl.myrecipebasket.model.Category;
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
	public void setREcipeService(RecipeService recipeService) {
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
			
		recipeService.saveRecipe(recipeToAdd);
		return "redirect:recipeadded";
	}
	
	@GetMapping("/recipeadded")
	public String addRecipeSuccess(Model model) {
		return "recipeadded";
	}
	
}
