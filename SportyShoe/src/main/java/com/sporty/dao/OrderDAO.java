package com.sporty.dao;

import java.util.List;

import com.sporty.pojo.Order;

public interface OrderDAO {
	
	public List<Order> getOrder();
	
	public Order getOrder(int orderId);
	
	public void saveOrder(Order order);
	
	public void deleteOrder(int orderId);
		
	public List<Order> getUserOrders(int userId);

}

