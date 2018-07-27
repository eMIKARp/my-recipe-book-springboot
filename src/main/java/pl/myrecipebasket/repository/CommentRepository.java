package pl.myrecipebasket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.myrecipebasket.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

}
