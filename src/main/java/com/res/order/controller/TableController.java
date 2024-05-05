package com.res.order.controller;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.res.order.dao.CTable;
import com.res.order.db.DBAccess;

@Controller
public class TableController {
	
	@GetMapping("showAllTables")
	public String showAllTables(Model model) {
		try {
			ResultSet rs = DBAccess.allTable();
			List<CTable> tables = new ArrayList<>();
			while(rs.next()) {
				CTable tableObj = new CTable();
				tableObj.setTableId(rs.getInt("table_id"));
				tableObj.setTableStatus(rs.getInt("table_status"));
				tableObj.setTableCapacity(rs.getInt("table_capacity"));
				tableObj.setTotalPeople(rs.getInt("total_people"));
				tableObj.setCustomerName(rs.getString("customer_name"));
				tableObj.setCustomerId(rs.getInt("customer_id"));
				tables.add(tableObj);
			}
			model.addAttribute("tableObjs",tables);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "table_list";
	}
	
	@GetMapping("registerCheckIn")
	public String registerCheckIn(@RequestParam("customerName")String custName,
			@RequestParam("noOfPeople")int noPeople ,
			@RequestParam("tableNo")int tblNo) {
				DBAccess.regCheck(tblNo);
				DBAccess.regCheckInsertRecord(custName,noPeople,tblNo);
		return"redirect:showAllTables";
	}
	
	@GetMapping("registerCheckInUpdate")
	public String registerCheckInUpdate(@RequestParam("customerName")String custName,
			@RequestParam("noOfPeople")int noPeople ,
			@RequestParam("customerId")int custId) {
				DBAccess.regCheckInUpdate(custName,noPeople,custId);
		return"redirect:showAllTables";
	}
	
	@GetMapping("deleteRegisterInfo")
	public String deleteRegisterInfo(@RequestParam("customerId")int custId,
			@RequestParam("tableNo")int tblNo) {
		DBAccess.regCheck1(tblNo);
		DBAccess.deleteRegisterInfo(custId);
		
		return"redirect:showAllTables";
	}
	
	

}
