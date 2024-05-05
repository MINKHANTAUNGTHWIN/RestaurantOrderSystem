package com.res.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.res.order.dao.Menu;

@Controller
public class PageController {


	/*
	 * @GetMapping("/") public String loginPage(Model model) {
	 * 
	 * List<Integer> tablesId = new ArrayList<>(); for (int i = 1; i < 13; i++) {
	 * tablesId.add(i); } model.addAttribute("tableIdObj", tablesId);
	 * 
	 * return "index"; }
	 */

	@GetMapping("/adminHome")
	public String adminHome() {
		return "admin_home";
	}
	
	

	@GetMapping("/addMenu")
	public String toAddMenuPage(Model model) {

		model.addAttribute("msg", "this is test");
		model.addAttribute("menuObj", new Menu());
		return "add_menu_item";
	}

}
