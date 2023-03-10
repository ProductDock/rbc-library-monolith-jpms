package com.productdock.security.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

public class UserProfileAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  private UserDetailsService userDetailsService;

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    if (authentication.getCredentials() == null) {
      this.logger.debug("Failed to authenticate since no credentials provided");
      throw new BadCredentialsException(this.messages
              .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
    }
//    String presentedPassword = authentication.getCredentials().toString();
//    if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
//      this.logger.debug("Failed to authenticate since password does not match stored value");
//      throw new BadCredentialsException(this.messages
//              .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
//    }
  }

  @Override
  protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
          throws AuthenticationException {
    //prepareTimingAttackProtection();
    try {
      UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(username);
      if (loadedUser == null) {
        throw new InternalAuthenticationServiceException(
                "UserDetailsService returned null, which is an interface contract violation");
      }
      return loadedUser;
    }
    catch (UsernameNotFoundException ex) {
      //mitigateAgainstTimingAttack(authentication);
      throw ex;
    }
    catch (InternalAuthenticationServiceException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
    }
  }

  @Override
  protected void doAfterPropertiesSet() {
    Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
  }

  public void setUserDetailsService(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  protected UserDetailsService getUserDetailsService() {
    return this.userDetailsService;
  }
}
