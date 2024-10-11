package com.proyecto_dbp.auth.dto;

import lombok.Data;

@Data
public class RegisterReq {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Boolean isDriver = false;
    private Category category;
    private VehicleBasicDto vehicle;
}
