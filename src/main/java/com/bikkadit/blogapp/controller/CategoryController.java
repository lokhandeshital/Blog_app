package com.bikkadit.blogapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikkadit.blogapp.helper.AppConstant;
import com.bikkadit.blogapp.payload.CategoryDto;
import com.bikkadit.blogapp.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	/**
	 * @author Shital
	 * @apiNote This Api is used to create category
	 * @param categoryDto
	 * @return
	 */

	@PostMapping("/category")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		log.info("Initiated request for create category");
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		log.info("Completed request for create category");
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);

	}

	/**
	 * @apiNote This Api is used to update category
	 * @param categoryDto
	 * @param categoryId
	 * @return
	 */
	@PutMapping("/category/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable Long categoryId) {
		log.info("Initiated request for update category with categoryId:{}", categoryId);
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, categoryId);
		log.info("Complted request for update category with categoryId:{}", categoryId);
		return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);

	}

	/**
	 * @apiNote This Api is used to get single category
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@Valid @PathVariable Long categoryId) {
		log.info("Initiated request to get single category with categoryId:{}", categoryId);
		CategoryDto getCategory = this.categoryService.getCategory(categoryId);
		log.info("Completed request to get single category with categoryId:{}", categoryId);
		return new ResponseEntity<CategoryDto>(getCategory, HttpStatus.FOUND);

	}

	/**
	 * @apiNote This Api is used to get all category
	 * @return
	 */
	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		log.info("Initiated request to get all category");
		List<CategoryDto> allCategory = this.categoryService.getAllCategory();
		log.info("Completed request to get all category");
		return new ResponseEntity<List<CategoryDto>>(allCategory, HttpStatus.FOUND);

	}

	/**
	 * @apiNote This Api is used to delete category
	 * @param categoryId
	 * @return
	 */
	@DeleteMapping("/category/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
		log.info("Initiated request to delete category");
		this.categoryService.deleteCategory(categoryId);
		log.info("Completed request to delete category");
		return new ResponseEntity<String>(AppConstant.CATEGORY_DELETE, HttpStatus.OK);

	}

}
