package com.blog.config;

import com.blog.security.CustomUserDetailsService;
import com.blog.security.JwtAuthenticationEntryPoint;
import com.blog.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


                http.authorizeHttpRequests((authorize) ->

                        authorize
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/posts").hasAnyRole("USER","ADMIN")
                                .requestMatchers(HttpMethod.POST,"/api/posts/**").hasAnyRole("USER","ADMIN")
                                //.requestMatchers(HttpMethod.PUT,"/api/posts/{PostId}/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/api/posts/{PostId}/**").hasAnyRole("USER","ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/api/posts/{PostId}").hasRole("ADMIN")
                                .anyRequest()
                                .authenticated()

                ).exceptionHandling( exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                ).sessionManagement( session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }











}




//spring security without Jwt

  /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests((configurer)->
                configurer
                        .requestMatchers(HttpMethod.POST,"/api/posts").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/posts/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
                        .requestMatchers("/api/posts/{postId}/**").permitAll()
                        .anyRequest()
                        .authenticated()
        );
//        http.httpBasic(Customizer.withDefaults());
//        http.csrf(Customizer.withDefaults());

        http.httpBasic();
        http.cors().and().csrf().disable();

        return http.build();
    }*/







/*    // authentication
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(PasswordEncoder encoder){

        UserDetails intkhab = User.builder()
                .username("ikhan")
                .password(encoder.encode("khan"))
                .roles("Employee","Admin","users")
                .build();

        UserDetails user1 = User.builder()
                .username("sumit")
                .password(encoder.encode("sumit"))
                .roles("users")
                .build();

        return new InMemoryUserDetailsManager(intkhab,user1);
    }*/
