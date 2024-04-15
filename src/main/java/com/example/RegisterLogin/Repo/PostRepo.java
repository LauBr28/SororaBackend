package com.example.RegisterLogin.Repo;

import com.example.RegisterLogin.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

import java.util.Optional;

public interface PostRepo extends JpaRepository<Post, Integer> {

    // Añadir un nuevo post
    @Override
    @Transactional
    <S extends Post> S save(S post);

    // Editar un post existente
    @Override
    @Transactional
    <S extends Post> S saveAndFlush(S post);

    // Eliminar un post por su ID
    @Override
    @Transactional
    void deleteById(Integer id);

    // Obtener un post por su ID
    @Override
    Optional<Post> findById(Integer id);

    // Dar like a un post
    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.likes = p.likes + 1 WHERE p.id = :postId")
    void likePost(@Param("postId") int postId);

    // Reportar un post por su ID
    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.reported = true WHERE p.id = :postId")
    void reportPost(@Param("postId") int postId);
}
