package pl.myrecipebasket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.myrecipebasket.model.Comment;

/**
 * 
 * Object <b>{@code CommentRepository}</b> provides access to database and 
 * ability to perform CRUD operations on {@code Comment} database table.
 *
 */

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

}
