package com.example.RegisterLogin.Dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

}
