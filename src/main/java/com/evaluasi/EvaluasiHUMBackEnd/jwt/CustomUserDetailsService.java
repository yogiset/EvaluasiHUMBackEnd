package com.evaluasi.EvaluasiHUMBackEnd.jwt;



import com.evaluasi.EvaluasiHUMBackEnd.entity.User;
import com.evaluasi.EvaluasiHUMBackEnd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<User> user = userRepository.findByUsername(username);
            return user.map(CustomUserDetails::new)
                    .orElseThrow(() -> new UsernameNotFoundException("username tidak ditemukan " + username));
        }
}
