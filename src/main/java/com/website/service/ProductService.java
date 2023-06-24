package com.website.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.website.model.Product;
import com.website.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	public List<Product> getAllProduct(){
		return productRepository.findAll();
	}
	
	public void addProducts(Product product) {
		this.productRepository.save(product);
		
	}
	
	public void deleteProductById(Long id) {
		this.productRepository.deleteById(id);
	}
	
	public Optional<Product> getProductById(Long id) {
		return this.productRepository.findById(id);
	}
	
	
	public List<Product> getAllProductByCategory_id(int id){
		return productRepository.findAllByCategory_Id(id);
	}

}
