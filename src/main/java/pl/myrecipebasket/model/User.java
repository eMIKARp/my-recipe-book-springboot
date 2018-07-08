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

@Entity
@Table(name="user")
public class User{

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
	@ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="user_role",
	joinColumns= {@JoinColumn(name="user_id", referencedColumnName="id_user")},
	inverseJoinColumns= {@JoinColumn(name="role_id", referencedColumnName="id_role")})
	private List<Role> roles = new ArrayList<>();
	@OneToMany(mappedBy="usrWhoAddedRecipe",
		cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Recipe> ownRecipes = new ArrayList<>();
	
	@ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="user_favourite_recipes",
	joinColumns= {@JoinColumn(name="user_id", referencedColumnName="id_user")},
	inverseJoinColumns= {@JoinColumn(name="recipe_id", referencedColumnName="id_recipe")})
	private List<Recipe> favRecipes = new ArrayList<>();
	
	@OneToMany(mappedBy="user",
		cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Vote> votes = new ArrayList<>();
	
	public User() {
		
	}
	
	public User(String username, String email, String password, List<Role> roles) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.isActive = true;
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", isActive=" + isActive + ", roles=" + roles + ", #ownRecipes=" + ownRecipes.size() + ", #favRecipes="
				+ favRecipes.size() + ", #votes=" + votes.size() + "]";
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

	public List<Recipe> getOwnRecipes() {
		return ownRecipes;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((favRecipes == null) ? 0 : favRecipes.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + ((ownRecipes == null) ? 0 : ownRecipes.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((votes == null) ? 0 : votes.hashCode());
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
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (favRecipes == null) {
			if (other.favRecipes != null)
				return false;
		} else if (!favRecipes.equals(other.favRecipes))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isActive != other.isActive)
			return false;
		if (ownRecipes == null) {
			if (other.ownRecipes != null)
				return false;
		} else if (!ownRecipes.equals(other.ownRecipes))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (votes == null) {
			if (other.votes != null)
				return false;
		} else if (!votes.equals(other.votes))
			return false;
		return true;
	}

	
	
}
