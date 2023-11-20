package com.rest.api.Services;

import com.rest.api.data.dto.v1.security.AccountCredentialsDTO;
import com.rest.api.data.dto.v1.security.TokenDTO;
import com.rest.api.model.User;
import com.rest.api.repository.UserRepository;
import com.rest.api.security.Jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServices {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository repository;

    public ResponseEntity signin(AccountCredentialsDTO data) {
        try {
            String username = data.getUsername();
            String password = data.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            User user = repository.findByUserName(username);

            var tokenResponse = new TokenDTO();
            if (user != null) {
                tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }


    public ResponseEntity refreshToken(String username, String refreshToken) {
        if (checkRefreshToken(username, refreshToken))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials");

        var user = repository.findByUserName(username);

        var tokenResponse = new TokenDTO();
        if (user != null) {
            tokenResponse = tokenProvider.refreshToken(refreshToken);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return ResponseEntity.ok(tokenResponse);
    }

    private static boolean checkRefreshToken(String username, String refreshToken) {
        return username == null || username.isBlank() || refreshToken == null || refreshToken.isBlank();
    }

    private static boolean checkCredentials(AccountCredentialsDTO credentials) {
        return credentials == null || credentials.getUsername() == null || credentials.getPassword() == null
                || credentials.getUsername().isBlank() || credentials.getPassword().isBlank();
    }

}
