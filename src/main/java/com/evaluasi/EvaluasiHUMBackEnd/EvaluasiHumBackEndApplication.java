package com.evaluasi.EvaluasiHUMBackEnd;

import com.evaluasi.EvaluasiHUMBackEnd.dto.UserDto;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Karyawan;
import com.evaluasi.EvaluasiHUMBackEnd.entity.User;
import com.evaluasi.EvaluasiHUMBackEnd.repository.KaryawanRepository;
import com.evaluasi.EvaluasiHUMBackEnd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

@SpringBootApplication
public class EvaluasiHumBackEndApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private KaryawanRepository karyawanRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(EvaluasiHumBackEndApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

			User userr = userRepository.findByUsernameId("SUPERADMIN");
		if (userr == null) {

			User user = new User();
			user.setKodeuser("SUPERADMIN");
			user.setUsername("SUPERADMIN");
			String rawPassword = "SUPERADMIN";
			String encodedPassword = passwordEncoder.encode(rawPassword);
			user.setPassword(encodedPassword);
			user.setRole("ADMIN");
			user.setStatus("True");
			user.setCreated(Instant.now());
			Karyawan karyawan = new Karyawan();
			karyawan.setNik("SUPERADMIN");
			karyawan.setNik("SUPERADMIN");
			karyawan.setNama("SUPERADMIN");
			karyawan.setDivisi("SUPERADMIN");
			karyawan.setJabatan("SUPERADMIN");
			karyawan.setCadangan1("SUPERADMIN");
			karyawan.setCadangan2(0);
			user.setKaryawan(karyawan);

			userRepository.save(user);
		}
	}
}
