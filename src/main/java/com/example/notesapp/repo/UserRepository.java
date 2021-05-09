package com.example.notesapp.repo;

import com.example.notesapp.beans.User;
import com.example.notesapp.dtos.userDtos.IGetUserDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Integer> {

    Optional<User> findByUserEmail(String email);
    Optional<User> findByUserEmailAndUserPassword(String email, String password);

    @Query("SELECT p FROM User p WHERE (p.userId) = :userId AND (p.JWTtoken) = :token")
    Optional<User> findByUserIdAndJWTtoken(@Param("userId")int userId,@Param("token") String token);

    @Query(
            value = "SELECT " +
                    "user.user_id as userId, user.user_email as userEmail,user_first_name as userFirstName,user_last_name as userLastName," +
                    "user_image as userImage, user_location as userLocation,user_phone as userPhone, user_role as userRole " +
                    "from user",
            nativeQuery = true)
    List<IGetUserDto> getAllUsers();


}
