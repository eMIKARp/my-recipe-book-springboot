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

/**
 * 
 * Object <b>{@code HomeController}</b> is responsible for displaying 
 * {@code homepage.html} and {@code userpage.html} page when requested by {@code User} 
 * and populating them with {@code Category}, {@code Recipe}, {@code Comment} related 
 * data stored in <em>MyRecipeBasket</em> database.
 * 
 * @see pl.myrecipebasket.model.User
 * @see pl.myrecipebasket.model.Category
 * @see pl.myrecipebasket.model.Recipe
 * @see pl.myrecipebasket.model.Comment
 * 
 *
 */

@Controller
public class HomeController {

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
	 * {@code CommentService} field declaration
	 */
	
	CommentService commentService;
	
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
	 * This method injects {@code CategoryService} object into {@code HomeController}
	 * and enables {@code HomeController} to have access to all {@code Category} related 
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
	 * This method injects {@code CommentService} object into {@code HomeController}
	 * and enables {@code HomeController} to have access to all {@code Comment} related 
	 * business logic of <em>MyRecipeBasket</em> application
	 * 
	 * @param CommentService - {@code CommentService} object
	 * 
	 * @see pl.myrecipebasket.model.Comment
	 * 
	 */
	
	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
	/**
	 * This method injects {@code UserService} object into {@code HomeController}
	 * and enables {@code HomeController} to have access to all {@code User} related 
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
	 * This method injects {@code RecipeService} object into {@code HomeController}
	 * and enables {@code HomeController} to have access to all {@code Recipe} related 
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
	 * 
	 * This method displays {@code homepage.html} page when requested by {@code User} 
	 * and populates it with {@code Category}, {@code Recipe}, {@code Comment} related 
	 * data stored in <em>MyRecipeBasket</em> database.
	 * 
	 * @param cName one of {@code Category} fields 
	 * @param model an object which stores and transfers attributes between controllers and html pages
	 * @return {@code homepage.html} page
	 * 
	 * @see pl.myrecipebasket.model.User
	 * @see pl.myrecipebasket.model.Category
	 * @see pl.myrecipebasket.model.Recipe
	 * @see pl.myrecipebasket.model.Comment
	 * 
	 */
	
	@GetMapping("/")
	public String home(@RequestParam(value="cName",required=false,defaultValue="all") String cName, Model model) {
		model.addAttribute("categories", categoryService.getCategories());
		model.addAttribute("recipes", recipeService.getAllSharedRecipesWithinCategory(cName));
		model.addAttribute("comments",commentService.getAllComments());
		return "homepage";
	}
	
	/**
	 * 
	 * This method displays {@code homepage.html} page when requested by {@code User} 
	 * and populates it with {@code Category}, {@code Recipe}, {@code Comment} related 
	 * data stored in <em>MyRecipeBasket</em> database.
	 * 
	 * @param cName one of {@code Category} fields 
	 * @param model an object which stores and transfers attributes between controllers and html pages
	 * @return {@code homepage.html} page
	 * 
	 * @see pl.myrecipebasket.model.User
	 * @see pl.myrecipebasket.model.Category
	 * @see pl.myrecipebasket.model.Recipe
	 * @see pl.myrecipebasket.model.Comment
	 * 
	 */
	
	@GetMapping("/homepage")
	public String homepage(@RequestParam(value="cName",required=false,defaultValue="all") String cName,Model model) {
		model.addAttribute("categories", categoryService.getCategories());
		model.addAttribute("recipes", recipeService.getAllSharedRecipesWithinCategory(cName));
		model.addAttribute("comments",commentService.getAllComments());
		return "homepage";
	}
	
	/**
	 * 
	 * This method displays {@code userpage.html} page when requested by {@code User} 
	 * and populates it with {@code Category}, {@code Recipe} related 
	 * data stored in <em>MyRecipeBasket</em> database.
	 * 
	 * @param cName one of {@code Category} fields 
	 * @param model an object which stores and transfers attributes between controllers and html pages
	 * @return {@code userpage.html} page
	 * 
	 * @see pl.myrecipebasket.model.User
	 * @see pl.myrecipebasket.model.Category
	 * @see pl.myrecipebasket.model.Recipe
	 * 
	 */
	
	@GetMapping("/userpage")
	public String userpage(@RequestParam(value="cName",required=false,defaultValue="all") String cName,Model model) {
		User loggedUser = userService.findByUsername(getLoggedUserUsername());
		model.addAttribute("categories", categoryService.getCategories());
		model.addAttribute("recipes", recipeService.getAllUsersRecipesWhithinCategory(cName, loggedUser));
		return "userpage";
	}
	
}
