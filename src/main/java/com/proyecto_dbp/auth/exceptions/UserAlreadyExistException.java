package com.proyecto_dbp.auth.exceptions;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String message) {super (message);}
}
