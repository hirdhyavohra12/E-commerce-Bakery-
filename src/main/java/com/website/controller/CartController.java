package com.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.website.global.GlobalData;
import com.website.model.Product;
import com.website.service.ProductService;

@Controller
public class CartController {

	@Autowired
	ProductService productService;
	
	
	@GetMapping("/addToCart/{id}")
	public String addToCart(@PathVariable long id) {
		GlobalData.cart.add(productService.getProductById(id).get());
		return "redirect:/shop";
	}
	
	@GetMapping("/cart")
	public String getCart(Model model) {
		model.addAttribute("cartCount",GlobalData.cart.size());
		model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrice ).sum());
		model.addAttribute("cart",GlobalData.cart);
		return "cart";
	}
	
	@GetMapping("/cart/removeItem/{index}")
	public String removeCartItems(@PathVariable int index) {
		GlobalData.cart.remove(index);
		return "redirect:/cart";
	}
	
	@GetMapping("/checkout")
	public String checkout(Model model) {
		model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrice ).sum());
		return "checkout";
		
	}
}
