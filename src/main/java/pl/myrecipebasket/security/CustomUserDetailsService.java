package pl.myrecipebasket.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import pl.myrecipebasket.model.User;
import pl.myrecipebasket.model.Role;
import pl.myrecipebasket.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService{

	private UserRepository userRepository;
    
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username);
		if(user == null)
			throw new UsernameNotFoundException("User not found");
		org.springframework.security.core.userdetails.User userDetails = 
				new org.springframework.security.core.userdetails.User(
						user.getUsername(), 
						user.getPassword(), 
						convertAuthorities(user.getRoles()));
		return userDetails;
	}

	private List<GrantedAuthority> convertAuthorities(List<Role> userRoles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(Role ur: userRoles) {
			authorities.add(new SimpleGrantedAuthority(ur.getRole()));
		}
		return authorities;
	}
}





