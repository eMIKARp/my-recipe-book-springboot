package pl.myrecipebasket.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.myrecipebasket.model.Recipe;
import pl.myrecipebasket.model.User;
import pl.myrecipebasket.model.Vote;
import pl.myrecipebasket.model.VoteType;
import pl.myrecipebasket.repository.RecipeRepository;
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
	
	public Vote getVoteByUserAndRecipe(User user, Recipe recipe) {
		Vote vote = voteRepository.findByUserAndRecipe(user, recipe);
		return vote;
	}
	
	public void addVote(String voteType, Recipe recipe, User user) {
		
		Vote vote = new Vote(user, recipe, VoteType.valueOf(voteType));
		vote.setDate(new Timestamp(new Date().getTime()));
		
		user.getVotes().add(vote);
		recipe.getVotes().add(vote);
		if (voteType.equals("VOTE_UP")) {
			recipe.setUpVote(recipe.getUpVote()+1);
		} else {
			recipe.setDownVote(recipe.getDownVote()+1);
		}
		
		userRepository.save(user);
		recipeRepository.save(recipe);
		voteRepository.save(vote);
				
	}
	
	public void updateVote(Vote vote, Recipe recipe, User user) {
		
		if (vote.getVoteType().equals(VoteType.valueOf("VOTE_UP"))) {
			recipe.setUpVote(recipe.getUpVote()+1);
			recipe.setDownVote(recipe.getDownVote()-1);
			
		} else {
			recipe.setUpVote(recipe.getUpVote()-1);
			recipe.setDownVote(recipe.getDownVote()+1);
		}
		recipeRepository.save(recipe);
		voteRepository.save(vote);
	}
}
	