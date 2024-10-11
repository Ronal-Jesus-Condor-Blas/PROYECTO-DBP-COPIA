package com.proyecto_dbp.auth.excepcions;

public class UserAlreadyExistException extends RuntimeException {
  public UserAlreadyExistException(String message) {
    super(message);
  }
}
