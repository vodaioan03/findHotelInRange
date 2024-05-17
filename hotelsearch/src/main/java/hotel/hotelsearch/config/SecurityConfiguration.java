package hotel.hotelsearch.config;

import org.springframework.web.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import hotel.hotelsearch.user.model.permission;
import hotel.hotelsearch.user.service.userService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  @Autowired
  userService userService;
  @Autowired
  AuthenticationProvider authenticationProvider;

  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
      .csrf(AbstractHttpConfigurer::disable)
      .sessionManagement(sessionManageConfig -> sessionManageConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
      .authorizeHttpRequests(registry -> {
      registry.requestMatchers("/auth/**","/home/**").permitAll();
      registry.requestMatchers("/admin/**").hasAuthority(permission.USER_ADMIN.name());
      registry.requestMatchers("/member/**","/api/hotel/**").hasAuthority(permission.USER_MEMBER.name());
      registry.requestMatchers("/developer/**").hasAuthority(permission.USER_DEVELOPER.name());
      registry.anyRequest().authenticated();
    })
    .build();
  }

  @Bean
  public CorsFilter corsFilter() {
      CorsConfiguration corsConfig = new CorsConfiguration();
      corsConfig.addAllowedOrigin("*"); 
      corsConfig.addAllowedMethod("*");
      corsConfig.addAllowedHeader("*"); 

      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", corsConfig);

      return new CorsFilter(source);
  }
}
