//OK

package com.proyecto_dbp.auth.dto;

import com.proyecto_dbp.user.domain.UserType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class RegisterReq {

    private String name;
    private String email;
    private String bio;
    private UserType userType;
    private String password;
    private MultipartFile profilePicture;
}
