package com.example.RegisterLogin.Repo;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.RegisterLogin.Entity.Comment;

import jakarta.transaction.Transactional;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {
  // Método personalizado para agregar un comentario
  @Modifying
  @Transactional
  @Query(value = "INSERT INTO comment (post_id, user_id, username, datetime, content) " +
                 "VALUES (:postId, :userId, :username, :dateTime, :content)",
         nativeQuery = true)
  void addComment(@Param("postId") int postId,
                  @Param("userId") int userId,
                  @Param("username") String username,
                  @Param("dateTime") LocalDateTime dateTime,
                  @Param("content") String content);
}