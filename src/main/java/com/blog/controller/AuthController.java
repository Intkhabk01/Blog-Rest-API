package com.blog.controller;

import com.blog.dto.JWTAuthResponse;
import com.blog.dto.LoginDto;
import com.blog.dto.SignUpDto;
import com.blog.exception.InvalidCredentialException;
import com.blog.model.Role;
import com.blog.model.User;
import com.blog.repository.RoleRepository;
import com.blog.repository.UserRepository;
import com.blog.security.CustomUserDetailsService;
import com.blog.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
//@SecurityRequirement(name="bearerAuth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/singin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@Valid @RequestBody LoginDto loginDto){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //get token from token provider
        String token = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));

    }

    @PostMapping("/singup")
    public ResponseEntity<?> registerUser(@Valid@RequestBody SignUpDto signUpDto){
        //ass check for username exist in a db
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken",HttpStatus.BAD_REQUEST);
        }

        //add check for email exist in db
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken",HttpStatus.BAD_REQUEST);
        }

        //careate Usrer object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        //String password= encoder.encode(signUpDto.getPassword());
        user.setPassword(encoder.encode(signUpDto.getPassword()));

        Optional<Role> roles =roleRepository.findByName("ROLE_USER");
        user.setRoles(Collections.singleton(roles.get()));
        userRepository.save(user);

        return new ResponseEntity<>("User Register Sucessfully",HttpStatus.CREATED) ;
    }









}





//default Method for authentication



/*String username=loginDto.getUsernameOrEmail();
String password=loginDto.getPassword();

UserDetails userDetails=userDetailsService.loadUserByUsername(username);


        if(encoder.matches(password,userDetails.getPassword())){
        return new ResponseEntity<>("User signed-in Successfully !", HttpStatus.OK);
        }else {
        throw new InvalidCredentialException("Invalid Credentail !!!");
        }*/
