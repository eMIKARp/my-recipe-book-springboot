package pl.myrecipebasket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.myrecipebasket.model.Comment;
import pl.myrecipebasket.model.Recipe;
import pl.myrecipebasket.repository.CommentRepository;
import pl.myrecipebasket.repository.RecipeRepository;

@Service
public class CommentService {

	private CommentRepository commentRepository;
	private RecipeRepository recipeRepository;
	
	@Autowired
	public void setCommentRepository(CommentRepository commentRepository) {
		this.commentRepository=commentRepository;
	}
	
	@Autowired
	public void setRecipeRepository(RecipeRepository recipeRepository) {
		this.recipeRepository=recipeRepository;
	}

	public void saveComment(Comment comment) {
		commentRepository.save(comment);
	}
	
	public List<Comment> getAllComments(){
		return commentRepository.findAll();
	} 
}
