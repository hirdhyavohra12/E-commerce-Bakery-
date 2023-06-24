package com.website.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.website.dao.ProductDao;
import com.website.model.Category;
import com.website.model.Product;
import com.website.repository.ProductRepository;
import com.website.service.CategoryService;
import com.website.service.ProductService;

@Controller
public class AdminController {
	
	public static String uploadUri=System.getProperty("user.dir")+"/src/main/resources/static/productImages";
//	public static String uploadUri="C:\\Users\\Asus\\Documents\\workspace-spring-tool-suite-4-4.18.1.RELEASE\\E-commerce2\\src\\main\\resources\\static\\productImages";
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;

	@GetMapping("/admin")
	public String admin() {
		return "adminHome";
	}
	
	@GetMapping("/admin/categories")
	public String getCategories(Model model) {
		model.addAttribute("categories",categoryService.getAllCategory());
		return "categories";
	}
	
	@GetMapping("/admin/categories/add")
	public String getCategoriesAdd(Model model) {
		model.addAttribute("category",new Category());
		return "categoriesAdd";
	}
	
	@PostMapping("/admin/categories/add")
	public String postCategoriesAdd(@ModelAttribute("category") Category category) {
		this.categoryService.addCategory(category);
		
		return "redirect:/admin/categories";
	}
	
	@GetMapping("admin/categories/delete/{id}")
	public String deleteCategory(@PathVariable int id) {
		this.categoryService.deleteCategory(id);
		
		return "redirect:/admin/categories";
	}
	
	@GetMapping("admin/categories/update/{id}")
	public String updateCategory(@PathVariable int id , Model model)
	{
		Optional<Category> category=categoryService.getCategory(id);
		if(category.isPresent()) {
			model.addAttribute("category",category.get());
			return "categoriesAdd";
		}
		
		return "categories";
	}
	
	@GetMapping("/admin/products")
	public String getProduct(Model model) {
		model.addAttribute("products",productService.getAllProduct());
		return "products";
	}
	
	@GetMapping("/admin/products/add")
	public String getProductAdd(Model model) {
		model.addAttribute("productDTO",new ProductDao());
		model.addAttribute("categories",categoryService.getAllCategory());
		return "productsAdd";
	}
	
	@PostMapping("/admin/products/add")
	public String postProductAdd(@ModelAttribute("productDTO") ProductDao productDTO,@RequestParam("productImage") MultipartFile file
			,@RequestParam("imgName") String imgName)  throws IOException{
		
		Product product=new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getCategory(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		
		String imageUUID;
		if(!file.isEmpty()) {
			imageUUID=file.getOriginalFilename();
			Path fileNameAndPath=Paths.get(uploadUri,imageUUID);
			Files.write(fileNameAndPath,file.getBytes());
		}else {
			imageUUID=imgName;
		}
		
		product.setImageName(imageUUID);
		productService.addProducts(product);
		
		return "redirect:/admin/products";
	}
	
	
	@GetMapping("admin/product/delete/{id}")
	public String deleteProduct(@PathVariable long id) {
		this.productService.deleteProductById(id);		
		return "redirect:/admin/products";
	}
	
	@GetMapping("admin/product/update/{id}")
	public String updateProduct(@PathVariable long id,Model model) {
		
		Product product=productService.getProductById(id).get();
		ProductDao productDTO=new ProductDao();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId(product.getCategory().getId());
		productDTO.setPrice(product.getPrice());
		productDTO.setWeight(product.getWeight());
		productDTO.setDescription(product.getDescription());
		productDTO.setImageName(product.getImageName());
		
		
		model.addAttribute("categories",categoryService.getAllCategory());
		model.addAttribute("productDTO",productDTO);
		
		
		
		
		return "productsAdd";
	}
}
