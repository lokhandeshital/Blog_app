package com.bikkadit.blogapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikkadit.blogapp.exception.ResourceNotFoundException;
import com.bikkadit.blogapp.model.Category;
import com.bikkadit.blogapp.payload.CategoryDto;
import com.bikkadit.blogapp.repository.CategoryRepository;
import com.bikkadit.blogapp.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CategoryRepository categoryRepository;

	/**
	 * @author Shital
	 * @implNote This impl is used to create category
	 */
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		log.info("Initiated request for create category");
		Category category = this.mapper.map(categoryDto, Category.class);
		Category save = this.categoryRepository.save(category);
		log.info("Completed request for create category");
		CategoryDto createCategory = this.mapper.map(save, CategoryDto.class);
		return createCategory;
	}

	/**
	 * @implNote This impl is used to update category
	 */
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
		log.info("Initiated request for update category with categoryId:{}", categoryId);
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());

		this.categoryRepository.save(category);
		log.info("Complted request for update category with categoryId:{}", categoryId);
		CategoryDto updateCategory = this.mapper.map(category, CategoryDto.class);

		return updateCategory;
	}

	/**
	 * @implNote This impl is used to get single category
	 */
	@Override
	public CategoryDto getCategory(Long categoryId) {

		log.info("Initiated request to get single category with categoryId:{}", categoryId);
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		log.info("Completed request to get single category with categoryId:{}", categoryId);
		return this.mapper.map(category, CategoryDto.class);
	}

	/**
	 * @implNote This impl is used to get all category
	 */
	@Override
	public List<CategoryDto> getAllCategory() {

		log.info("Initiated request to get all category");
		List<Category> findAll = this.categoryRepository.findAll();
		log.info("Completed request to get all category");
		List<CategoryDto> allList = findAll.stream().map((categories) -> mapper.map(categories, CategoryDto.class))
				.collect(Collectors.toList());
		return allList;
	}

	/**
	 * @implNote This impl is used to delete category
	 */
	@Override
	public void deleteCategory(Long categoryId) {
		log.info("Initiated request to delete category");
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		log.info("Completed request to delete category");
		this.categoryRepository.delete(category);

	}

}
