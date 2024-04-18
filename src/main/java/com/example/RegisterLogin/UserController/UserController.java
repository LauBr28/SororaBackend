package com.example.RegisterLogin.UserController;
import com.example.RegisterLogin.Dto.UserDto;
import com.example.RegisterLogin.Dto.UserProfileDto;
import com.example.RegisterLogin.Entity.Post;
import com.example.RegisterLogin.Response.LoginResponse;
import com.example.RegisterLogin.Dto.LoginDto;
import com.example.RegisterLogin.Dto.MapaDto;
import com.example.RegisterLogin.Service.UserService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

     @PostMapping("/addPost")
    public ResponseEntity<Post> addPost(@RequestBody Post post) {
        Post addedPost = userService.addPost(post);
        return ResponseEntity.ok(addedPost);
    }

    @PutMapping("/editPost")
    public ResponseEntity<Post> editPost(@RequestBody Post post) {
        Post editedPost = userService.editPost(post);
        return ResponseEntity.ok(editedPost);
    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<String> deletePost(@PathVariable int id) {
        userService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @PatchMapping("/likePost/{postId}")
    public ResponseEntity<String> likePost(@PathVariable int postId) {
        userService.likePost(postId);
        return ResponseEntity.ok("Post liked successfully");
    }

    @GetMapping("FindPost/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable int id) {
        Optional<Post> post = userService.getPostById(id);
        return post.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/friends/location")
    public ResponseEntity<List<MapaDto>> getFriendsLocationByUserId(@RequestParam int userId) {
        List<MapaDto> friendsLocation = userService.getFriendsLocationByUserId(userId);
        return ResponseEntity.ok(friendsLocation);
    }

    @PostMapping("/location")
    public ResponseEntity<String> saveUserLocation(@RequestBody MapaDto mapaDto) {
        userService.saveMapa(mapaDto);
        return ResponseEntity.ok("Location saved successfully");
    }

}