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

/**
 * 
 * Object <b>{@code VoteService}</b> is a intermediate layer between 
 * persistence layer ({@code UserRepository} & {@code VoteRepository} & {@code RecipeRepository}) 
 * and view layer ({@code VoteController}). Contains all {@code Vote} related business 
 * logic of <em>MyRecipeBasket</em> application.
 * 
 * @see pl.myrecipebasket.repository.UserRepository
 * @see pl.myrecipebasket.repository.VoteRepository
 * @see pl.myrecipebasket.repository.RecipeRepository
 * @see pl.myrecipebasket.web.controller.VoteController
 * 
 */

@Service
public class VoteService {

	/**
	 * {@code UserRepository} field declaration 
	 */
	
	private UserRepository userRepository;
	
	/**
	 * {@code VoteRepository} field declaration 
	 */
	
	private VoteRepository voteRepository;
	
	/**
	 * {@code RecipeRepository} field declaration 
	 */
	
	private RecipeRepository recipeRepository;
	
	/**
	 * 
	 * This method injects {@code UserRepository} object into {@code VoteService}
	 * and enables {@code VoteService} to perform CRUD operations on {@code User}
	 * database table.
	 * 
	 * @param userRepository {@code UserRepository} object
	 */
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	/**
	 * 
	 * This method injects {@code VoteRepository} object into {@code VoteService}
	 * and enables {@code VoteService} to perform CRUD operations on {@code Vote}
	 * database table.
	 * 
	 * @param voteRepository {@code VoteRepository} object
	 */
	
	
	@Autowired
	public void setVoteRepository(VoteRepository voteRepository) {
		this.voteRepository=voteRepository;
	}
	
	/**
	 * 
	 * This method injects {@code RecipeRepository} object into {@code VoteService}
	 * and enables {@code VoteService} to perform CRUD operations on {@code Recipe}
	 * database table.
	 * 
	 * @param recipeRepository {@code RecipeRepository} object
	 */
	
	@Autowired
	public void setRecipeRepository(RecipeRepository recipeRepository) {
		this.recipeRepository=recipeRepository;
	}
	
	/**
	 * 
	 * This method retrieves single {@code Vote} object that had been left by 
	 * particular {@code User} and concerns specific {@code Recipe}
	 * 
	 * @param user {@code User} whose {@code Vote} is being looked for
	 * @param recipe {@code Recipe} which the {@code Vote} that is being 
	 * looked for should concern
	 * @return {@code Vote} object
	 * 
	 */
	
	public Vote getVoteByUserAndRecipe(User user, Recipe recipe) {
		Vote vote = voteRepository.findByUserAndRecipe(user, recipe);
		return vote;
	}
	
	/**
	 * 
	 * This method creates new {@code Vote} object and persists it
	 * in <em>MyRecipeBasket</em> database.
	 * 
	 * @param voteType type of {@code Vote} left by {@code User}
	 * @param recipe {@code Recipe} which the {@code Vote} concerns
	 * @param user {@code User} who left the {@code Vote}
	 */
	
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
	
	/**
	 * 
	 * This method updates {@code VoteType} of existing {@code Vote} and 
	 * makes corresponding changes in {@code UpVote} & {@code DownVote} fields 
	 * of the {@code Recipe} that the {@code Vote} considers. 
	 * 
	 * @param vote updated {@code Vote} left by {@code User}
	 * @param recipe {@code Recipe} witch the new {@code Vote} considers
	 * @param user {@code User} who updated {@code Vote}
	 */
	
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
	