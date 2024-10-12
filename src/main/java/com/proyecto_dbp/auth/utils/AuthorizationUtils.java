package com.proyecto_dbp.auth.utils;


import com.proyecto_dbp.user.domain.UserService;
import com.proyecto_dbp.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationUtils {

    @Autowired
    private UserService userService;

    public boolean isAdminOrResourceOwner(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        UserDto userDto = userService.getUserByEmail(username);

        return userDto.getUserId().equals(id) || userDto.getUserType().toString().equals("INFLUENCER");
    }

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        } catch (ClassCastException e) {
            return null;
        }
    }
}