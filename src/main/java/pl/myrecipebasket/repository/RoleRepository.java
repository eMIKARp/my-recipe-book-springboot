package pl.myrecipebasket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.myrecipebasket.model.Role;

/**
 * 
 * Object <b>{@code RoleRepository}</b> provides access to database and 
 * ability to perform CRUD operations on {@code Role} database table.
 *
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	/**
	 * This method finds in {@code Role} database table a record with {@code role} field equal to one 
	 * provided as a parameter of this method and returns it as a {@code Role} object.  
	 * 
	 * @param role name of {@code Role} that is being looked for
	 * @return {@code Role} object
	 * 
	 * @see pl.myrecipebasket.model.Role
	 * 
	 * 
	 */
	
	public Role findByRole(String role);
}
