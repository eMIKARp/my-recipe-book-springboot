package pl.myrecipebasket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.myrecipebasket.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	public Category findBycName(String categoryName);
}
