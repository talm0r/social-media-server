package com.example.notesapp.controllers;


import com.example.notesapp.beans.User;
import com.example.notesapp.dtos.notesDtos.UpdateNoteDto;
import com.example.notesapp.dtos.userDtos.LoginDto;
import com.example.notesapp.dtos.userDtos.SignUpDto;
import com.example.notesapp.dtos.userDtos.UpdateUserDto;
import com.example.notesapp.services.UserService;
import com.example.notesapp.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "*")
public class UserController {

    String message = "Could not";
    @Autowired
    UserService userService;
    @PostMapping("signup")
    public ApiResponse add(@RequestBody SignUpDto signUpDto, HttpServletRequest request) {
        try {
            return this.userService.signUp(signUpDto);
        }
        catch (Exception e){
            return new ApiResponse(400,message + "Add user",e.getMessage());
        }

    }
    @PostMapping("login")
    public ApiResponse login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        try {
            return  this.userService.login(loginDto);
        }
        catch (Exception e){
            return new ApiResponse(400,message + "login",e.getMessage());
        }

    }


    @GetMapping("getAllUsers")
    public ApiResponse getAllUsers() {
        try {
            return this.userService.getAllUsers();
        }
        catch (Exception e){
            return new ApiResponse(400,message + "get all users",e.getMessage());
        }

    }

    @PutMapping("/update")
    public ApiResponse updateUser(@RequestBody UpdateUserDto updateUserDto) {
        try {
            return this.userService.updateUser(updateUserDto);
        }
        catch (Exception e){
            return new ApiResponse(400,message + "update user",e.getMessage());
        }

    }

}
