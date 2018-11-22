package pl.myrecipebasket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.myrecipebasket.model.User;

/**
 * 
 * Object <b>{@code UserRepository}</b> provides access to database and 
 * ability to perform CRUD operations on {@code User} database table.
 *
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	/**
	 * This method finds in {@code User} database table a record with {@code email} field equal to one 
	 * provided as a parameter of this method and returns it as a {@code User} object.  
	 * 
	 * @param email email address of {@code User} that is being looked for
	 * @return {@code User} object
	 * 
	 * @see pl.myrecipebasket.model.User
	 * 
	 * 
	 */
	
	public User findByEmail(String email);
	
	/**
	 * This method finds in {@code User} database table a record with {@code username} field equal to one 
	 * provided as a parameter of this method and returns it as a {@code User} object.  
	 * 
	 * @param username name of {@code User} that is being looked for
	 * @return {@code User} object
	 * 
	 * @see pl.myrecipebasket.model.User
	 * 
	 */
	
	public User findByUsername(String username);
}
