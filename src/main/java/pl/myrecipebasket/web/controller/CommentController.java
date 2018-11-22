	package pl.myrecipebasket.web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import pl.myrecipebasket.model.Comment;
import pl.myrecipebasket.model.Image;
import pl.myrecipebasket.model.Recipe;
import pl.myrecipebasket.model.User;
import pl.myrecipebasket.service.CommentService;
import pl.myrecipebasket.service.ImageService;
import pl.myrecipebasket.service.RecipeService;
import pl.myrecipebasket.service.UserService;

/**
 * 
 * Object <b>{@code CommentController}</b> is responsible for displaying 
 * {@code comment.html} page when requested by {@code User}, receiving {@code Comment}
 * related data provided by {@code User} via {@code comment.html} form and
 * persist this data in <em>MyRecipeBasket</em> database using appropriate services.
 * 
 * @see pl.myrecipebasket.model.User
 * @see pl.myrecipebasket.model.Comment
 * 
 *
 */

@Controller
public class CommentController {

	/**
	 * {@code UserService} field declaration
	 */
	
	private UserService userService;
	
	/**
	 * {@code RecipeService} field declaration
	 */
	
	private RecipeService recipeService;
	
	/**
	 * {@code CommentService} field declaration
	 */
	
	private CommentService commentService;
	
	/**
	 * {@code ImageService} field declaration
	 */
	
	private ImageService imageService;
	
	/**
	 * {@code Authentication} field declaration
	 */
	
	private Authentication authentication;
	
	/**
	 * {@code loggedUserUsername} field declaration 
	 */
	
	
	private String loggedUserUsername;
	
	/**
	 * {@code uploadDirectory} field declaration
	 */
	
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/static/img";
	
	/**
	 * This method injects {@code RecipeService} object into {@code CommentController}
	 * and enables {@code CommentController} to have access to all {@code Recipe} related 
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
	 * This method injects {@code UserService} object into {@code CommentController}
	 * and enables {@code CommentController} to have access to all {@code User} related 
	 * business logic of <em>MyRecipeBasket</em> application
	 * 
	 * @param UserService {@code UserService} object
	 * 
	 * @see pl.myrecipebasket.model.User
	 * 
	 */
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * This method injects {@code CommentService} object into {@code CommentController}
	 * and enables {@code CommentController} to have access to all {@code Comment} related 
	 * business logic of <em>MyRecipeBasket</em> application
	 * 
	 * @param CommentService {@code CommentService} object
	 * 
	 * @see pl.myrecipebasket.model.Comment
	 * 
	 */

	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	/**
	 * This method injects {@code ImageService} object into {@code CommentController}
	 * and enables {@code CommentController} to have access to all {@code Image} related 
	 * business logic of <em>MyRecipeBasket</em> application
	 * 
	 * @param ImageService {@code ImageService} object
	 * 
	 * @see pl.myrecipebasket.model.Image
	 * 
	 */
	
	@Autowired
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
	
	/**
	 * This method provides logged {@code User} name
	 */
	
	public String getLoggedUserUsername() {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		loggedUserUsername=authentication.getName();
		return loggedUserUsername;
	}
	
	/**
	 * This method displays @{code comment.html} page after {@code User} sends 
	 * a {@code GET} request to URI ("/comment")
	 * 
	 * @param recipeId an Id of {@code Recipe} which the {@code Comment} considers
	 * @param model an object which stores and transfers attributes between controllers and html pages
	 * @return {@code comment.html} page 
	 * 
	 * @see pl.myrecipebasket.model.User
	 * @see pl.myrecipebasket.model.Comment
	 * @see pl.myrecipebasket.model.Recipe
	 */
	
	@GetMapping("/comment")
	public String leaveComment(@RequestParam Long recipeId, Model model) {
		
		model.addAttribute("comment", new Comment());
		model.addAttribute("recipeId",recipeId);
		
		return "comment";
	}
	
	/**
	 * 
	 * This method receives {@code Comment} related data provided by {@code User} 
	 * via {@code comment.html} form and persist this data in <em>MyRecipeBasket</em>
	 * database using appropriate services.
	 * 
	 * @param recipeId an Id of {@code Recipe} which the {@code Comment} considers
	 * @param comment a {@code Comment} object 
	 * @param files {@code Image} files that were uploaded by {@code User} 
	 * @param model an object which stores and transfers attributes between controllers and html pages
	 * @return {@code homepage.html} page
	 * 
	 * @see pl.myrecipebasket.model.User
	 * @see pl.myrecipebasket.model.Comment
	 * @see pl.myrecipebasket.model.Recipe
	 */
	
	@PostMapping("/comment")
	public String submitComment(@RequestParam Long recipeId, @ModelAttribute Comment comment, @RequestParam("files") MultipartFile[] files, Model model) {
		
		Recipe recipeWithComment = recipeService.getRecipeById(recipeId);
		User userWhoAddedComment;
		
		if (userService.findByUsername(getLoggedUserUsername())!=null) {
			userWhoAddedComment = userService.findByUsername(getLoggedUserUsername());
		} else {
			userWhoAddedComment = userService.findByUsername("Unregistered User");
		}
		
		comment.setDate(new Timestamp(new Date().getTime()));
		
		StringBuilder fileNames = new StringBuilder();
		for (MultipartFile file: files) {
			Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
			comment.addImage(new Image(file.getOriginalFilename(), uploadDirectory, comment));
			fileNames.append(file.getOriginalFilename());
			try {
				Files.write(fileNameAndPath, file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		comment = userWhoAddedComment.addComment(comment);;
		comment = recipeWithComment.addComment(comment);
		
		userService.saveUser(userWhoAddedComment);
		recipeService.saveRecipe(recipeWithComment);
		commentService.saveComment(comment);
		
		
		return "redirect:homepage";
	}
}
