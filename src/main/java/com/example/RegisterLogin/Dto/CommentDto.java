package com.example.RegisterLogin.Dto;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private int commentId;
    private int postId; // ID del post al que pertenece el comentario
    private int userId; // ID del usuario que hizo el comentario
    private String username; // Nombre de usuario del que hizo el comentario
    private LocalDateTime dateTime; // Fecha y hora del comentario
    private String content; // Contenido del comentario
    private int likes; //
}