package com.evaluasi.EvaluasiHUMBackEnd.service;

import com.evaluasi.EvaluasiHUMBackEnd.dto.AuthResponse;
import com.evaluasi.EvaluasiHUMBackEnd.dto.RuleDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.UserDto;
import com.evaluasi.EvaluasiHUMBackEnd.dto.UserEvaResultDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Evaluasi;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Karyawan;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Rule;
import com.evaluasi.EvaluasiHUMBackEnd.entity.User;
import com.evaluasi.EvaluasiHUMBackEnd.exception.AllException;
import com.evaluasi.EvaluasiHUMBackEnd.jwt.JwtUtil;
import com.evaluasi.EvaluasiHUMBackEnd.repository.KaryawanRepository;
import com.evaluasi.EvaluasiHUMBackEnd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

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

    public ResponseEntity<Object> editUser(Long id, UserDto userDto) {
        try {
            log.info("Inside edit user");
            Optional<User> optionalUser = userRepository.findById(id);
            User user = optionalUser.get();

            user.setKodeuser(userDto.getKodeuser());
            user.setUsername(userDto.getUsername());
            String rawPassword = userDto.getPassword();
            String encodedPassword = passwordEncoder.encode(rawPassword);
            user.setPassword(encodedPassword);
            user.setRole(userDto.getRole());
            user.setStatus(userDto.getStatus());

            userRepository.save(user);
            return ResponseEntity.ok("User edited successfully");

        }catch (Exception e){
            log.error("Error edited user", e);
            return ResponseEntity.status(500).body("Error edited user");
        }
    }

    public ResponseEntity<Object> hapusUser(Long id) {
        try {
            log.info("Inside hapus User");
            Optional<User> optionalUser  = userRepository.findById(id);

            if (optionalUser.isPresent()) {
                userRepository.deleteById(id);
                return ResponseEntity.ok("Successfully deleted user");
            } else {
                return ResponseEntity.status(404).body("user not found");
            }
        } catch (Exception e) {
            log.error("Error delete user", e);
            return ResponseEntity.status(500).body("Error delete user");
        }
    }

    public ResponseEntity<Object> login(AuthResponse authResponse) {
        try {
            log.info("Inside login");
            String username = authResponse.getUsername();
            String password = authResponse.getPassword();

            Optional<User> userOpt = userRepository.findByUsername(username);
            if (userOpt.isPresent()) {
                User user = userOpt.get();

                if ("true".equalsIgnoreCase(user.getStatus())) {
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    username, password
                            )
                    );

                    try {
                        String jwtToken = jwtUtil.generateToken(user.getUsername(), user.getRole(), user.getIduser(),user.getKaryawan().getNik());
                        return ResponseEntity.ok().body(Collections.singletonMap("token", jwtToken));
                    } catch (Exception e) {
                        return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Token has expired. Please log in again."));
                    }
                } else {
                    return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Please ask Admin/HRD to activate your account."));
                }
            } else {
                return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Our system didn't find your username. Please contact Admin/HRD."));
            }
        } catch (Exception ex) {
            log.error("Exception during login", ex);
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Bad Credentials. Please check your username or password"));
        }
    }

    public UserEvaResultDto getUserEvaResultByUsername(String username,UserEvaResultDto userEvaResultDtoo,AuthResponse authResponse) {
        log.info("Inside Userevaresul");
        username = authResponse.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Karyawan karyawan = user.getKaryawan();
        List<Evaluasi> evaluasiList = karyawan.getEvaluasiList();

        if (evaluasiList.isEmpty()) {
            // Handle the case where no evaluations are found for the user
            return null;
        }

        Evaluasi latestEvaluasi = evaluasiList.stream()
                .max(Comparator.comparing(Evaluasi::getTanggalevaluasi))
                .orElseThrow(() -> new UsernameNotFoundException("Evaluasi not found"));

        userEvaResultDtoo.setIdkar(karyawan.getIdkar());
        userEvaResultDtoo.setNik(karyawan.getNik());
        userEvaResultDtoo.setNama(karyawan.getNama());
        userEvaResultDtoo.setDivisi(karyawan.getDivisi());
        userEvaResultDtoo.setJabatan(karyawan.getJabatan());
        userEvaResultDtoo.setKodeevaluasi(latestEvaluasi.getKodeevaluasi());
        userEvaResultDtoo.setTanggalevaluasi(latestEvaluasi.getTanggalevaluasi());
        userEvaResultDtoo.setHasilevaluasi(latestEvaluasi.getHasilevaluasi());
        userEvaResultDtoo.setPerluditingkatkan(latestEvaluasi.getPerluditingkatkan());

        return userEvaResultDtoo;
    }

    public UserDto mapUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setIduser(user.getIduser());
        userDto.setKodeuser(user.getKodeuser());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());
        userDto.setStatus(user.getStatus());
        userDto.setCreated(user.getCreated());


        if (user.getKaryawan() != null) {
            userDto.setNik(user.getKaryawan().getNik());
            userDto.setIdkar(user.getKaryawan().getIdkar());
        }

        return userDto;
    }
    
    public UserDto fetchUserDtoByIduser(Long id) throws AllException {
        log.info("Inside fetchUserDtoByIduser");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AllException("User with iduser " + id + " not found"));

        return mapUserToUserDto(user);
    }

}


