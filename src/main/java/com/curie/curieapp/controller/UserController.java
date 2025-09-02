package com.curie.curieapp.controller;

import com.curie.curieapp.dto.request.RegisterRequest;
import com.curie.curieapp.entities.Role;
import com.curie.curieapp.entities.User;
import com.curie.curieapp.repository.RoleRepository;
import com.curie.curieapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
public class UserController {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest dto) {
        var basicRole = roleRepository.findByName(Role.Values.BASIC.name());

        var userFromDb = userRepository.findByEmail(dto.email());
        if (userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var user = new User();
        user.setNome(dto.name());
        user.setDescricao(dto.descricao());
        user.setTelefone(dto.telefone());
        user.setNascimento(dto.nascimento());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRoles(Set.of(basicRole));

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<User>> getAll() {
        var users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

}
