package me.rate.rateme.config;

import static me.rate.rateme.data.enums.Role.ADMIN;
import static me.rate.rateme.data.enums.Role.COMPANY_HEAD;
import static me.rate.rateme.data.enums.Role.USER;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  @Bean
  @Primary
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                 AuthenticationProvider authenticationProvider)
      throws Exception {
    return http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable)
               .authenticationProvider(authenticationProvider).authorizeHttpRequests(
            request -> request.requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
                              .hasRole(ADMIN.name())

                              // judge
                              .requestMatchers("/judge/**").hasRole(USER.name())

                              // contest
                              .requestMatchers(GET, "/contest/**").hasRole(USER.name())
                              .requestMatchers(POST, "/contest/**").hasRole(USER.name())
                              .requestMatchers(DELETE, "/contest/**").hasRole(USER.name())

                              // user
                              .requestMatchers(GET, "/user/**").hasRole(USER.name())
                              .requestMatchers(POST, "/user/**").permitAll()
                              .requestMatchers(DELETE, "/user/{username}").access(
                    new WebExpressionAuthorizationManager("#username == authentication.name"))

                              // role
                              .requestMatchers("/role/**").hasRole(ADMIN.name())

                              // company
                              .requestMatchers(GET, "/company/**").hasRole(USER.name())
                              .requestMatchers(POST, "/company/**").hasRole(COMPANY_HEAD.name())
                              .requestMatchers(PUT, "/company/**").hasRole(COMPANY_HEAD.name())
                              .requestMatchers(DELETE, "/company/**").hasRole(COMPANY_HEAD.name()))
               .httpBasic(Customizer.withDefaults()).build();
  }

  @Bean
  public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);

    return provider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
