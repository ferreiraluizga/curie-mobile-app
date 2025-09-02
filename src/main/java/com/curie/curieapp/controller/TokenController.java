package com.curie.curieapp.controller;

import com.curie.curieapp.dto.request.LoginRequest;
import com.curie.curieapp.dto.response.LoginResponse;
import com.curie.curieapp.entities.Role;
import com.curie.curieapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class TokenController {

    @Autowired
    private final JwtEncoder jwtEncoder;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        var user = userRepository.findByEmail(loginRequest.email());
        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("Usuário ou Senha inválidos");
        }

        var now = Instant.now();
        var expiresIn = 300L;
        var scopes = user.get().getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));
        var claims = JwtClaimsSet.builder()
                .issuer("fastmarket")
                .subject(user.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }

}
