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
import com.res.order.dao.Order;
import com.res.order.db.DBAccess;

import jakarta.servlet.http.HttpSession;

@Controller
public class BillController {
	
	@Autowired
	HttpSession httpSession;
	
	@GetMapping("/okaikei")
	public String okaikei(Model model) {
		
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
		
		try {
			ResultSet rs = DBAccess.getCustomerCount(cusId);
			
			if(rs.next()) {
				
				int	totalCus = rs.getInt("total_people");
				
				model.addAttribute("totalPeople",totalCus);
				System.out.println(totalCus);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "okaikei";
	}
	
	@GetMapping("/okaikeiKettei")
	public String okaikeiKettei() {
		
		int cusId = (int)httpSession.getAttribute("cusId");
		try {
			DBAccess.changeFinalCheckStatus(cusId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "payment_check";
	}
	
	@GetMapping("/okaikeiMachi")
	public String okaikeiMachi(Model model) {
		try {
			ResultSet rs = DBAccess.getWaitBillingCus();
			List<CTable> cusInfo = new ArrayList<>();
			while(rs.next()) {
				CTable cusInfoObj = new CTable();
				cusInfoObj.setTableId(rs.getInt("table_id"));
				cusInfoObj.setTotalPeople(rs.getInt("total_people"));
				cusInfoObj.setCustomerName(rs.getString("customer_name"));
				cusInfoObj.setCustomerId(rs.getInt("customer_id"));
				cusInfo.add(cusInfoObj);
			}
			model.addAttribute("cusInfo", cusInfo);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return"okaikeimachi";
	}
	
	@GetMapping("/paymentDetail")
	public String paymentDetail(@RequestParam("cusId")int cusId,
			@RequestParam("tableId")int tabId,
			@RequestParam("page")int pageId,
			Model model) {
		
			System.out.println(pageId);
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
			
			model.addAttribute("cusId",cusId);
			model.addAttribute("tabId",tabId);
			model.addAttribute("pageId",pageId);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace()
;		}
		return "payment_detail";
	}
	
	@GetMapping("/finishBillCheckOut")
	public String finishBillCheckOut(@RequestParam("customerId")int cusId,@RequestParam("tableId")int tabId, Model model) {
		try {
			DBAccess.changeCheckOutStatus(cusId);
			DBAccess.changeCheckOutTableStatus(tabId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:adminHome";
	}
	
	@GetMapping("journalRecord")
	public String journalRecord(Model model) {

		try {
			ResultSet rs = DBAccess.journalRecord();
			List<CTable> cusInfoRecList = new ArrayList<>();
			while(rs.next()) {
				CTable cusRecObj = new CTable();
				cusRecObj.setCheckInTime(rs.getString("checkin_time"));
				cusRecObj.setCheckOutTime(rs.getString("checkout_time"));
				cusRecObj.setTableId(rs.getInt("table_id"));
				cusRecObj.setTotalPeople(rs.getInt("total_people"));
				cusRecObj.setCustomerName(rs.getString("customer_name"));
				cusRecObj.setCustomerId(rs.getInt("customer_id"));
				cusInfoRecList.add(cusRecObj);
			}
			model.addAttribute("cusInfoRecListObj",cusInfoRecList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return "journal_record";
	}
}
