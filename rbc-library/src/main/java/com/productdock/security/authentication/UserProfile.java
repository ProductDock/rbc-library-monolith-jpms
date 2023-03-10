package com.productdock.security.authentication;

public class UserProfile {

  private String userId;
  private String email;
  private String fullName;
  private String profilePicture;

  public UserProfile(String principal) {
    this.email = principal;
  }
}
