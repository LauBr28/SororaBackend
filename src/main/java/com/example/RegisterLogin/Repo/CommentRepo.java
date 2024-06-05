package com.example.RegisterLogin.Repo;
import com.example.RegisterLogin.Entity.Comment;
import com.example.RegisterLogin.Entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {
    List<Comment> findByPost(Post post);
}