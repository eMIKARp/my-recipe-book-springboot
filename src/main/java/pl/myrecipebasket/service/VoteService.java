package pl.myrecipebasket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.myrecipebasket.repository.RecipeRepository;
import pl.myrecipebasket.repository.RoleRepository;
import pl.myrecipebasket.repository.UserRepository;
import pl.myrecipebasket.repository.VoteRepository;

@Service
public class VoteService {

	private UserRepository userRepository;
	private VoteRepository voteRepository;
	private RecipeRepository recipeRepository;
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	@Autowired
	public void setVoteRepository(VoteRepository voteRepository) {
		this.voteRepository=voteRepository;
	}
	
	@Autowired
	public void setRecipeRepository(RecipeRepository recipeRepository) {
		this.recipeRepository=recipeRepository;
	}
}
