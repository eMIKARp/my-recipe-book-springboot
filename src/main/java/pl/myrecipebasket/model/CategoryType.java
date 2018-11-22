package pl.myrecipebasket.model;

/**
 * Object <b><code>CategoryType</code></b> represents a type that
 * is used to describe <code>Category</code> and which distinguish 
 * <code>Categories</code> that have been provided in application 
 * from those created by <code>User</code>.
 * <br>
 * <br>
 * There are two <b><code>CategoryTypes<code></b>:
 * <ul>
 * 	<li><code>PRE_DEFINED</code> - provided in application, non editable</li>
 * 	<li><code>USER_DEFINED</code> - created by <code>Users</code>, editable by its creator</li>
 * </ul>
 * 
 * @see pl.myrecipebasket.model.Category
 * @see pl.myrecipebasket.model.User
 * 
 */

public enum CategoryType {
	PRE_DEFINED, USER_DEFINED
}
