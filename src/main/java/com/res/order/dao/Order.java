package com.res.order.dao;

public class Order {
	
	private int orderId;
	private String orderMenuName;
	private int orderQuantity;
	private int orderItemPrice;
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getOrderMenuName() {
		return orderMenuName;
	}
	public void setOrderMenuName(String orderMenuName) {
		this.orderMenuName = orderMenuName;
	}
	public int getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public int getOrderItemPrice() {
		return orderItemPrice;
	}
	public void setOrderItemPrice(int orderItemPrice) {
		this.orderItemPrice = orderItemPrice;
	}
	
	
	

}
