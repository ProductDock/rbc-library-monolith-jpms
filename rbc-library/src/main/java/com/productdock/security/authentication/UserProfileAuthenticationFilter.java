package com.productdock.security.authentication;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserProfileAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  public static final String DEFAULT_FILTER_PROCESSES_URI = "/api/*";

  private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

  private Converter<OAuth2AuthenticationToken, UserProfileAuthenticationToken> authenticationResultConverter = this::createAuthenticationResult;

  private UserProfileAuthenticationToken createAuthenticationResult(OAuth2AuthenticationToken authenticationResult) {
    String principalName = authenticationResult.getPrincipal().getName();
    return new UserProfileAuthenticationToken(principalName);
  }

  protected UserProfileAuthenticationFilter() {
    super(DEFAULT_FILTER_PROCESSES_URI);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || this.trustResolver.isAnonymous(authentication)) {
      throw new UserProfileAuthenticationException("User is not previously authenticated with OAuth2 Provider");
    }

    Authentication authenticationResult = this
            .getAuthenticationManager().authenticate(authentication);

    UserProfileAuthenticationToken authenticationToken = new UserProfileAuthenticationToken();
    return authenticationToken;
  }
}
