package com.productdock.security.authentication;

import org.springframework.security.core.AuthenticationException;


public class UserProfileAuthenticationException extends AuthenticationException {

  public UserProfileAuthenticationException(String msg) {
    super(msg);
  }

}
