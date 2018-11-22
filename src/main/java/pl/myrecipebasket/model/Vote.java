package pl.myrecipebasket.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * Object <b>{@code Vote}</b> represents {@code User's} opinion about particular {@code Recipe}, which can be positive or negative.
 * <br>   
 * <br>
 * A <b>{@code Vote}</b> has:
 * <ul>
 * 	<li>{@code user} - a reference to {@code User} who left this {@code Vote}</li>
 * 	<li>{@code recipe} - a reference to {@code Recipe} which it concerns</li>
 * 	<li>{@code date} - a time stamp when {@code Vote} had been left</li>
 * <li>{@code voteType} - an information if {@code Vote} supports or opposes {@code Recipe} it concerns</li>
 * </ul>   
 *
 *@see pl.myrecipebasket.model.User
 *@see pl.myrecipebasket.model.Recipe
 *
 */

@Entity
@Table(name="vote")
public class Vote implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name="recipe_id")
	private Recipe recipe;
	private Timestamp date;
	private VoteType voteType;
	
	public Vote() {
		
	}
	
	public Vote(User user, Recipe recipe, VoteType voteType) {
		super();
		this.user = user;
		this.recipe = recipe;
		this.voteType = voteType;
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public Timestamp getDate() {
		return date;
	}

	public VoteType getVoteType() {
		return voteType;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}

	@Override
	public String toString() {
		return "Vote [id=" + id + ", user=" + user + ", recipe=" + recipe + ", date=" + date + ", voteType=" + voteType
				+ "]";
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
		Vote other = (Vote) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
