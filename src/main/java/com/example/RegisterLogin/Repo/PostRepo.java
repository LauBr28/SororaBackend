package com.example.RegisterLogin.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RegisterLogin.Entity.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

}