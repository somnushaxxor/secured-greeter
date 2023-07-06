package ru.kolesnik.securedgreeter.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kolesnik.securedgreeter.auth.dto.SignUpRequest;
import ru.kolesnik.securedgreeter.auth.exception.EmailAlreadyInUseException;
import ru.kolesnik.securedgreeter.auth.model.Role;
import ru.kolesnik.securedgreeter.auth.model.User;
import ru.kolesnik.securedgreeter.auth.repository.RoleRepository;
import ru.kolesnik.securedgreeter.auth.repository.UserRepository;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    public void addUser(SignUpRequest request) {
        if (isEmailAlreadyInUse(request.getEmail())) {
            throw new EmailAlreadyInUseException();
        }
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Role \"USER\" not found!"));
        final User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(userRole))
                .build();
        userRepository.save(user);
    }

    private boolean isEmailAlreadyInUse(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
