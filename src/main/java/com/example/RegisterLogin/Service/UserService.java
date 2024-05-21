package com.example.RegisterLogin.Service;
import java.util.List;
import java.util.Optional;

import com.example.RegisterLogin.Dto.CommentDto;
import com.example.RegisterLogin.Dto.LoginDto;
import com.example.RegisterLogin.Dto.UserDto;
import com.example.RegisterLogin.Dto.UserProfileDto;
import com.example.RegisterLogin.Entity.Mapa;
import com.example.RegisterLogin.Dto.MapaDto;
import com.example.RegisterLogin.Dto.PostDTO;
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

    PostDTO createPost(PostDTO postDto); // Método para crear un nuevo post
    List<PostDTO> getAllPosts(); // Método para obtener todos los posts
    void addCommentToPost(int postId, CommentDto commentDto); // Método para agregar un comentario a un post
    void likePost(int postId);

     // Método para actualizar un post existente
     void updatePost(int postId, PostDTO postDto);

     // Método para eliminar un post existente
     void deletePost(int postId);
}