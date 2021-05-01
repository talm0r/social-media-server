package com.example.notesapp.services;


import com.example.notesapp.beans.Note;
import com.example.notesapp.beans.User;
import com.example.notesapp.dtos.notesDtos.UpdateNoteDto;
import com.example.notesapp.dtos.userDtos.IGetUserDto;
import com.example.notesapp.dtos.userDtos.LoginDto;
import com.example.notesapp.dtos.userDtos.SignUpDto;
import com.example.notesapp.dtos.userDtos.UpdateUserDto;
import com.example.notesapp.repo.UserRepository;
import com.example.notesapp.utils.ApiResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    public ApiResponse signUp(SignUpDto signUpDto) {
       Optional<User> checkIfEmailExistsOptional = userRepo.findByUserEmail(signUpDto.getUserEmail());

        if(checkIfEmailExistsOptional.isEmpty()) {
            User user = new User();
            BeanUtils.copyProperties(signUpDto,user);
            userRepo.save(user);

            return new ApiResponse(200, "success", user);
        }
        else {
            return new ApiResponse(409,"user email already exists",null);
        }

    }
    // get both user and password
    public ApiResponse login(LoginDto loginDto) {
        // For security reasons im making a global error for both cases of wrong mail\password
        String errorMsg = "Wrong username/password, please try again";


        Optional<User> getUserByEmail = userRepo.findByUserEmail(loginDto.getUserEmail());
        System.out.println(getUserByEmail);
        System.out.println(loginDto.getUserEmail());
        if(getUserByEmail.isEmpty()) {
            return new ApiResponse(409, errorMsg, null) ;
        }

        User user = getUserByEmail.get();

        if(!user.getUserPassword().equals(loginDto.getUserPassword())) {
            return new ApiResponse(409, errorMsg, null) ;
        }
        String token = generateToken();
        user.setJWTtoken(token);
        userRepo.save(user);
        return new ApiResponse(200, "Login success", user) ;
    }

    public ApiResponse getAllUsers() {

        List<IGetUserDto> allUsers = userRepo.getAllUsers();


        return new ApiResponse(200,"Loaded all users successfully",allUsers);
    }
    public ApiResponse updateUser(UpdateUserDto updateUserDto) {

        Optional<User> OptionalUser = userRepo.findById(updateUserDto.getUserId());
        User user = OptionalUser.get();

        if(updateUserDto.getUserImage() == null) {
            System.out.println("GET NOTE ICON IS NULL !!");
        }
        else {
            System.out.println("GET NOTE ICON IS NOT NULL :{");
            System.out.println("GET NOTE ICON IS NOT NULL :{");
            System.out.println("GET NOTE ICON IS NOT NULL :{");
        }
        BeanUtils.copyProperties(updateUserDto,user,"JWTtoken,userPassword");
        BeanUtils.copyProperties(updateUserDto,user);

        userRepo.save(user);

        return new ApiResponse(200,"User updated successfully",null);
    }

    private String generateToken() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String token = bytes.toString();
        return token;
    }


}
