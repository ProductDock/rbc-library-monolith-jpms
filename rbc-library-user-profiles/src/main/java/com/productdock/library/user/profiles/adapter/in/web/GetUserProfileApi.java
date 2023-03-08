package com.productdock.library.user.profiles.adapter.in.web;

import com.productdock.library.user.profiles.application.port.in.GetUserProfileQuery;
//import com.productdock.library.user.profiles.config.UserProfileAuthenticationToken;
import com.productdock.library.user.profiles.domain.UserProfile;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user-profiles")
public record GetUserProfileApi(GetUserProfileQuery getUserProfileQuery,
                                UserProfileMapper mapper) {

    @GetMapping("/user-info")
    public LoggedInUserDto getLoggedInUserProfile(OAuth2AuthenticationToken authentication) {
      OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
      OidcIdToken token = oidcUser.getIdToken();
      return new LoggedInUserDto(
              token.getClaim("name"),
              token.getClaim("email"),
              token.getClaim("picture"),
              "ROLE_USER");
    }

    @GetMapping
    public List<UserProfileDto> getByUserEmails(@RequestParam List<String> userEmails) {
        var userProfiles = getUserProfileQuery.getByUserEmails(userEmails);

        return mapper.toDtoCollection(userProfiles);
    }
}
