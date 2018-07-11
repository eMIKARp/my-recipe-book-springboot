package pl.myrecipebasket.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pl.myrecipebasket.model.Category;
import pl.myrecipebasket.model.Recipe;
import pl.myrecipebasket.model.Role;
import pl.myrecipebasket.model.User;
import pl.myrecipebasket.model.Vote;
import pl.myrecipebasket.service.UserService;

@Controller
public class RegisterController {

	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService=userService;
	}
	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@GetMapping("/registerSuccess")
	public String registerSuccess(Model model) {
		return "registerSuccess";
	}
	
	@PostMapping("/register")
	public String addUser(@ModelAttribute @Valid User user, BindingResult bindResult) {

		if(bindResult.hasErrors())
			return "register";
		else {
			userService.addWithDefaultRole(user);
			return "redirect:registerSuccess";
		}
	}
	
	
}
