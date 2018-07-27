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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="category")
public class Category implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_category")
	private Long id;
	@Column(name="cat_name")
	private String cName;
	@Column(name="cat_type")
	private CategoryType cType;
	@Column(name="cat_description")
	private String cDescription;
	
	@ManyToMany(mappedBy="rCategories",
			cascade= {CascadeType.MERGE})
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Recipe> cRecipes = new ArrayList<>();
	
	public Category() {
		
	}
	
	public Category(String cName, CategoryType cType, String cDescription) {
		super();
		this.cName = cName;
		this.cType = cType;
		this.cDescription = cDescription;
	}

	public Long getId() {
		return id;
	}

	public String getcName() {
		return cName;
	}

	public CategoryType getcType() {
		return cType;
	}

	public String getcDescription() {
		return cDescription;
	}

	public List<Recipe> getcRecipes() {
		return cRecipes;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public void setcType(CategoryType cType) {
		this.cType = cType;
	}

	public void setcDescription(String cDescription) {
		this.cDescription = cDescription;
	}

	public void setcRecipes(List<Recipe> cRecipes) {
		this.cRecipes = cRecipes;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", cName=" + cName + ", cType=" + cType + ", cDescription=" + cDescription
				+ ", #RecipesInCategory=" + cRecipes.size() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cName == null) ? 0 : cName.hashCode());
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
		Category other = (Category) obj;
		if (cName == null) {
			if (other.cName != null)
				return false;
		} else if (!cName.equals(other.cName))
			return false;
		return true;
	}

	
	
		
}
