package com.sporty.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sporty.dao.OrderDAO;
import com.sporty.dao.ProductDAO;
import com.sporty.pojo.Order;
import com.sporty.pojo.Product;
import com.sporty.pojo.User;

@Controller
@RequestMapping("/homepage")
public class HomePageController {

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private OrderDAO orderDAO;

	@GetMapping("/products")
	public String products(Model theModel, HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		User currentUser = (User) session.getAttribute("currentUser");

		List<Product> products = productDAO.getProducts();

		 
		List<Order> userOrders = orderDAO.getUserOrders(currentUser.getId());
		List<Integer> userProducts = new ArrayList<Integer>();

		for (Order order : userOrders) {
			userProducts.add(order.getProduct().getId());
		}

		// add the products to the model
		theModel.addAttribute("products", products);
		theModel.addAttribute("userProducts", userProducts);
		theModel.addAttribute("currentUser", currentUser);

		return "user-home";
	}

	@GetMapping("/orderProcess")
	public String orderProcess(Model theModel,@RequestParam("productId") int productId, HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		User currentUser = (User) session.getAttribute("currentUser");
		Product product = productDAO.getProduct(productId);

		Order order = new Order(currentUser, product);

		orderDAO.saveOrder(order);
		
		theModel.addAttribute("currentUser", currentUser);

		return "redirect:/homepage/products";
	}

	@GetMapping("/mycart")
	public String showMyCart(Model theModel, HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		User currentUser = (User) session.getAttribute("currentUser");

		// get all products from DAO
		List<Order> userOrders = orderDAO.getUserOrders(currentUser.getId());
		List<Product> userProducts = new ArrayList<Product>();

		for (Order order : userOrders) {
			userProducts.add(order.getProduct());
		}

		int total_price = 0;

		for (Product product : userProducts) {
			total_price = total_price + product.getPrice();
		}

		theModel.addAttribute("userProducts", userProducts);
		theModel.addAttribute("total_price", total_price);
		theModel.addAttribute("currentUser", currentUser);

		return "mycart";
	}

	@PostMapping("/searchProducts")
	public String searchProducts(HttpServletRequest request, Model theModel, @RequestParam("keySearch") String key) {

		HttpSession session = request.getSession(true);
		User currentUser = (User) session.getAttribute("currentUser");

		System.out.println(key);
		List<Product> products = productDAO.searchProducts(key);
		 
		  
		if (currentUser.getType() == 0) {
			List<Order> userOrders = orderDAO.getUserOrders(currentUser.getId());
			List<Integer> userProducts = new ArrayList<Integer>();

			for (Order order : userOrders) {
				userProducts.add(order.getProduct().getId());
			
		}
		
			theModel.addAttribute("userProducts", userProducts);
			theModel.addAttribute("currentUser", currentUser);
		}
		theModel.addAttribute("products", products);

		
		if (currentUser.getType() == 0) {
			return "user-home";
		}
		
		else {
			return "mange-products";
		}

		
	}

}
