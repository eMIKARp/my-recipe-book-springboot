package pl.myrecipebasket.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Object <b>{@code LoginController}</b> is responsible for handling all
 * login related requests. Its main task is to receive input from {@code User} 
 * during authorization, process it via proper Services and display response 
 * by invoking appropriate html.page 
 */

@Controller
public class LoginController {

	/**
	 * This method displays {@code login.html} page
	 * @return {@code login.html}
	 */
	
	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}
	
	/**
	 * This method displays {@code login.html} page
	 * @return {@code loginError.html}
	 */
	
	@GetMapping("/loginError")
	public String loginError() {
		return "loginError";
	}	
}
	