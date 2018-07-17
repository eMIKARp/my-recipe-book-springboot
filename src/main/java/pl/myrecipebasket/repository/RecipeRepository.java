package pl.myrecipebasket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.myrecipebasket.model.Recipe;
import pl.myrecipebasket.model.User;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
		public List<Recipe> findAllByUsrWhoAddedRecipe(User user);
		public Recipe findById(Long id);
		public List<Recipe> findAllByIsShared(boolean isShared);
		
}
