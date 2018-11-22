package pl.myrecipebasket.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Object <b><code>Comment</code></b> represents a message that can
 * be left by <code>User</code> who wants to share his/hers opinion
 * about particular <code>Recipe</code> with other <code>Users</code>. 
 * <br>
 * <br>
 * A <b><code>Comment</code></b> has:
 * <ul>
 * 	<li> <code>title</code> - message title (e.g. "Best pancakes ever!")</li>
 *  <li> <code>contents</code> - message contents (e.g. "Using this recipe I've 
 *  prepared pancakes for me and my family last night. They were delicious!)</li> 
 *  <li> <code>date</code> - time stamp when <code>Comment</code> has  been created</li> 
 *  <li> <code>images</code> - list of <code>Images</code> uploaded by <code>User</code></li> 
 * 	<li> <code>recipeWithComment</code> - reference to <code>Recipe</code> it concerns</li>
 * 	<li> <code>userWhoLeftComment</code> - reference to <code>User</code> who created it</li>
 * </ul> 
 * 
 * @see pl.myrecipebasket.model.User
 * @see pl.myrecipebasket.model.Recipe
 * @see pl.myrecipebasket.model.Image
 *
 */

@Entity
public class Comment implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private String contents;
	private Timestamp date;
	
	@OneToMany(mappedBy="comment", 
			cascade= {CascadeType.PERSIST, CascadeType.REMOVE},
			fetch=FetchType.EAGER)
	private List<Image> images = new ArrayList<>();
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="recipe_id")
	private Recipe recipeWithComment;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User userWhoLeftComment;
	
	public Comment() {
		
	}

	public Comment(String title, String contents, Timestamp date, List<Image> images, Recipe recipeWithComment,
			User userWhoLeftComment) {
		
		this.title = title;
		this.contents = contents;
		this.date = date;
		this.images = images;
		this.recipeWithComment = recipeWithComment;
		this.userWhoLeftComment = userWhoLeftComment;
	}

	public Image addImage(Image image) {
		image.setComment(this);
		getImages().add(image);
	
		return image;
	}
	
	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

	public Timestamp getDate() {
		return date;
	}

	public List<Image> getImages() {
		return images;
	}

	public Recipe getRecipeWithComment() {
		return recipeWithComment;
	}

	public User getUserWhoLeftComment() {
		return userWhoLeftComment;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public void setRecipeWithComment(Recipe recipeWithComment) {
		this.recipeWithComment = recipeWithComment;
	}

	public void setUserWhoLeftComment(User userWhoLeftComment) {
		this.userWhoLeftComment = userWhoLeftComment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contents == null) ? 0 : contents.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((images == null) ? 0 : images.hashCode());
		result = prime * result + ((recipeWithComment == null) ? 0 : recipeWithComment.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((userWhoLeftComment == null) ? 0 : userWhoLeftComment.hashCode());
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
		Comment other = (Comment) obj;
		if (contents == null) {
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (images == null) {
			if (other.images != null)
				return false;
		} else if (!images.equals(other.images))
			return false;
		if (recipeWithComment == null) {
			if (other.recipeWithComment != null)
				return false;
		} else if (!recipeWithComment.equals(other.recipeWithComment))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (userWhoLeftComment == null) {
			if (other.userWhoLeftComment != null)
				return false;
		} else if (!userWhoLeftComment.equals(other.userWhoLeftComment))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", title=" + title + ", contents=" + contents + ", date=" + date + ", imageUrl="
				+ images + ", recipeWithComment=" + recipeWithComment + ", userWhoLeftComment=" + userWhoLeftComment
				+ "]";
	}
	
	
}
