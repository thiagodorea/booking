package com.digitalbooking.digitalbooking.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

//@EnableWebSecurity
//@EnableMethodSecurity
@Configuration
public class SecurityConfig  {
   @Autowired
   private Environment env;

   @Autowired
   AutenticacaoService autenticacaoService;

   @Autowired
   AutenticacaoViaTokenFilter autenticacaoViaTokenFilter;

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      if (Arrays.asList(env.getActiveProfiles()).contains("dev")) {
         http.headers().frameOptions().disable();
      }
      http.cors().and().csrf().disable()
               .httpBasic().and()
               .authorizeHttpRequests( (authorize) -> authorize
                     .requestMatchers("/doc/**","/doc/swagger-ui/**","/api-docs-json/**").permitAll()
                     .requestMatchers(HttpMethod.GET,"/produtos/**","/categorias/**","/cidades/**","/caracteristicas/**").permitAll()
                     .requestMatchers(HttpMethod.POST,"/auth","/usuarios/**","/uploads/**").permitAll()
                     .requestMatchers(HttpMethod.POST,"/produtos/**").hasAuthority("ADMIN")
                     .requestMatchers(HttpMethod.PATCH,"/produtos/**").permitAll()
                     .anyRequest().authenticated()
            )
            .addFilterBefore(autenticacaoViaTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      return http.build();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public AuthenticationManager authenticationManager(HttpSecurity http,
                                                      PasswordEncoder passwordEncoder,
                                                      AutenticacaoService userDetailsService) throws Exception {
      return http.getSharedObject(AuthenticationManagerBuilder.class)
               .userDetailsService(userDetailsService)
               .passwordEncoder(passwordEncoder)
               .and()
               .build();
   }

   @Bean
   CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
      configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT","PATCH", "DELETE", "OPTIONS"));
      final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);
      return source;
   }
}
