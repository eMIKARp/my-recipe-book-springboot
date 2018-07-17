package pl.myrecipebasket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.myrecipebasket.model.Role;
import pl.myrecipebasket.model.User;
import pl.myrecipebasket.repository.RecipeRepository;
import pl.myrecipebasket.repository.RoleRepository;
import pl.myrecipebasket.repository.UserRepository;

@Service
public class UserService {
	
		private static final String DEFAULT_ROLE="ROLE_USER";
		private UserRepository userRepository;
		private RoleRepository roleRepository;
		private RecipeRepository recipeRepository;
		
		@Autowired
		public void setUserRepository(UserRepository userRepository) {
			this.userRepository=userRepository;
		}
		
		@Autowired
		public void setRoleRepository(RoleRepository roleRepository) {
			this.roleRepository=roleRepository;
		}
		
		@Autowired
		public void setRecipeRepository(RecipeRepository recipeRepository) {
			this.recipeRepository=recipeRepository;
		}

		public User findByUsername(String username) {
			User wantedUser = userRepository.findByUsername(username);
			return wantedUser;
		}
		public void addWithDefaultRole(User user) {
			Role defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
			user.getRoles().add(defaultRole);
			user.setActive(true);
			userRepository.save(user);
		}
		
		public void saveUser(User user) {
			userRepository.save(user);
		}
}
