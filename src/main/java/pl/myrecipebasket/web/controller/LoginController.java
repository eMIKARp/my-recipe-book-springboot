package pl.myrecipebasket.web.controller;

import javax.validation.Valid;

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

@Controller
public class LoginController {

	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}	
	
	@PostMapping("/login")
	public String postLogin() {
		return "redirect:homepage";
	}
}
	