package com.bikkadit.blogapp.service;

import java.util.List;

import com.bikkadit.blogapp.payload.CategoryDto;

public interface CategoryService {

	// Create
	public CategoryDto createCategory(CategoryDto categoryDto);

	// Update
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);

	// Get
	public CategoryDto getCategory(Long categoryId);

	// getAll
	public List<CategoryDto> getAllCategory();

	// Delete
	public void deleteCategory(Long categoryId);

}
