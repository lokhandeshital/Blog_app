package com.bikkadit.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bikkadit.blogapp.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
