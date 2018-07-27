package pl.myrecipebasket.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.myrecipebasket.model.User;
import pl.myrecipebasket.service.CategoryService;
import pl.myrecipebasket.service.CommentService;
import pl.myrecipebasket.service.RecipeService;
import pl.myrecipebasket.service.UserService;

@Controller
public class HomeController {

	CategoryService categoryService;
	RecipeService recipeService;
	UserService userService;
	CommentService commentService;
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
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
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
	public String home(@RequestParam(value="cName",required=false,defaultValue="all") String cName, Model model) {
		model.addAttribute("categories", categoryService.getCategories());
		model.addAttribute("recipes", recipeService.getAllSharedRecipesWithinCategory(cName));
		model.addAttribute("comments",commentService.getAllComments());
		return "homepage";
	}
	
	@GetMapping("/homepage")
	public String homepage(@RequestParam(value="cName",required=false,defaultValue="all") String cName,Model model) {
		model.addAttribute("categories", categoryService.getCategories());
		model.addAttribute("recipes", recipeService.getAllSharedRecipesWithinCategory(cName));
		model.addAttribute("comments",commentService.getAllComments());
		return "homepage";
	}
	
	@GetMapping("/userpage")
	public String userpage(@RequestParam(value="cName",required=false,defaultValue="all") String cName,Model model) {
		User loggedUser = userService.findByUsername(getLoggedUserUsername());
		model.addAttribute("categories", categoryService.getCategories());
		model.addAttribute("recipes", recipeService.getAllUsersRecipesWhithinCategory(cName, loggedUser));
		return "userpage";
	}
	
}
