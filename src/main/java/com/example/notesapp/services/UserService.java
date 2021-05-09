package com.example.notesapp.services;

import com.example.notesapp.beans.User;
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

    /**
     * User sign up, get Sign up data object, check if email already exists & save on db
     * @param signUpDto
     * @return user object
     */
    public ApiResponse signUp(SignUpDto signUpDto) {
        try {
            Optional<User> checkIfEmailExistsOptional = userRepo.findByUserEmail(signUpDto.getUserEmail());
            if (checkIfEmailExistsOptional.isEmpty()) {
                User user = new User();
                BeanUtils.copyProperties(signUpDto, user);
                String token = generateToken();
                user.setJWTtoken(token);
                userRepo.save(user);
                return new ApiResponse(200, "success", user);
            } else {
                return new ApiResponse(409, "user email already exists", null);
            }
        } catch (Exception e) {
            return new ApiResponse(409, "error signing up ", e.getMessage());
        }


    }

    /**
     * Get login data object, check if exists by email & password
     * @param loginDto
     * @return user object
     */

    public ApiResponse login(LoginDto loginDto) {

        try {
            // For security reasons im making a global error for both cases of wrong mail\password
            String errorMsg = "Wrong username/password, please try again";
            Optional<User> checkUserDetails = userRepo.findByUserEmailAndUserPassword(loginDto.getUserEmail(), loginDto.getUserPassword());

            if (checkUserDetails.isEmpty()) {
                return new ApiResponse(409, errorMsg, null);
            }
            User user = checkUserDetails.get();
            String token = generateToken();
            user.setJWTtoken(token);

            userRepo.save(user);
            return new ApiResponse(200, "Login success", user);
        } catch (Exception e) {
            return new ApiResponse(409, "error logging in  ", e.getMessage());
        }

    }

    /**
     * Get all users
     * @return all users (IGetUserDto type,
     */
    public ApiResponse getAllUsers() {
        try {
            List<IGetUserDto> allUsers = userRepo.getAllUsers();
            return new ApiResponse(200, "Loaded all users successfully", allUsers);
        } catch (Exception e) {
            return new ApiResponse(409, "error getting users ", e.getMessage());
        }

    }

    /**
     * gets update user data object, finding by email and updating
     * @param updateUserDto
     * @return
     */
    public ApiResponse updateUser(UpdateUserDto updateUserDto) {

        System.out.println(updateUserDto);
        try {
            User user = new User();
            Optional<User> checkIfEmailExists = userRepo.findByUserEmail(updateUserDto.getUserEmail());
            if (!checkIfEmailExists.isEmpty()) {
                 user = checkIfEmailExists.get();
                System.out.println(user.getUserEmail());
                System.out.println(updateUserDto.getUserEmail());
                if(!user.getUserEmail().equals( updateUserDto.getUserEmail())) {
                    return new ApiResponse(409, "Email already exists", null);
                }
            }
            else {
                Optional<User> OptionalUser = userRepo.findById(updateUserDto.getUserId());
                 user = OptionalUser.get();
            }
            // Check if user uploaded an image, if so update db if not ignore on update to keep old image
            if (updateUserDto.getUserImage() == null) {
                BeanUtils.copyProperties(updateUserDto, user, "userPassword","JWTtoken","userImage");
            } else {
                BeanUtils.copyProperties(updateUserDto, user, "userPassword","JWTtoken");
            }
            userRepo.save(user);
            return new ApiResponse(200, "User updated successfully", user);
        } catch (Exception e) {
            return new ApiResponse(409, "error updating user ", e.getMessage());
        }
    }

    private String generateToken() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String token = bytes.toString();
        return token;
    }


}
