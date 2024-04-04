package com.example.RegisterLogin.Service;


import java.util.List;

import com.example.RegisterLogin.Dto.LoginDto;
import com.example.RegisterLogin.Dto.UserDto;
import com.example.RegisterLogin.Dto.UserProfileDto;
import com.example.RegisterLogin.Response.LoginResponse;


public interface UserService {
    String addUser(UserDto userDto);
    LoginResponse loginUser(LoginDto loginDTO);
    void updateUserProfile(UserProfileDto userProfileDto);
    UserProfileDto getUserProfile(Integer userId);
    UserDto getUserProfileWithUserDetails(Integer userId);
    List<UserDto> getAllUsers();
}
