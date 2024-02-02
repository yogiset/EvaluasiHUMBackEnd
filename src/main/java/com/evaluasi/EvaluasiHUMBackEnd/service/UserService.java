package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.RuleDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.UserDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Karyawan;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Rule;
import com.evaluasi.EvaluasiHUMBackEnd.entity.User;
import com.evaluasi.EvaluasiHUMBackEnd.jwt.JwtUtil;
import com.evaluasi.EvaluasiHUMBackEnd.repository.KaryawanRepository;
import com.evaluasi.EvaluasiHUMBackEnd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final KaryawanRepository karyawanRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<Object> createAccount(UserDto userDto) {
        try {
            log.info("Inside Create Account {}", userDto);
            User user = new User();

            user.setKodeuser(userDto.getKodeuser());
            user.setUsername(userDto.getUsername());
            String rawPassword = userDto.getPassword();
            String encodedPassword = passwordEncoder.encode(rawPassword);
            user.setPassword(encodedPassword);
            user.setRole(userDto.getRole());
            user.setStatus("true");
            user.setCreated(Instant.now());


            Karyawan karyawan = karyawanRepository.findByNik(userDto.getNik());
            if (karyawan == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Karyawan not found for NIK: " + userDto.getNik());
            }

            user.setKaryawan(karyawan);
            userRepository.save(user);

            return ResponseEntity.ok("Account created successfully");
        } catch (Exception e) {
            log.error("Error creating account", e);
            return ResponseEntity.status(500).body("Error creating account");
        }
    }



    public List<UserDto> showall() {
        log.info("Inside Show User");

        List<User> userList = userRepository.findAll();

        return userList.stream()
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setIduser(user.getIduser());
                    userDto.setKodeuser(user.getKodeuser());
                    userDto.setUsername(user.getUsername());
                    userDto.setPassword(user.getPassword());
                    userDto.setRole(user.getRole());
                    userDto.setStatus(user.getStatus());
                    userDto.setCreated(user.getCreated());
                    userDto.setNik(user.getKaryawan().getNik());
                    return userDto;
                })
                .collect(Collectors.toList());
    }

}
