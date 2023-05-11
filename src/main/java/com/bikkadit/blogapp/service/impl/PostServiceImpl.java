package com.bikkadit.blogapp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bikkadit.blogapp.exception.PostResponse;
import com.bikkadit.blogapp.exception.ResourceNotFoundException;
import com.bikkadit.blogapp.model.Category;
import com.bikkadit.blogapp.model.Post;
import com.bikkadit.blogapp.model.User;
import com.bikkadit.blogapp.payload.PostDto;
import com.bikkadit.blogapp.repository.CategoryRepository;
import com.bikkadit.blogapp.repository.PostRepository;
import com.bikkadit.blogapp.repository.UserRepository;
import com.bikkadit.blogapp.service.PostService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CategoryRepository cateRepo;

	/**
	 * @author Shital
	 * @implNote This impl create the post
	 */
	@Override
	public PostDto createPost(PostDto postdto, Long userId, Long categoryId) {

		log.info("Initiated request for save the post details with userId:{},categoryId{}:", userId, categoryId);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Category category = this.cateRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

		Post post = this.mapper.map(postdto, Post.class);
		post.setImageName("Defaul.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post save = this.postRepo.save(post);
		log.info("Completed request for save the post details with userId:{},categoryId{}:", userId, categoryId);
		PostDto createPost = this.mapper.map(save, PostDto.class);
		return createPost;
	}

	/**
	 * @implNote This impl update the post
	 */
	@Override
	public PostDto updatePost(PostDto postdto, Long postId) {
		log.info("Initiated request for update the post details with postId:{}", postId);
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		post.setTitle(postdto.getTitle());
		post.setContent(postdto.getContent());
		post.setImageName(postdto.getImageName());

		Post save = this.postRepo.save(post);

		PostDto updatePost = this.mapper.map(save, PostDto.class);
		log.info("Completed request for update the post details with postId:{}", postId);
		return updatePost;
	}

	/**
	 * @implNote This impl get the single post
	 */
	@Override
	public PostDto getPost(Long postId) {

		log.info("Initiated request for get the single post with postId:{}", postId);
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		log.info("Completed request for get the single post with postId:{}", postId);
		return this.mapper.map(post, PostDto.class);
	}

	/**
	 * @implNote This impl get all the post
	 */
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		log.info("Initiated request for get all post with pageNumber:{},pageSize:{},sortBy:{},sortDir:{}", pageNumber,
				pageSize, sortBy, sortDir);
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable page = PageRequest.of(pageNumber, pageSize, sort);

		Page<Post> pagePost = this.postRepo.findAll(page);

		List<Post> allList = pagePost.getContent();

		List<PostDto> postDto = allList.stream().map((post) -> mapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		log.info("Completed request for get all post with pageNumber:{},pageSize:{},sortBy:{},sortDir:{}", pageNumber,
				pageSize, sortBy, sortDir);
		return postResponse;
	}

	/**
	 * @implNote This impl delete the post
	 */
	@Override
	public void deletePost(Long postId) {
		log.info("Initiated request for delete post with postId:{}", postId);
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		log.info("Completed request for delete post with postId:{}", postId);
		this.postRepo.delete(post);

	}

	/**
	 * @implNote This impl get post by categoryId
	 */
	@Override
	public List<PostDto> getPostByCategory(Long categoryId) {

		log.info("Initiated request for get post by categoryId:{}", categoryId);
		Category category = this.cateRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

		List<Post> listPost = this.postRepo.findByCategory(category);

		List<PostDto> postDtos = listPost.stream().map((post) -> this.mapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Completed request for get post by categoryId:{}", categoryId);
		return postDtos;
	}

	/**
	 * @implNote This impl get post by userId
	 */
	@Override
	public List<PostDto> getPostByUser(Long userId) {

		log.info("Initiated request for get post by user with userId:{}", userId);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		List<Post> listUser = this.postRepo.findByUser(user);

		List<PostDto> listOfPostDto = listUser.stream().map((post) -> this.mapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Completed request for get post by user with userId:{}", userId);
		return listOfPostDto;
	}

	/**
	 * @implNote This impl search post
	 */
	@Override
	public List<PostDto> searchPosts(String keyword) {
		log.info("Initiated request for search posts with keyword:{}", keyword);
		List<Post> listPost = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = listPost.stream().map((post) -> this.mapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Completed request for search posts with keyword:{}", keyword);
		return postDtos;
	}

}
