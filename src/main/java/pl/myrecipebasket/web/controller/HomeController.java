package pl.myrecipebasket.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pl.myrecipebasket.model.Category;
import pl.myrecipebasket.model.Recipe;
import pl.myrecipebasket.model.Role;
import pl.myrecipebasket.model.User;
import pl.myrecipebasket.model.Vote;
import pl.myrecipebasket.service.CategoryService;
import pl.myrecipebasket.service.RecipeService;
import pl.myrecipebasket.service.UserService;

@Controller
public class HomeController {

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
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setRecipeService(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("categories", categoryService.getCategories());
		model.addAttribute("recipes", recipeService.getAllSharedRecipes());
		return "homepage";
	}
	
	@GetMapping("/homepage")
	public String homepage(Model model) {
		model.addAttribute("categories", categoryService.getCategories());
		model.addAttribute("recipes", recipeService.getAllSharedRecipes());
		return "homepage";
	}
	
	@GetMapping("/userpage")
	public String userpage(Model model) {
		User loggedUser = userService.findByUsername(getLoggedUserUsername());
		List<Recipe> recipesToShow = loggedUser.getFavRecipes();
		model.addAttribute("categories", categoryService.getCategories());
		model.addAttribute("recipes", recipesToShow);
		return "userpage";
	}
	
}
