package com.project.library.controller;

import com.project.library.dto.UserDTO;
import com.project.library.service.UserService;
import com.project.library.service.impl.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    private final UserService userService;

    @PostMapping("/token")
    public String generateToken(Authentication auth){
        var token = tokenService.generateToken(auth);
        return "Bearer ".concat(token);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserDTO req){;
        return ResponseEntity.ok(userService.createUser(req));
    }
}
