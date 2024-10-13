//OK
package com.proyecto_dbp.auth.dto;

import lombok.Data;

@Data
public class LoginReq {
    private String email;
    private String password;
}
