package pl.myrecipebasket.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="vote")
public class Vote{

	
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
