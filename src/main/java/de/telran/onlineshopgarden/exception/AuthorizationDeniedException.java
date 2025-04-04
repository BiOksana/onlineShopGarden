package de.telran.onlineshopgarden.exception;

public class AuthorizationDeniedException extends RuntimeException {
  public AuthorizationDeniedException(String message) {
    super(message);
  }
}
