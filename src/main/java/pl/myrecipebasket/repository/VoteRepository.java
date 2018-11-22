package pl.myrecipebasket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.myrecipebasket.model.Recipe;
import pl.myrecipebasket.model.User;
import pl.myrecipebasket.model.Vote;

/**
 * 
 * Object <b>{@code VoteRepository}</b> provides access to database and 
 * ability to perform CRUD operations on {@code Vote} database table.
 *
 */

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
	
	
	/**
	 * This method finds in {@code Vote} database table records with {@code user} and {@code recipe} fields equal 
	 * to ones provided as a parameters of this method and returns it as a {@code Vote} object.  
	 * 
	 * @param user {@code User} whose {@code Votes} are being looked for
	 * @param recipe {@code Recipe} that the {@code Vote} is being looked for should concern
	 * @return {@code Vote} object
	 * 
	 * @see pl.myrecipebasket.model.Vote
	 * @see pl.myrecipebasket.model.User
	 * @see pl.myrecipebasket.model.Recipe
	 */
	
	public Vote findByUserAndRecipe(User user, Recipe recipe);
}
