package pl.myrecipebasket.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * Object <b>{@code User}</b> represents a person who created an account in 
 * <em>MyRecipeBasket</em> application and is using it to store and share links 
 * to recipes found on some external web sites.
 * <br>
 * <br>
 * An <b>{@code User}</b> has:
 * <ul>
 * 	<li> {@code username} - a name that {@code User} have chosen to use within <em>MyRecipeBasket</em> application</li>
 * 	<li> {@code email} - an email address provided by {@code User} when creating account</li> 
 *  <li> {@code password} - a password provided by {@code User} when creating account</li>
 *  <li> {@code isActive} - a flag which state if {@code User} is active and can perform {@code User} specific actions</li>
 *  <li> {@code roles} - a list of {@code Roles} that have been assigned to {@code User}</li>
 *  <li> {@code ownRecipes} - a list of {@code Recipes} added to <em>MyRecipeBasket</em> database by this {@code User} </li>
 *  <li> {@code favRecipes} - a list of {@code Recipes} that this {@code User} added to his favorite section</li>
 *  <li> {@code votes} - a list of {@code Votes} left by this {@code User}</li>
 *  <li> {@code comments} - a list of {@code Comments} left by this {@code User}</li>
 * </ul>
 * 
 * @see pl.myrecipebasket.model.Role
 * @see pl.myrecipebasket.model.Recipe
 * @see pl.myrecipebasket.model.Vote
 * @see pl.myrecipebasket.model.Comment
 * 
 *  
 */

@Entity
@Table(name="user")
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_user")
	private Long id;
	private String username;
	@NotEmpty
	private String email;
	@NotEmpty
	private String password;
	private boolean isActive;
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="user_role",
	joinColumns= {@JoinColumn(name="user_id", referencedColumnName="id_user")},
	inverseJoinColumns= {@JoinColumn(name="role_id", referencedColumnName="id_role")})
	private List<Role> roles = new ArrayList<>();
	
	@OneToMany(mappedBy="usrWhoAddedRecipe",
		cascade= {CascadeType.PERSIST})
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Recipe> ownRecipes = new ArrayList<>();
	
	@ManyToMany(cascade= {CascadeType.PERSIST})
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="user_favourite_recipes",
	joinColumns= {@JoinColumn(name="user_id", referencedColumnName="id_user")},
	inverseJoinColumns= {@JoinColumn(name="recipe_id", referencedColumnName="id_recipe")})
	private List<Recipe> favRecipes = new ArrayList<>();
	
	@OneToMany(mappedBy="user")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Vote> votes = new ArrayList<>();

	@OneToMany(mappedBy="userWhoLeftComment")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Comment> comments = new ArrayList<>();

	public User() {
		
	}
	
	public User(String username, String email, String password, boolean isActive, List<Role> roles,
			List<Recipe> ownRecipes, List<Recipe> favRecipes, List<Vote> votes, List<Comment> comments) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.isActive = isActive;
		this.roles = roles;
		this.ownRecipes = ownRecipes;
		this.favRecipes = favRecipes;
		this.votes = votes;
		this.comments = comments;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public Comment addComment(Comment comment) {
		comment.setUserWhoLeftComment(this);
		getComments().add(comment);
	
		return comment;
	}
	
	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public boolean isActive() {
		return isActive;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public Vote addVote(Vote vote) {
		vote.setUser(this);
		getVotes().add(vote);
	
		return vote;
	}
	
	public void removeVote(Vote vote) {
		getVotes().remove(vote);
	}
	
	public void addOwnRecipe(Recipe recipe) {
		recipe.setUsrWhoAddedRecipe(this);
		getOwnRecipes().add(recipe);
	}
	
	public void removeOwnRecipe(Recipe recipe) {
		getOwnRecipes().remove(recipe);
	}
	
	public List<Recipe> getOwnRecipes() {
		return ownRecipes;
	}
	
	public void addFavRecipe(Recipe recipe) {
		getFavRecipes().add(recipe);
	}
	
	public void removeFavRecipe(Recipe recipe) {
		getFavRecipes().remove(recipe);
	}

	public List<Recipe> getFavRecipes() {
		return favRecipes;
	}

	public List<Vote> getVotes() {
		return votes;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setOwnRecipes(List<Recipe> ownRecipes) {
		this.ownRecipes = ownRecipes;
	}

	public void setFavRecipes(List<Recipe> favRecipes) {
		this.favRecipes = favRecipes;
	}

	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", roles=" + roles + "]";
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
