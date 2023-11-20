package com.rest.api.controllers;

import com.rest.api.Services.AuthenticationServices;
import com.rest.api.data.dto.v1.security.AccountCredentialsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "endpoints for managing authentication")
public class AuthenticationController {

    @Autowired
    AuthenticationServices authenticationServices;

    @PostMapping("/signin")
    @Operation(summary = "Sign in", description = "Sign in", tags = {"Authentication"})
    public ResponseEntity signIn(@RequestBody AccountCredentialsDTO credentials) {

        ResponseEntity token = authenticationServices.signin(credentials);
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");

        return token;
    }

    @PutMapping("/refresh/{username}")
    @Operation(summary = "Refresh token", description = "Refresh token", tags = {"Authentication"})
    public ResponseEntity refreshToken(@PathVariable("username") String username,
                                       @RequestHeader("Authorization") String refreshToken) {
        ResponseEntity token = authenticationServices.refreshToken(username, refreshToken);
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");

        return token;
    }

}
