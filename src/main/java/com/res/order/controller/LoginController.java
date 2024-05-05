package com.res.order.controller;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.res.order.dao.CTable;
import com.res.order.db.DBAccess;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@Autowired
	HttpSession httpSession;
	
	@GetMapping("/") 
	  public String loginPage(Model model) { 
		try { 
			ResultSet rs =DBAccess.selectTableId(); 
			List<CTable> tablesId = new ArrayList<>();
	  while(rs.next()) {
		  CTable tabIdObj = new CTable();
		  tabIdObj.setTableId(rs.getInt("table_id")); 
		  tabIdObj.setTableStatus(rs.getInt("table_status"));
		  tablesId.add(tabIdObj); 
		  }
	  model.addAttribute("tableIdObj",tablesId); } 
		catch (Exception e) { 
			// TODO:handle exception 
		}
		return "index";
		}
	
	@GetMapping("/adminLoginAction")
	public String adminLoginAction(@RequestParam("user_id") String userId, 
			@RequestParam("user_pwd") String userPwd,
			Model model) {
		try {
			ResultSet rs = DBAccess.checkAccByIdPwd(userId, userPwd);
			if (rs.next()) {
				System.out.println("HI Admin");
				return "redirect:adminHome";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

		// Login
		return "redirect:/";
	}
	
	@GetMapping("/LoginAction")
	public String LoginAction(@RequestParam("user_id") String userId, 
			@RequestParam("user_pwd") String userPwd,@RequestParam("tableId")int tableId,
			Model model) {
		try {
			ResultSet rs = DBAccess.checkAccByIdPwd1(userId, userPwd);
			if (rs!=null && rs.next()) {
				
				System.out.println(tableId);
				System.out.println("HI customer");
				
				httpSession.setAttribute("tableId",tableId);
				httpSession.setAttribute("userId",userId);
				
				return "redirect:allMenu";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		// Login
		return "redirect:/";
	}
	
	
	
}
