package com.proyecto_dbp.comment.domain;

import com.proyecto_dbp.comment.domain.Comment;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository; // Add UserRepository

    @Autowired
    private PostRepository postRepository; // Add PostRepository

    public CommentResponseDto getCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.map(this::mapToDto).orElse(null);
    }

    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        Comment comment = mapToEntity(commentRequestDto);
        comment.setCommentDate(LocalDateTime.now()); // Set comment date
        comment = commentRepository.save(comment);
        return mapToDto(comment);
    }

    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setContent(commentRequestDto.getContent());
            comment = commentRepository.save(comment);
            return mapToDto(comment);
        }
        return null;
    }

    public void deleteComment(Long id) {
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

        // Fetch user and post from their respective repositories
        User user = userRepository.findById(commentRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        comment.setUser(user);

        Post post = postRepository.findById(commentRequestDto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));
        comment.setPost(post);

        return comment;
    }
}