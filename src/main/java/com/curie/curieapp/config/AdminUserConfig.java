package com.curie.curieapp.config;

import com.curie.curieapp.entities.Role;
import com.curie.curieapp.entities.User;
import com.curie.curieapp.repository.RoleRepository;
import com.curie.curieapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Configuration
@AllArgsConstructor
public class AdminUserConfig implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());

        var userAdmin = userRepository.findByName("admin");

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("admin ja existe");
                },
                () -> {
                    var user = new User();
                    user.setNome("admin");
                    user.setEmail("admin@admin.com");
                    user.setDescricao("Administrador do Sistema");
                    user.setTelefone("11987654321");
                    user.setNascimento(LocalDateTime.now());
                    user.setPassword(passwordEncoder.encode("admin"));
                    user.setRoles(Set.of(roleAdmin));
                    userRepository.save(user);
                }
        );
    }
}
