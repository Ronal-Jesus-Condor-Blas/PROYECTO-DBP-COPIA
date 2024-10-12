package com.proyecto_dbp.comment.domain;

import com.proyecto_dbp.exception.ResourceNotFoundException;
import com.proyecto_dbp.exception.ValidationException;
import com.proyecto_dbp.comment.dto.CommentRequestDto;
import com.proyecto_dbp.comment.dto.CommentResponseDto;
import com.proyecto_dbp.comment.infrastructure.CommentRepository;
import com.proyecto_dbp.post.domain.Post;
import com.proyecto_dbp.post.infrastructure.PostRepository;
import com.proyecto_dbp.user.domain.User;
import com.proyecto_dbp.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public CommentResponseDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));
        return mapToDto(comment);
    }

    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostPostId(postId);
        return comments.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        if (commentRequestDto.getContent() == null || commentRequestDto.getContent().isEmpty()) {
            throw new ValidationException("Content cannot be null or empty");
        }
        Comment comment = mapToEntity(commentRequestDto);
        comment.setCommentDate(LocalDateTime.now());
        comment = commentRepository.save(comment);
        return mapToDto(comment);
    }

    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));
        if (commentRequestDto.getContent() == null || commentRequestDto.getContent().isEmpty()) {
            throw new ValidationException("Content cannot be null or empty");
        }
        comment.setContent(commentRequestDto.getContent());
        comment = commentRepository.save(comment);
        return mapToDto(comment);
    }

    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comment not found with id " + id);
        }
        commentRepository.deleteById(id);
    }

    private CommentResponseDto mapToDto(Comment comment) {
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setCommentId(comment.getCommentId());
        commentResponseDto.setContent(comment.getContent());
        commentResponseDto.setCommentDate(comment.getCommentDate());
        commentResponseDto.setUserId(comment.getUser().getUserId());
        commentResponseDto.setPostId(comment.getPost().getPostId());
        return commentResponseDto;
    }

    private Comment mapToEntity(CommentRequestDto commentRequestDto) {
        Comment comment = new Comment();
        comment.setContent(commentRequestDto.getContent());

        User user = userRepository.findById(commentRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + commentRequestDto.getUserId()));
        comment.setUser(user);

        Post post = postRepository.findById(commentRequestDto.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + commentRequestDto.getPostId()));
        comment.setPost(post);

        return comment;
    }
}