package com.example.RegisterLogin.Dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private int postId;
    private int userId; // Id del usuario que subió el post
    private String username; // Nombre de usuario
    private LocalDateTime dateTime; // Fecha y hora de subida del post
    private int likes; // Número de likes
    private List<CommentDto> comments; // Lista de comentarios
    public int getPostId() {
        return postId;
    }
    public void setPostId(int postId) {
        this.postId = postId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
    public List<CommentDto> getComments() {
        return comments;
    }
    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

}

