package pl.myrecipebasket.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pl.myrecipebasket.model.Category;
import pl.myrecipebasket.model.Recipe;
import pl.myrecipebasket.model.Role;
import pl.myrecipebasket.model.User;
import pl.myrecipebasket.model.Vote;
import pl.myrecipebasket.service.CategoryService;

@Controller
public class HomeController {

	CategoryService categoryService;
	
	@Autowired
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("categories", categoryService.getCategories());
		return "homepage";
	}
	
	@GetMapping("/homepage")
	public String homepage(Model model) {
		model.addAttribute("categories", categoryService.getCategories());
		return "homepage";
	}
	
	@GetMapping("/userpage")
	public String userpage(Model model) {
		return "userpage";
	}
	
}
