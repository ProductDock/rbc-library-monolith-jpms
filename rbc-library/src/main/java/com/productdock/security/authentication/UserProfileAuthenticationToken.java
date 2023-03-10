package com.productdock.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.Assert;

public class UserProfileAuthenticationToken extends AbstractAuthenticationToken {

  private static final String NOT_EXPOSING_CREDENTIALS = "";

  private final UserProfile principal;

  public UserProfileAuthenticationToken(String principal) {
    super(AuthorityUtils.NO_AUTHORITIES);
    Assert.notNull(principal, "principal cannot be null");
    this.principal = new UserProfile(principal);
    this.setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return NOT_EXPOSING_CREDENTIALS;
  }

  @Override
  public UserProfile getPrincipal() {
    return this.principal;
  }
}
