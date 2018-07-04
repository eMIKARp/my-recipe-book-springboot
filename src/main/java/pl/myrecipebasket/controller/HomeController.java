package pl.myrecipebasket.controller;

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
	
	
		model.addAttribute("user", new User());
		model.addAttribute("category", new Category());
		model.addAttribute("recipe", new Recipe());
		model.addAttribute("role",new Role());
		model.addAttribute("vote",new Vote());

		
		return "homepage";
	}
}
