	package pl.myrecipebasket.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import pl.myrecipebasket.security.CustomUserDetailsService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public UserDetailsService customUserDetailsService() {
		return new CustomUserDetailsService();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/homepage").permitAll()
			.antMatchers("/register").permitAll()
			.antMatchers("/login").permitAll()
			.antMatchers("/registerSuccess").permitAll()
			.antMatchers("/loginError").permitAll()
			.antMatchers("/comment").permitAll()
			.antMatchers("/img/**").permitAll()
			.anyRequest().authenticated()
		.and()
		.formLogin()
			.loginPage("/login")
		    .failureUrl("/loginError")	
		.and()
		.logout()
			.logoutUrl("/logout")
				.logoutSuccessUrl("/")
					.permitAll();
	}
}
