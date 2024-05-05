package com.res.order.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.res.order.db.DBAccess;

import jakarta.servlet.http.HttpSession;

@RestController
public class CartRestController {
	
	@Autowired
	HttpSession httpSession;

	@GetMapping("addCart")
	public void addCart(@RequestParam ("count") int count,
			@RequestParam ("itemName") String itemName,
			@RequestParam ("itemPrice") int itemPrice) {
		
		System.out.println("Calling Me!" + count +" "+ itemName +" "+ itemPrice);
		int tabId = (int) httpSession.getAttribute("tableId");
		int cusId = (int) httpSession.getAttribute("cusId");
		System.out.println(tabId);
		System.out.println(cusId);
		
		try {
			DBAccess.insertOrderCart(count,itemName,tabId,cusId,itemPrice);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
}
