package com.example.notesapp.beans;


import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
public class User  implements Serializable {
    @Id
    @GeneratedValue
    private int userId;



    @Size(min = 2,max = 20, message = "First name must be minimum 2 maximum 20")
    private String userFirstName;
    @Size(min = 2,max = 20, message = "Last name must be minimum 2 maximum 20")
    private String userLastName;
    @Email
    private String userEmail;
//    @Size(min = 3,max = 120, message = "Password must be minimum 2 maximum 120")
    private String userPassword;
    private String userImage = "http://localhost:8080/upload/files/default.png";
    private String userRole;
    private String userPhone;
    private String userLocation;
    private String JWTtoken;

}
