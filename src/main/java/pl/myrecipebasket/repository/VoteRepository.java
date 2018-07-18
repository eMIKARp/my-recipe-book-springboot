package pl.myrecipebasket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.myrecipebasket.model.Recipe;
import pl.myrecipebasket.model.User;
import pl.myrecipebasket.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
	public Vote findByUserAndRecipe(User user, Recipe recipe);
}
