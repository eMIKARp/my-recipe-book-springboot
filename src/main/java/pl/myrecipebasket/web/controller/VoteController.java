package pl.myrecipebasket.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.myrecipebasket.service.RecipeService;
import pl.myrecipebasket.service.UserService;
import pl.myrecipebasket.service.VoteService;

@Controller
public class VoteController {
	
	private UserService userService;
	private RecipeService recipeService;
	private VoteService voteService;
	
	Authentication authentication;
	String loggedUserUsername;

	@Autowired
	public void setRecipeService(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setVoteService(VoteService voteService) {
		this.voteService = voteService;
	}
	
	public String getLoggedUserUsername() {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		loggedUserUsername=authentication.getName();
		return loggedUserUsername;
	}
	
	@GetMapping("/vote")
	public String addVote(@RequestParam long recipeId, @RequestParam String voteType, Model model) {
		
		return "redirect:homepage";
	}
}
