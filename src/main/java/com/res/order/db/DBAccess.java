package com.res.order.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.res.order.dao.Menu;

public class DBAccess {
	
	public static ResultSet checkAccByIdPwd(String userId, String userPwd) {
		try {
			Connection con = DBConnector.getConnection();
			PreparedStatement pstm = con.prepareStatement("SELECT * FROM account WHERE user_id = ? AND user_pwd = ? AND user_role=0");
			pstm.setString(1, userId);
			pstm.setString(2, userPwd);

			ResultSet rs = pstm.executeQuery();

			return rs;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static ResultSet checkAccByIdPwd1(String userId, String userPwd) {
		try {
			Connection con = DBConnector.getConnection();
			PreparedStatement pstm = con.prepareStatement("SELECT * FROM account WHERE user_id = ? AND user_pwd = ?");
			pstm.setString(1, userId);
			pstm.setString(2, userPwd);

			ResultSet rs = pstm.executeQuery();
			return rs;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static void deleteMenuAction(Menu menu) {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "DELETE FROM all_menus WHERE menu_id=?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1,menu.getMenuId());
			
			pstm.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void addMenuAction(Menu menu) {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "INSERT INTO all_menus (menu_photo,menu_name,menu_price,menu_category,menu_detail) VALUES (?,?,?,?,?)";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setBytes(1,menu.getMenuPhoto().getBytes());
			pstm.setString(2, menu.getMenuName());
			pstm.setString(3, menu.getMenuPrice());
			pstm.setInt(4, menu.getMenuCategory());
			pstm.setString(5, menu.getMenuDetail());
			
			pstm.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void editMenuAction(Menu menu) {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "UPDATE all_menus SET menu_photo = ?,menu_name = ?, menu_price= ?, menu_category= ?, menu_detail=? WHERE menu_id=?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setBytes(1,menu.getMenuPhoto().getBytes());
			pstm.setString(2, menu.getMenuName());
			pstm.setString(3, menu.getMenuPrice());
			pstm.setInt(4, menu.getMenuCategory());
			pstm.setString(5, menu.getMenuDetail());
			pstm.setInt(6, menu.getMenuId());
			pstm.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static ResultSet editMenu(int menuId) {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "SELECT * FROM all_menus WHERE menu_id=?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, menuId);
			
			ResultSet rs = pstm.executeQuery();
			return rs;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static ResultSet allMenu() {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "SELECT * FROM all_menus";
			PreparedStatement pstm = con.prepareStatement(sql);
			
			ResultSet rs = pstm.executeQuery(); //multiple record
			return rs;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	public static ResultSet adminAllMenu() {
		try {
			Connection con = DBConnector.getConnection();
			
			String sql = "SELECT * FROM all_menus";
			PreparedStatement pstm = con.prepareStatement(sql);
			
			ResultSet rs = pstm.executeQuery(); //multiple record
			return rs;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	public static ResultSet getCustomerId(int tabId) {
		try {
			Connection con = DBConnector.getConnection();
			
			String sql = "SELECT customer_id FROM customer_info WHERE check_status=0 && table_id=?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1,tabId);
			ResultSet rs = pstm.executeQuery(); //single record
			return rs;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public static ResultSet selectTableId() {
		try {
			Connection con = DBConnector.getConnection();
			
			String sql = "SELECT * FROM all_tables";
			PreparedStatement pstm = con.prepareStatement(sql);
			
			ResultSet rs = pstm.executeQuery(); //multiple record
			return rs;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	public static ResultSet allTable() {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "SELECT * FROM all_tables LEFT JOIN (SELECT * FROM customer_info WHERE check_status = 0) AS checkInCustomer\r\n"
					+ "ON all_tables.table_id = checkInCustomer.table_id";
			PreparedStatement pstm = con.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			return rs;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static void regCheck(int tableNo) {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "UPDATE  all_tables SET table_status=1 WHERE table_id=?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1,tableNo);
			
			pstm.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void regCheck1(int tableNo) {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "UPDATE  all_tables SET table_status=0 WHERE table_id=?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1,tableNo);
			
			pstm.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void regCheckInsertRecord(String cusName,int noPeople,int tableNo) {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "INSERT INTO customer_info (customer_name,total_people,table_id,checkin_time) VALUES(?,?,?,sysdate())";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1,cusName);
			pstm.setInt(2,noPeople);
			pstm.setInt(3,tableNo);
			
			pstm.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void regCheckInUpdate(String cusName,int noPeople,int custId) {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "UPDATE customer_info SET customer_name=? ,total_people=? WHERE customer_id=?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1,cusName);
			pstm.setInt(2,noPeople);
			pstm.setInt(3,custId);
			
			pstm.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void deleteRegisterInfo(int custId) {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "DELETE FROM customer_info WHERE customer_id=?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1,custId);
			
			pstm.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void insertOrderCart(int orderQun ,String orderItemName,int tableId,int customerId,int itemPrice) {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "INSERT INTO orders (order_quantity,order_item_name,table_id,customer_id,order_item_price) VALUES(?,?,?,?,?)";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1,orderQun);
			pstm.setString(2,orderItemName);
			pstm.setInt(3,tableId);
			pstm.setInt(4,customerId);
			pstm.setInt(5,itemPrice);
			
			pstm.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static ResultSet addCart(int cusId) {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "SELECT * FROM orders WHERE customer_id=? && order_status=0";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1,cusId);
			
			ResultSet rs = pstm.executeQuery(); //multiple record
			return rs;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	public static ResultSet orderHistory(int cusId) {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "SELECT * FROM orders WHERE customer_id=? && order_status=1";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1,cusId);
			
			ResultSet rs = pstm.executeQuery(); //multiple record
			return rs;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	public static ResultSet getCustomerCount(int cusId) {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "SELECT * FROM customer_info WHERE customer_id=?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1,cusId);
			
			ResultSet rs = pstm.executeQuery(); //multiple record
			return rs;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	public static void changeFinalCheckStatus(int cusId) {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "UPDATE customer_info SET check_status=1 WHERE customer_id=?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1,cusId);
			
			pstm.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static ResultSet getWaitBillingCus() {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "SELECT * FROM customer_info WHERE check_status=1";
			PreparedStatement pstm = con.prepareStatement(sql);
			
			ResultSet rs = pstm.executeQuery(); //multiple record
			return rs;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	public static void changeCheckOutStatus(int customerId) {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "UPDATE customer_info SET check_status=2,checkout_time=sysdate() WHERE customer_id = ?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1,customerId);
			pstm.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void changeCheckOutTableStatus(int tabId) {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "UPDATE all_tables SET table_status=0 WHERE table_id = ?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1,tabId);
			pstm.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static ResultSet journalRecord() {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "SELECT * FROM customer_info WHERE check_status=2";
			PreparedStatement pstm = con.prepareStatement(sql);
			
			ResultSet rs = pstm.executeQuery(); //multiple record
			return rs;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	
	public static void deleteOrder(int orderId) {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "DELETE FROM orders WHERE order_id=?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1,orderId);
			
			pstm.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void changeOrderStatus(int orderId) {
		try {
			Connection con = DBConnector.getConnection();
			String sql = "UPDATE orders SET order_status=1 WHERE order_id = ?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1,orderId);
			pstm.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void changeStockStatus (int menuId,int status) {
		try {
			Connection conn = DBConnector.getConnection();
			String sql = "UPDATE all_menus SET status_of_stock=? WHERE menu_id=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1,status);
			pstm.setInt(2,menuId);
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
