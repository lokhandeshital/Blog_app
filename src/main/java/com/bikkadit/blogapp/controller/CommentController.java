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
import com.bikkadit.blogapp.payload.CommentDto;
import com.bikkadit.blogapp.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class CommentController {

	@Autowired
	private CommentService commentService;

	/**
	 * @author Shital
	 * @apiNote This Api is used to create comment
	 * @param commentDto
	 * @param postId
	 * @param userId
	 * @return
	 */
	@PostMapping("comment/post/{postId}/user/{userId}")
	public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto,
			@PathVariable Long postId, @PathVariable Long userId) {
		log.info("Initiated request for create comment with postId:{},userId:{}", postId, userId);
		CommentDto createComment = this.commentService.createComment(commentDto, postId, userId);
		log.info("Completed request for create comment with postId:{},userId:{}", postId, userId);
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);

	}

	/**
	 * @apiNote This Api is used to delete comment
	 * @param commentId
	 * @return
	 */
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
		log.info("Initiated request for delete comment with commentId:{}", commentId);
		this.commentService.deleteComment(commentId);
		log.info("Completed request for delete comment with commentId:{}", commentId);
		return new ResponseEntity<String>(AppConstant.COMMENT_DELETE, HttpStatus.OK);

	}

	/**
	 * @apiNote This Api is used to get all comments
	 * @return
	 */
	@GetMapping("/comments")
	public ResponseEntity<List<CommentDto>> getAllComment() {

		log.info("Initiated request for get all comments");
		List<CommentDto> allComment = this.commentService.getAllComment();

		log.info("Completed request for get all commets");
		return new ResponseEntity<List<CommentDto>>(allComment, HttpStatus.OK);

	}

	/**
	 * @apiNote This Api is used to update Comment
	 * @param commentDto
	 * @param commentId
	 * @return
	 */
	@PutMapping("/comment/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@Valid @RequestBody CommentDto commentDto,
			@PathVariable Long commentId) {

		log.info("Initiated request fot update comment with commentId :{}", commentId);
		CommentDto updateComment = this.commentService.updateComment(commentDto, commentId);
		log.info("Completed request for update comment with commentId :{}", HttpStatus.OK);
		return new ResponseEntity<CommentDto>(updateComment, HttpStatus.OK);

	}
}
