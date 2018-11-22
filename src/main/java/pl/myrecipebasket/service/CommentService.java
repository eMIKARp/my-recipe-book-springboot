package pl.myrecipebasket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.myrecipebasket.model.Comment;
import pl.myrecipebasket.model.Recipe;
import pl.myrecipebasket.repository.CommentRepository;
import pl.myrecipebasket.repository.RecipeRepository;

/**
 * 
 * Object <b>{@code CommentService}</b> is a intermediate layer between 
 * persistence layer ({@code CommentRepository} & {@code RecipeRepository}) and 
 * view layer ({@code HomeController} & {@code CommentController}). Contains 
 * all {@code Comment} related business logic of <em>MyRecipeBasket</em> application.
 * 
 * @see pl.myrecipebasket.repository.CommentRepository
 * @see pl.myrecipebasket.repository.RecipeRepository
 * @see pl.myrecipebasket.web.controller.HomeController
 * @see pl.myrecipebasket.web.controller.CommentController
 * 
 */

@Service
public class CommentService {

	/**
	 * {@code CommentRepository} field declaration
	 */
	
	private CommentRepository commentRepository;
	
	/**
	 * {@code RecipeRepository} field declaration
	 */
	
	private RecipeRepository recipeRepository;
	
	/**
	 * This method injects {@code CommentRepository} object into {@code CommentService}
	 * and enables {@code CommentService} to perform CRUD operations on 
	 * {@code Comment} database table.
	 * 
	 * @param commentRepository {@code CommentRepository} object
	 */
	
	@Autowired
	public void setCommentRepository(CommentRepository commentRepository) {
		this.commentRepository=commentRepository;
	}
	
	/**
	 * 
	 * This method injects {@code RecipeRepository} object into {@code CommentService}
	 * and enables {@code CommentService} to perform CRUD operations on 
	 * {@code Recipe} database table.
	 * 
	 * @param recipeRepository {@code RecipeRepository} object
	 */
	
	@Autowired
	public void setRecipeRepository(RecipeRepository recipeRepository) {
		this.recipeRepository=recipeRepository;
	}

	/**
	 * 
	 * This method persists {@code Comment} object in 
	 * <em>MyRecipeBasket</em> database
	 * 
	 * @param comment {@code Comment} object
	 */
	
	public void saveComment(Comment comment) {
		commentRepository.save(comment);
	}
	
	/**
	 * 
	 * This method retrieves all {@code Comment} objects from 
	 * <em>MyRecipeBasket</em> database
	 * 
	 * @return a list of {@code Comment} objects
	 */
	
	public List<Comment> getAllComments(){
		return commentRepository.findAll();
	} 
}
