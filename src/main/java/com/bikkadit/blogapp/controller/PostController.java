package com.bikkadit.blogapp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bikkadit.blogapp.exception.PostResponse;
import com.bikkadit.blogapp.helper.AppConstant;
import com.bikkadit.blogapp.payload.PostDto;
import com.bikkadit.blogapp.service.FileService;
import com.bikkadit.blogapp.service.PostService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	/**
	 * @author Shital
	 * @apiNote This Api is used to create post data
	 * @param postDto
	 * @param userId
	 * @param categoryId
	 * @return
	 */
	// Create Post
	@PostMapping("post/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Long userId,
			@PathVariable Long categoryId) {

		log.info("Initiated request for save the post details with userId:{},categoryId{}:", userId, categoryId);
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		log.info("Completed request for save the post details with userId:{},categoryId{}:", userId, categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}

	/**
	 * @apiNote This Api is used for update post details
	 * @param postDto
	 * @param postId
	 * @return
	 */
	// Update Post
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Long postId) {
		log.info("Initiated request for update the post details with postId:{}", postId);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		log.info("Completed request for update the post details with postId:{}", postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

	}

	/**
	 * @apiNote This Api is used to get single post details
	 * @param postId
	 * @return
	 */
	// Get Post
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPost(@Valid @PathVariable Long postId) {
		log.info("Initiated request for get the single post with postId:{}", postId);
		PostDto post = this.postService.getPost(postId);
		log.info("Completed request for get the single post with postId:{}", postId);
		return new ResponseEntity<PostDto>(post, HttpStatus.FOUND);

	}

	/**
	 * @apiNote This Api is used to get All post details
	 * @param pageNumber
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * @return
	 */
	// Get All Post
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {

		log.info("Initiated request for get all post with pageNumber:{},pageSize:{},sortBy:{},sortDir:{}", pageNumber,
				pageSize, sortBy, sortDir);
		PostResponse allPost = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);

		log.info("Completed request for get all post with pageNumber:{},pageSize:{},sortBy:{},sortDir:{}", pageNumber,
				pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(allPost, HttpStatus.FOUND);

	}

	/**
	 * @apiNote This Api is used to delete the post
	 * @param postId
	 * @return
	 */
	// Delete Post
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<String> deletePost(@PathVariable Long postId) {
		log.info("Initiated request for delete post with postId:{}", postId);
		this.postService.deletePost(postId);
		log.info("Completed request for delete post with postId:{}", postId);
		return new ResponseEntity<String>(AppConstant.POST_DELETE, HttpStatus.OK);

	}

	/**
	 * @apiNote This Api is used to get post by userId
	 * @param userId
	 * @return
	 */
	// Get By User
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Long userId) {
		log.info("Initiated request for get post by user with userId:{}", userId);
		List<PostDto> posts = this.postService.getPostByUser(userId);
		log.info("Completed request for get post by user with userId:{}", userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}

	/**
	 * @apiNote This Api is used to get post by categoryId
	 * @param categoryId
	 * @return
	 */
	// Get By category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Long categoryId) {
		log.info("Initiated request for get post by categoryId:{}", categoryId);
		List<PostDto> posts = this.postService.getPostByCategory(categoryId);
		log.info("Completed request for get post by categoryId:{}", categoryId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}

	/**
	 * @apiNote This Api is used to search post by keyword
	 * @param keyword
	 * @return
	 */
	// Searching Posts
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword") String keyword) {
		log.info("Initiated request for search posts with keyword:{}", keyword);
		List<PostDto> searchPosts = this.postService.searchPosts(keyword);
		log.info("Completed request for search posts with keyword:{}", keyword);
		return new ResponseEntity<List<PostDto>>(searchPosts, HttpStatus.OK);

	}

	/**
	 * @apiNote This Api is used to upload the image on post
	 * @param image
	 * @param postId
	 * @return
	 * @throws IOException
	 */
	// Post Image Upload
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam MultipartFile image, @PathVariable Long postId)
			throws IOException {
		log.info("Initiated request for upload the image with postId:{}", postId);
		PostDto postDto = this.postService.getPost(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		log.info("Completed request for upload the image with postId:{}", postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

	}

	/**
	 * @apiNote This Api/Method is used to serve the file
	 * @param imageName
	 * @param response
	 * @throws IOException
	 */
	// Method To serve File
	@GetMapping(value = "/posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException {
		log.info("Initiated request for serve file with imageName:{},response:{}", imageName, response);
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		log.info("Completed request for serve file with imageName:{},response:{}", imageName, response);
		StreamUtils.copy(resource, response.getOutputStream());

	}

}
