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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="recipe")
public class Recipe{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_recipe")
	private Long id;
	private String rName;
	private String rDescription;
	private String rUrl;
	private Timestamp Date;
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
	
	@OneToMany(mappedBy="recipe",
		cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Vote> votes = new ArrayList<>();
	
	@ManyToMany(mappedBy="favRecipes",
		cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<User> usrWhoAddedRecipeToFavourites = new ArrayList<>();

	
	public Recipe() {
		
	}
	
	public Recipe(String rName, String rDescription, String rUrl, Timestamp date, int upVote, int downVote,
			boolean isShared, List<Category> rCategories, User usrWhoAddedRecipe) {
		super();
		this.rName = rName;
		this.rDescription = rDescription;
		this.rUrl = rUrl;
		Date = date;
		UpVote = upVote;
		DownVote = downVote;
		this.isShared = isShared;
		this.rCategories = rCategories;
		this.usrWhoAddedRecipe = usrWhoAddedRecipe;
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
		return Date;
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
		Date = date;
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
		return "Recipe [id=" + id + ", rName=" + rName + ", rDescription=" + rDescription + ", rUrl=" + rUrl + ", Date="
				+ Date + ", #UpVote=" + UpVote + ", #DownVote=" + DownVote + ", isShared=" + isShared + ", #Categories="
				+ rCategories.size() + ", usrWhoAddedRecipe=" + usrWhoAddedRecipe + ", #votes=" + votes.size()
				+ ", #Likes=" + usrWhoAddedRecipeToFavourites.size() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Date == null) ? 0 : Date.hashCode());
		result = prime * result + DownVote;
		result = prime * result + UpVote;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isShared ? 1231 : 1237);
		result = prime * result + ((rCategories == null) ? 0 : rCategories.hashCode());
		result = prime * result + ((rDescription == null) ? 0 : rDescription.hashCode());
		result = prime * result + ((rName == null) ? 0 : rName.hashCode());
		result = prime * result + ((rUrl == null) ? 0 : rUrl.hashCode());
		result = prime * result + ((usrWhoAddedRecipe == null) ? 0 : usrWhoAddedRecipe.hashCode());
		result = prime * result
				+ ((usrWhoAddedRecipeToFavourites == null) ? 0 : usrWhoAddedRecipeToFavourites.hashCode());
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
		Recipe other = (Recipe) obj;
		if (Date == null) {
			if (other.Date != null)
				return false;
		} else if (!Date.equals(other.Date))
			return false;
		if (DownVote != other.DownVote)
			return false;
		if (UpVote != other.UpVote)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isShared != other.isShared)
			return false;
		if (rCategories == null) {
			if (other.rCategories != null)
				return false;
		} else if (!rCategories.equals(other.rCategories))
			return false;
		if (rDescription == null) {
			if (other.rDescription != null)
				return false;
		} else if (!rDescription.equals(other.rDescription))
			return false;
		if (rName == null) {
			if (other.rName != null)
				return false;
		} else if (!rName.equals(other.rName))
			return false;
		if (rUrl == null) {
			if (other.rUrl != null)
				return false;
		} else if (!rUrl.equals(other.rUrl))
			return false;
		if (usrWhoAddedRecipe == null) {
			if (other.usrWhoAddedRecipe != null)
				return false;
		} else if (!usrWhoAddedRecipe.equals(other.usrWhoAddedRecipe))
			return false;
		if (usrWhoAddedRecipeToFavourites == null) {
			if (other.usrWhoAddedRecipeToFavourites != null)
				return false;
		} else if (!usrWhoAddedRecipeToFavourites.equals(other.usrWhoAddedRecipeToFavourites))
			return false;
		if (votes == null) {
			if (other.votes != null)
				return false;
		} else if (!votes.equals(other.votes))
			return false;
		return true;
	}
	
	
	
	
}	
