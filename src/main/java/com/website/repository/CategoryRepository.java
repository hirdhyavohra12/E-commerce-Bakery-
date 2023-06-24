package com.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.website.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	

}
