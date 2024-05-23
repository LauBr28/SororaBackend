package com.example.RegisterLogin.UserController;
import com.example.RegisterLogin.Dto.UserDto;
import com.example.RegisterLogin.Dto.UserProfileDto;
import com.example.RegisterLogin.Entity.Mapa;
import com.example.RegisterLogin.Response.LoginResponse;

import com.example.RegisterLogin.Dto.LoginDto;
import com.example.RegisterLogin.Dto.MapaDto;
import com.example.RegisterLogin.Dto.PostDTO;
import com.example.RegisterLogin.Service.UserService;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/user")
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping(path = "/save")
    public String saveUser(@RequestBody UserDto userDto)
    {
        String id = userService.addUser(userDto);
        return id;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto)
    {
        LoginResponse loginResponse = userService.loginUser(loginDto);
        return ResponseEntity.ok(loginResponse);
    }
    @PostMapping(path = "/profile/update")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserProfileDto userProfileDto) {
        // Lógica para actualizar el perfil de usuario utilizando el UserService
        userService.updateUserProfile(userProfileDto);
        return ResponseEntity.ok("Profile updated successfully");
    
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestParam Integer userId) {
       UserDto userDto = userService.getUserProfileWithUserDetails(userId);
       return ResponseEntity.ok(userDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping("/connect/{userId}/{friendId}")
    public ResponseEntity<String> connectUsersAsFriends(@PathVariable int userId, @PathVariable int friendId) {
        userService.connectUsersAsFriends(userId, friendId);
        return ResponseEntity.ok("Usuarios conectados como amigos exitosamente.");
    }
    
    @GetMapping("/friends")
    public ResponseEntity<List<UserDto>> getFriendsByUserId(@RequestParam int userId) {
        List<UserDto> friends = userService.getFriendsByUserId(userId);
        return ResponseEntity.ok(friends);
    }


    @GetMapping("/friends/location")
    public ResponseEntity<List<MapaDto>> getFriendsLocationByUserId(@RequestParam int userId) {
        List<MapaDto> friendsLocation = userService.getFriendsLocationByUserId(userId);
        return ResponseEntity.ok(friendsLocation);
    }

    
    @PostMapping("/post/create")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDto) {
        PostDTO createdPost = userService.createPost(postDto);
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping("/posts/all")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> allPosts = userService.getAllPosts();
        return ResponseEntity.ok(allPosts);
    }

    @PostMapping("/location")
    public ResponseEntity<String> saveUserLocation(@RequestBody MapaDto mapaDto) {
        try {
            Optional<MapaDto> existingMapaOptional = userService.getMapaByUserId(mapaDto.getUserId());
            System.out.println("Existing Mapa Optional: " + existingMapaOptional);
            if (existingMapaOptional.isPresent()) {
                // Ya existe una ubicación, actualizarla
                MapaDto existingMapaDto = existingMapaOptional.get();
                System.out.println("Existing Mapa Optional: " + existingMapaDto);
                existingMapaDto.setLongitud(mapaDto.getLongitud());
                existingMapaDto.setLatitud(mapaDto.getLatitud());
                existingMapaDto.setDateTime(mapaDto.getDateTime());

                // Convertir el MapaDto actualizado a Mapa
                Mapa existingMapa = convertMapaDtoToEntity(existingMapaDto);
                System.out.println("Existing Mapa: " + existingMapa);

                userService.updateMapa(existingMapa, existingMapaDto);
                return ResponseEntity.ok("Location updated successfully");
            } else {
                // No existe una ubicación, guardarla como nueva
                userService.saveMapa(mapaDto);
                return ResponseEntity.ok("Location saved successfully");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving location: " + e.getMessage());
        }
    }

    // Método para convertir MapaDto a Mapa
    private Mapa convertMapaDtoToEntity(MapaDto mapaDto) {
        Mapa mapa = new Mapa();
        mapa.setLongitud(mapaDto.getLongitud());
        mapa.setLatitud(mapaDto.getLatitud());
        mapa.setDateTime(mapaDto.getDateTime());
        // Puedes establecer otras propiedades si es necesario
        return mapa;
    }

    @GetMapping("/mapa/details")
    public ResponseEntity<List<MapaDto>> getAllMapaDetails() {
        List<MapaDto> mapaDetails = userService.getAllMapaDetails();
        return ResponseEntity.ok(mapaDetails);
    }

    @PostMapping("/post/like/{postId}")
    public ResponseEntity<String> likePost(@PathVariable int postId) {
        try {
            userService.likePost(postId);
            return ResponseEntity.ok("Post liked successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error liking post: " + e.getMessage());
        }
    }

        // Actualizar un post existente
    @PostMapping("/post/update/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable int postId, @RequestBody PostDTO postDto) {
        try {
            userService.updatePost(postId, postDto);
            return ResponseEntity.ok("Post updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating post: " + e.getMessage());
        }
    }

    // Eliminar un post existente
    @DeleteMapping("/post/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable int postId) {
        try {
            userService.deletePost(postId);
            return ResponseEntity.ok("Post deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting post: " + e.getMessage());
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable int postId) {
        PostDTO postDto = userService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }


}