package pl.myrecipebasket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.myrecipebasket.model.Role;
import pl.myrecipebasket.model.User;
import pl.myrecipebasket.repository.RoleRepository;
import pl.myrecipebasket.repository.UserRepository;

@Service
public class UserService {
	
		private static final String DEFAULT_ROLE="ROLE_USER";
		private UserRepository userRepository;
		private RoleRepository roleRepository;
		
		@Autowired
		public void setUserRepository(UserRepository userRepository) {
			this.userRepository=userRepository;
		}
		
		@Autowired
		public void setRoleRepository(RoleRepository roleRepository) {
			this.roleRepository=roleRepository;
		}

		public void addWithDefaultRole(User user) {
			Role defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
			user.getRoles().add(defaultRole);
			userRepository.save(user);
		}
}