package com.rs.ecommerceapi.controller;

import com.rs.ecommerceapi.config.JwtProvider;
import com.rs.ecommerceapi.exception.UserException;
import com.rs.ecommerceapi.model.Cart;
import com.rs.ecommerceapi.model.User;
import com.rs.ecommerceapi.repository.UserRepository;
import com.rs.ecommerceapi.request.LoginRequest;
import com.rs.ecommerceapi.response.AuthResponse;
import com.rs.ecommerceapi.service.CartService;
import com.rs.ecommerceapi.service.impl.CustomUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserServiceImpl customUserServiceImpl;
    private final CartService cartService;

    @Autowired
    public AuthController(UserRepository userRepository, CustomUserServiceImpl customUserServiceImpl, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, CartService cartService) {
        this.userRepository = userRepository;
        this.customUserServiceImpl = customUserServiceImpl;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.cartService = cartService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        User isEmailExist = userRepository.findByEmail(email);
        if(isEmailExist != null) {
            throw new UserException("Email already exist with another user");
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);

        User savedUser = userRepository.save(createdUser);
        Cart cart = cartService.createCart(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.genrateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Signup successfully");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) throws UserException {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.genrateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Login successfully");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

//    @GetMapping("/login/oauth2/code/google")
//    public ResponseEntity<AuthResponse> loginWithGoogle(@RequestParam String code) throws IOException {
//        String accessToken = GoogleLogin.getToken(code);
//        GoogleProfile googleAccount = GoogleLogin.getUserInfo(accessToken);
//
//        User user = userRepository.findByEmail(googleAccount.getEmail());
//        if (user == null) {
//            user = new User();
//            user.setEmail(googleAccount.getEmail());
//            user.setFirstName(googleAccount.getName());
//            userRepository.save(user);
//        }
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null, customUserServiceImpl.loadUserByUsername(user.getEmail()).getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String token = jwtProvider.genrateToken(authentication);
//        AuthResponse authResponse = new AuthResponse();
//        authResponse.setJwt(token);
//        authResponse.setMessage("Login with Google successful");
//
//        return new ResponseEntity<>(authResponse, HttpStatus.OK);
//    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserServiceImpl.loadUserByUsername(username);

        if(userDetails == null) {
            throw new BadCredentialsException("Invalid Username");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
