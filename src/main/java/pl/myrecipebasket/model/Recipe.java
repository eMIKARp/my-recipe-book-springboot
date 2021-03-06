	package pl.myrecipebasket.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.type.descriptor.java.UUIDTypeDescriptor.ToBytesTransformer;

/**
 *
 * Object <b>{@code Recipe}</b> represents a recipe placed on an external 
 * web site that {@code User} finds worth storing for later use.
 * <br>
 * <br> 
 * A <b>{@code Recipe}</b> has:
 * <ul>
 * 	<li> {@code rName} - recipe name (e.g. "Recipe for homemade pancakes")</li>
 * 	<li> {@code rDescription} - recipe description (e.g. "This is my favorite recipe for homemade pancakes. Perfect taste and healthy ingredients")</li>
 * 	<li> {@code rUrl} - url address of web site where recipe is originally stored</li>
 * 	<li> {@code rDate} - time stamp when <code>Recipe</code> has been added to <em>MyRecipeBasket</em> database</li>
 * 	<li> {@code UpVote} & {@code DownVote} - information about number of supporting and opposing {@code Votes} left by <em>MyRecipeBasket</em> community</li>
 * 	<li> {@code isShared} - a flag which changes its value if {@code User} who added {@code Recipe} wants it to be shared with other <em>MyRecipeBasket</em> {@code Users}</li>
 * 	<li> {@code rCategories} - a list of {@code Categories} that are assigned to {@code Recipe}</li>
 * 	<li> {@code usrWhoAddedRecipe} - a reference to {@code User} who created {@code Recipe} 
 * 	<li> {@code votes} - a list of {@code Votes} left by <em>MyRecipeBasket</em> community</li>
 * 	<li> {@code comments} - a list of {@code Comments} left by <em>MyRecipeBasket</em> community</li>
 * 	<li> {@code usrWhoAddedRecipeToFavourites} - a list of {@code Users} who added this recipe to their favorite section</li> 
 * </ul> 
 * 
 * @see pl.myrecipebasket.model.User
 * @see pl.myrecipebasket.model.Vote
 * @see pl.myrecipebasket.model.Comment
 */

@Entity
@Table(name="recipe")
public class Recipe implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_recipe")
	private Long id;
	private String rName;
	private String rDescription;
	private String rUrl;
	private Timestamp rDate;
	private int UpVote;
	private int DownVote;
	private boolean isShared;
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="recipe_category",
		joinColumns= {@JoinColumn(name="recipe_id", referencedColumnName="id_recipe")},
		inverseJoinColumns= {@JoinColumn(name="category_id", referencedColumnName="id_category")})
	private List<Category> rCategories = new ArrayList<>();
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User usrWhoAddedRecipe;
	
	@OneToMany(mappedBy="recipe")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Vote> votes = new ArrayList<>();

	@OneToMany(mappedBy="recipeWithComment", fetch=FetchType.EAGER)
	private List<Comment> comments = new ArrayList<>();
	
	@ManyToMany(mappedBy="favRecipes",
		cascade= {CascadeType.PERSIST})
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<User> usrWhoAddedRecipeToFavourites = new ArrayList<>();

	
	public Recipe() {
		
	}
	
	public Recipe(String rName, String rDescription, String rUrl, Timestamp date, int upVote, int downVote,
			boolean isShared, List<Category> rCategories, User usrWhoAddedRecipe, List<Vote> votes,
			List<Comment> comments, List<User> usrWhoAddedRecipeToFavourites) {
		super();
		this.rName = rName;
		this.rDescription = rDescription;
		this.rUrl = rUrl;
		rDate = date;
		UpVote = upVote;
		DownVote = downVote;
		this.isShared = isShared;
		this.rCategories = rCategories;
		this.usrWhoAddedRecipe = usrWhoAddedRecipe;
		this.votes = votes;
		this.comments = comments;
		this.usrWhoAddedRecipeToFavourites = usrWhoAddedRecipeToFavourites;
	}



	public Comment addComment(Comment comment) {
		comment.setRecipeWithComment(this);
		getComments().add(comment);
		
		return comment;
	}
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Vote addVote(Vote vote) {
		vote.setRecipe(this);
		getVotes().add(vote);
		
		return vote;
	}
	
	public Long getId() {
		return id;
	}

	public String getrName() {
		return rName;
	}

	public String getrDescription() {
		return rDescription;
	}

	public String getrUrl() {
		return rUrl;
	}

	public Timestamp getDate() {
		return rDate;
	}

	public int getUpVote() {
		return UpVote;
	}

	public int getDownVote() {
		return DownVote;
	}

	public boolean isShared() {
		return isShared;
	}

	public List<Category> getrCategories() {
		return rCategories;
	}
	
	public User getUsrWhoAddedRecipe() {
		return usrWhoAddedRecipe;
	}

	public List<Vote> getVotes() {
		return votes;
	}

	public List<User> getUsrWhoAddedRecipeToFavourites() {
		return usrWhoAddedRecipeToFavourites;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setrName(String rName) {
		this.rName = rName;
	}

	public void setrDescription(String rDescription) {
		this.rDescription = rDescription;
	}

	public void setrUrl(String rUrl) {
		this.rUrl = rUrl;
	}

	public void setDate(Timestamp date) {
		rDate = date;
	}

	public void setUpVote(int upVote) {
		UpVote = upVote;
	}

	public void setDownVote(int downVote) {
		DownVote = downVote;
	}

	public void setShared(boolean isShared) {
		this.isShared = isShared;
	}

	public void setrCategories(List<Category> rCategories) {
		this.rCategories = rCategories;
	}

	public void setUsrWhoAddedRecipe(User usrWhoAddedRecipe) {
		this.usrWhoAddedRecipe = usrWhoAddedRecipe;
	}

	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}

	public void setUsrWhoAddedRecipeToFavourites(List<User> usrWhoAddedRecipeToFavourites) {
		this.usrWhoAddedRecipeToFavourites = usrWhoAddedRecipeToFavourites;
	}

	

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", rName=" + rName + ", usrWhoAddedRecipe=" + usrWhoAddedRecipe + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recipe other = (Recipe) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	
}	
