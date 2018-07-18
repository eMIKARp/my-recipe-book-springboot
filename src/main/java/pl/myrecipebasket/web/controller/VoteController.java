package pl.myrecipebasket.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.myrecipebasket.model.Recipe;
import pl.myrecipebasket.model.User;
import pl.myrecipebasket.model.Vote;
import pl.myrecipebasket.model.VoteType;
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
	public String addVote(@RequestParam long recipeId, 
			@RequestParam String voteType, 
			Model model) {
		
		if (getLoggedUserUsername()!=null) {
			Recipe recipe = recipeService.getRecipeById(recipeId);
			User user = userService.findByUsername(getLoggedUserUsername());
			Vote vote = voteService.getVoteByUserAndRecipe(user, recipe);
			System.out.println(vote);
			if (vote!=null) {
				if (vote.getVoteType()!=VoteType.valueOf(voteType)) {
					vote.setVoteType(VoteType.valueOf(voteType));
					voteService.updateVote(vote, recipe, user);
				} else {
					System.out.println("Nie możesz oddać dwóch identycznych głosów na ten sam przepis");
				}
			} else {
				voteService.addVote(voteType, recipe, user);
			}
				
		} else {
			System.out.println("Musisz być zalogowany, żeby zagłosować");
		}
		
		return "redirect:homepage";
	}
}
