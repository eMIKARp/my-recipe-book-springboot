package pl.myrecipebasket.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pl.myrecipebasket.model.Category;
import pl.myrecipebasket.model.Recipe;
import pl.myrecipebasket.model.Role;
import pl.myrecipebasket.model.User;
import pl.myrecipebasket.model.Vote;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(Model model) {
		return "homepage";
	}
	
	@GetMapping("/homepage")
	public String homepage(Model model) {
		return "homepage";
	}
	
	@GetMapping("/userpage")
	public String userpage(Model model) {
		return "userpage";
	}
	
}
