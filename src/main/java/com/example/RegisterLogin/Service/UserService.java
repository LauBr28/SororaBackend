package com.example.RegisterLogin.Service;


import java.util.List;
import java.util.Optional;

import com.example.RegisterLogin.Dto.LoginDto;
import com.example.RegisterLogin.Dto.UserDto;
import com.example.RegisterLogin.Dto.UserProfileDto;
import com.example.RegisterLogin.Dto.MapaDto;
import com.example.RegisterLogin.Entity.Mapa;
import com.example.RegisterLogin.Entity.Post;
import com.example.RegisterLogin.Response.LoginResponse;


public interface UserService {
    String addUser(UserDto userDto);
    LoginResponse loginUser(LoginDto loginDTO);
    void updateUserProfile(UserProfileDto userProfileDto);
    UserProfileDto getUserProfile(Integer userId);
    UserDto getUserProfileWithUserDetails(Integer userId);
    List<UserDto> getAllUsers();
    void connectUsersAsFriends(int userId, int friendId);
    List<UserDto> getFriendsByUserId(int userId);
    Optional<MapaDto> getMapaByUserId(int userId);
    List<MapaDto> getFriendsLocationByUserId(int userId); 
    void saveMapa(MapaDto mapaDto);
    void updateMapa(Mapa existingMapa, MapaDto mapaDto);
    List<MapaDto> getAllMapaDetails();

    /*Posts */
    Post addPost(Post post);
    Post editPost(Post post);
    void deletePost(int id);
    void likePost(int postId);
    Optional<Post> getPostById(int id);
}