package com.example.RegisterLogin.Repo;

import com.example.RegisterLogin.Entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface PostCommentRepo extends JpaRepository<PostComment, Integer> {

    // Añadir un nuevo comentario a un post
    @Override
    @Transactional
    <S extends PostComment> S save(S comment);

    // Editar un comentario existente
    @Override
    @Transactional
    <S extends PostComment> S saveAndFlush(S comment);

    // Eliminar un comentario por su ID
    @Override
    @Transactional
    void deleteById(Integer id);

    // Obtener un comentario por su ID
    @Override
    Optional<PostComment> findById(Integer id);

     // Obtener todos los comentarios de un post por su ID
     @Query("SELECT c FROM PostComment c WHERE c.post.id = :postId")
     List<PostComment> findAllByPostId(@Param("postId") Integer postId);

}