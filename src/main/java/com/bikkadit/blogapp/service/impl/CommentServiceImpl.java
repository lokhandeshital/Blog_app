package com.bikkadit.blogapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikkadit.blogapp.exception.ResourceNotFoundException;
import com.bikkadit.blogapp.model.Comment;
import com.bikkadit.blogapp.model.Post;
import com.bikkadit.blogapp.model.User;
import com.bikkadit.blogapp.payload.CommentDto;
import com.bikkadit.blogapp.repository.CommentRepository;
import com.bikkadit.blogapp.repository.PostRepository;
import com.bikkadit.blogapp.repository.UserRepository;
import com.bikkadit.blogapp.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CommentRepository commentRepo;

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private UserRepository userRepo;

	/**
	 * @author Shital
	 * @implNote This impl is used to create comment
	 */
	@Override
	public CommentDto createComment(CommentDto commentDto, Long postId, Long userId) {

		log.info("Initiated request for create comment with postId:{},userId:{}", postId, userId);
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Comment comment = this.mapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);

		Comment save = this.commentRepo.save(comment);
		log.info("Completed request for create comment with postId:{},userId:{}", postId, userId);
		CommentDto createComment = this.mapper.map(save, CommentDto.class);

		return createComment;
	}

	/**
	 * @implNote This impl is used to delete the comment
	 */
	@Override
	public void deleteComment(Long commentId) {
		log.info("Initiated request for delete comment with commentId:{}", commentId);
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		log.info("Completed request for delete comment with commentId:{}", commentId);
		this.commentRepo.delete(comment);

	}

	/**
	 * @implNote This impl is used to update comment
	 */
	@Override
	public CommentDto updateComment(CommentDto commentDto, Long commentId) {

		log.info("Initiated request for update comment with commentId :{}", commentId);
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		comment.setContent(commentDto.getContent());

		Comment save = this.commentRepo.save(comment);

		log.info("Completed request for update comment with commentId :{}", commentId);
		CommentDto updateCommment = this.mapper.map(save, CommentDto.class);
		return updateCommment;
	}

	/**
	 * @implNote This impl is used to get all comments
	 */
	@Override
	public List<CommentDto> getAllComment() {

		log.info("Initiated request for get all comment");
		List<Comment> findAll = this.commentRepo.findAll();

		log.info("Completed request for get all comment");
		List<CommentDto> allComment = findAll.stream().map((comment) -> mapper.map(comment, CommentDto.class))
				.collect(Collectors.toList());

		return allComment;
	}

}
