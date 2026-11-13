package chnu.edu.websecurity26.user;

/*
  @author   Pavliuk
  @project   web-security26
  @class  UserDetailsServiceImpl
  @version  1.0.0 
  @since 14.11.2026 - 00.00
*/
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    void init() {
        User user = User.builder()
                .id("1")
                .firstName("John")
                .lastName("Smith")
                .email("john@mail.com")
                .password(passwordEncoder.encode("admin"))
                .enabled(true)
                .accountLocked(false)
                .roles(List.of(Role.ADMIN))
                .build();
        repository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return repository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}