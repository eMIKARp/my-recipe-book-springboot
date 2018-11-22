package pl.myrecipebasket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.myrecipebasket.model.Category;

/**
 * 
 * Object <b>{@code CategoryRepository}</b> provides access to database and 
 * ability to perform CRUD operations on {@code Category} database table.
 *
 */

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	/**
	 * This method finds in {@code Category} database table a record with {@code name} equal to one 
	 * provided as a parameter of this method and returns it as a {@code Category} object.  
	 * 
	 * @param categoryName name of {@code Category} that is being looked for 
	 * @return {@code Category} object
	 * 
	 * @see pl.myrecipebasket.model.Category
	 */
	
	public Category findBycName(String categoryName);
}
