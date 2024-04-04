package com.example.RegisterLogin.UserController;
import com.example.RegisterLogin.Dto.UserDto;
import com.example.RegisterLogin.Dto.UserProfileDto;
import com.example.RegisterLogin.Response.LoginResponse;
import com.example.RegisterLogin.Dto.LoginDto;
import com.example.RegisterLogin.Service.UserService;


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

    
}