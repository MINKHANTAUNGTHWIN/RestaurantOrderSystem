package com.res.order.controller;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.res.order.dao.Order;
import com.res.order.db.DBAccess;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {
	
	@Autowired
	HttpSession httpSession;
	
	@GetMapping("/toCart")
	public String toCart(Model model) {
		
		int cusId = (int)httpSession.getAttribute("cusId");
		try {
			ResultSet rs = DBAccess.addCart(cusId);
			List<Order> orderList = new ArrayList<>();
			while(rs.next()) {
				Order orderObj = new Order();
				orderObj.setOrderId(rs.getInt("order_id"));
				orderObj.setOrderMenuName(rs.getString("order_item_name"));
				orderObj.setOrderQuantity(rs.getInt("order_quantity"));
				orderObj.setOrderItemPrice(rs.getInt("order_item_price"));
				orderList.add(orderObj);
			}
			model.addAttribute("orderListObj",orderList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace()
;		}
		
		return "cart";
	}
	
	@GetMapping("/toOrderHistory")
	public String toOrderHistory(Model model) {
		
		int cusId = (int)httpSession.getAttribute("cusId");
		try {
			ResultSet rs = DBAccess.orderHistory(cusId);
			List<Order> orderList = new ArrayList<>();
			int subTotal = 0;
			while(rs.next()) {
				Order orderObj = new Order();
				orderObj.setOrderId(rs.getInt("order_id"));
				orderObj.setOrderMenuName(rs.getString("order_item_name"));
				orderObj.setOrderQuantity(rs.getInt("order_quantity"));
				orderObj.setOrderItemPrice(rs.getInt("order_item_price"));
				orderList.add(orderObj);
				
				subTotal += orderObj.getOrderItemPrice() * orderObj.getOrderQuantity();
			}
			model.addAttribute("orderListObj",orderList);
			model.addAttribute("subTotal",subTotal);
			model.addAttribute("tax",subTotal* 0.1);
			model.addAttribute("total",subTotal+(subTotal* 0.1));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace()
;		}
		
		return "order_history";
	}
	
	
	
	@GetMapping("deleteOrder")
	public String deleteOrder(@RequestParam("orderId")int orderId) {
		try {
			DBAccess.deleteOrder(orderId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:toCart";
	}
	
	@GetMapping("orderAction")
	public String orderAction(@RequestParam("orderId")int orderId) {
		try {
			DBAccess.changeOrderStatus(orderId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:toCart";
	}
}
