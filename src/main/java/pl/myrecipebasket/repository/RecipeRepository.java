package pl.myrecipebasket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.myrecipebasket.model.Recipe;
import pl.myrecipebasket.model.User;

/**
 * 
 * Object <b>{@code RecipeRepository}</b> provides access to database and 
 * ability to perform CRUD operations on {@code Recipe} database table.
 *
 */

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
		
	/**
	 * This method finds in {@code Recipe} database table records with {@code usrWhoAddedRecipe} field equal to one 
	 * provided as a parameter of this method and returns them as a list of {@code Recipe} objects.  
	 * 
	 * @param user {@code User} whose {@code Recipes} are being looked for 
	 * @return a list of {@code Recipe} objects
	 * 
	 * @see pl.myrecipebasket.model.User
	 * @see pl.myrecipebasket.model.Recipe
	 */
	
	public List<Recipe> findAllByUsrWhoAddedRecipe(User user);
	
	/**
	 * This method finds in {@code Recipe} database table a record with {@code id} field equal to one 
	 * provided as a parameter of this method and returns it as a {@code Recipe} object.  
	 * 
	 * @param id an id of {@code Recipe} that is being looked for
	 * @return {@code Recipe} object
	 * 
	 * @see pl.myrecipebasket.model.Recipe
	 * 
	 */
	
	
	public Recipe findById(Long id);
	
	/**
	 * This method finds in {@code Recipe} database table records with {@code isShared} field equal to the value 
	 * provided as a parameter of this method and returns them as a list of {@code Recipe} objects.  
	 * 
	 * @param isShared boolean equivalent of shared or not shared value
	 * @return a list of {@code Recipe} objects
	 * 
	 * @see pl.myrecipebasket.model.Recipe
	 * 
	 */
	public List<Recipe> findAllByIsShared(boolean isShared);
		
}
