package pl.myrecipebasket.model;

/**
 * 
 * Object <b>{@code VoteType}</b> represents a type that is used to 
 * describe {@code Votes} and which distinguish {@code Votes} that were left by 
 * {@code Users} who liked particular {@code Recipe} from those which were left
 * by {@code Users} who didn't like it.
 * <br>
 * <br>
 * There are two types of <b>{@code VoteType}</b>:
 * <ul>
 * 	<li>{@code VOTE-UP} - supporting vote, positive opinion</li> 
 * 	<li>{@code VOTE-DOWN} - opposing vote, negative opinion</li> 
 * </ul> 
 *	
 *	@see pl.myrecipebasket.model.User
 *	@see pl.myrecipebasket.model.Recipe
 */

public enum VoteType {
	VOTE_UP, VOTE_DOWN
}
