package pl.myrecipebasket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.myrecipebasket.model.Role;
import pl.myrecipebasket.model.User;
import pl.myrecipebasket.repository.RoleRepository;
import pl.myrecipebasket.repository.UserRepository;

/**
 * 
 * Object <b>{@code UserService}</b> is a intermediate layer between 
 * persistence layer ({@code UserRepository} & {@code RoleRepository}) and 
 * view layer ({@code CommentController} & {@code HomeController} & {@code RecipeController} 
 * & @{@code RegisterController} & {@code VoteController}). 
 * Contains all {@code User} related business logic of <em>MyRecipeBasket</em> application.
 * 
 * @see pl.myrecipebasket.repository.UserRepository
 * @see pl.myrecipebasket.repository.RoleRepository
 * @see pl.myrecipebasket.web.controller.CommentController
 * @see pl.myrecipebasket.web.controller.HomeController
 * @see pl.myrecipebasket.web.controller.RecipeController
 * @see pl.myrecipebasket.web.controller.RegisterController
 * @see pl.myrecipebasket.web.controller.VoteController
 * 
 */

@Service
public class UserService {

		/**
		 *  <code DEFAULT_ROLE> constant declaration
		 */
	
		private static final String DEFAULT_ROLE="ROLE_USER";
		
		/**
		 * {@code UserRepository} field declaration 
		 */
		
		private UserRepository userRepository;
		
		/**
		 * {@code RoleRepository} field declaration
		 */
		
		private RoleRepository roleRepository;
		
		/**
		 * This method injects {@code UserRepository} object into {@code UserService}
		 * and enables {@code UserService} to perform CRUD operations on {@code User}
		 * database table.
		 * 
		 * @param userRepository {@code UserRepository} object
		 */
		
		@Autowired
		public void setUserRepository(UserRepository userRepository) {
			this.userRepository=userRepository;
		}
		
		/**
		 * This method injects {@code RoleRepository} object into {@code UserService}
		 * and enables {@code UserService} to perform CRUD operations on {@code Role}
		 * database table.
		 * 
		 * @param roleRepository {@code RoleRepository} object
		 */
		
		@Autowired
		public void setRoleRepository(RoleRepository roleRepository) {
			this.roleRepository=roleRepository;
		}
		
		/**
		 * This method retrieves from <em>MyRecipeBasket</em> database single {@code User}
		 * object which name field equals one provided as parameter of this method.
		 *  
		 * @param username name of {@code User} object that is being looked for
		 * @return {@code User} object
		 */
		
		public User findByUsername(String username) {
			User wantedUser = userRepository.findByUsername(username);
			return wantedUser;
		}
		
		/**
		 * This method persists {@code User} object in <em>MyRecipeBasket</em> database
		 * and sets its {@code Role} to default before persisting 
		 *  
		 * @param user {@code User} object that is to be persisted
		 */
		
		public void addWithDefaultRole(User user) {
			Role defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
			user.getRoles().add(defaultRole);
			user.setActive(true);
			userRepository.save(user);
		}
		
		/**
		 * This method persists {@code User} object in <em>MyRecipeBasket</em> database.
		 *  
		 * @param user {@code User} object that is to be persisted
		 */
		
		public void saveUser(User user) {
			userRepository.save(user);
		}
}
