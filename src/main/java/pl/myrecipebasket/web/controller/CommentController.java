package pl.myrecipebasket.web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

@Controller
public class CommentController {

	private UserService userService;
	private RecipeService recipeService;
	private CommentService commentService;
	private ImageService imageService;
	
	
	private Authentication authentication;
	private String loggedUserUsername;
	
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/static/img";
	
	@Autowired
	public void setRecipeService(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	@Autowired
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
	
	public String getLoggedUserUsername() {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		loggedUserUsername=authentication.getName();
		return loggedUserUsername;
	}
	
	
	@GetMapping("/comment")
	public String leaveComment(@RequestParam Long recipeId, Model model) {
		
		model.addAttribute("comment", new Comment());
		model.addAttribute("recipeId",recipeId);
		
		return "comment";
	}
	
	@PostMapping("/comment")
	public String submitComment(@RequestParam Long recipeId,@ModelAttribute Comment comment, @RequestParam("files") MultipartFile[] files, Model model) {
		
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
