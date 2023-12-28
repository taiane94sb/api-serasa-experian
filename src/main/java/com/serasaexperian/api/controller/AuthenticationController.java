package com.serasaexperian.api.controller;

import com.serasaexperian.api.model.auth.AuthenticationInput;
import com.serasaexperian.api.model.auth.LoginResponse;
import com.serasaexperian.api.model.auth.Register;
import com.serasaexperian.domain.model.User;
import com.serasaexperian.domain.repository.UserRepository;
import com.serasaexperian.infra.security.TokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationInput data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid Register data){
        if(this.userRepository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
