package com.productdock.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.server.authentication.logout.HttpStatusReturningServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.Filter;

import static java.util.Arrays.asList;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${cors.allowed.origins}")
    private String corsAllowedOrigins;

    @Value("${security.front-to-gateway.redirect-uri}")
    private String frontRedirectUri;

    @Autowired                                                       ❷
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterAfter(
            jwtAuthenticationFilter,
            BasicAuthenticationFilter.class
        );

         http
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeHttpRequests()
                    .antMatchers("/actuator/**").permitAll()
                    .anyRequest().authenticated().and()
                    .oauth2Login()
                        .successHandler(new SimpleUrlAuthenticationSuccessHandler(frontRedirectUri)).and()
                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .logout()
                .logoutUrl("/api/logout");
                //.logoutHandler(logoutHandler())
                //.logoutSuccessHandler(statusLogoutSuccessHandler())

    }

//    private LogoutHandler logoutHandler() {
//        return new DelegatingLogoutHandler(new WebSessionServerLogoutHandler());
//    }

    private ServerLogoutSuccessHandler statusLogoutSuccessHandler() {
        return new HttpStatusReturningServerLogoutSuccessHandler();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader(CorsConfiguration.ALL);
        configuration.addAllowedOrigin(corsAllowedOrigins);
        allowInvocationWithSessionCookie(configuration);
        configuration.setAllowedMethods(asList("GET", "POST", "OPTIONS", "PUT", "PATCH", "HEAD", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private void allowInvocationWithSessionCookie(CorsConfiguration configuration) {
        configuration.setAllowCredentials(true);
    }
}
