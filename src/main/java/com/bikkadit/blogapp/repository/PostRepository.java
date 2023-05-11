package com.bikkadit.blogapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bikkadit.blogapp.model.Category;
import com.bikkadit.blogapp.model.Post;
import com.bikkadit.blogapp.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

	List<Post> findByUser(User user); 
	
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title);
}
