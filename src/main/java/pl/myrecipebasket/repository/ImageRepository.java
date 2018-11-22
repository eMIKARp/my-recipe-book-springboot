package pl.myrecipebasket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.myrecipebasket.model.Image;

/**
 * 
 * Object <b>{@code ImageRepository}</b> provides access to database and 
 * ability to perform CRUD operations on {@code Image} database table.
 *
 */

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
