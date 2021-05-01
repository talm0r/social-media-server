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

    @Autowired
    UserService userService;
    @PostMapping("signup")
    public ApiResponse add(@RequestBody SignUpDto signUpDto, HttpServletRequest request) {
        ApiResponse response = this.userService.signUp(signUpDto);
        if(response.getStatus() == 200) {
            request.getSession().setAttribute("LOGGED_IN_USER",(User) response.getResult());
        }
        return response;
    }
    @PostMapping("login")
    public ApiResponse login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        ApiResponse response = this.userService.login(loginDto);
        return response;
    }
    @PostMapping("logout")
    public ApiResponse logout(HttpServletRequest request) {

        request.getSession().invalidate();
       return new ApiResponse(200,"Logged out successfully",null);
    }

    @GetMapping("getAllUsers")
    public ApiResponse getAllUsers() {
        System.out.println(1);
        return this.userService.getAllUsers();
    }

    @PutMapping("/update")
    public ApiResponse updateUser(@RequestBody UpdateUserDto updateUserDto) {
        return this.userService.updateUser(updateUserDto);
    }

}
