package com.evaluasi.EvaluasiHUMBackEnd;

import com.evaluasi.EvaluasiHUMBackEnd.entity.Karyawan;
import com.evaluasi.EvaluasiHUMBackEnd.entity.User;
// import com.evaluasi.EvaluasiHUMBackEnd.repository.KaryawanRepository;
import com.evaluasi.EvaluasiHUMBackEnd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDate;

@SpringBootApplication
public class EvaluasiHumBackEndApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	// @Autowired
	// private KaryawanRepository karyawanRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(EvaluasiHumBackEndApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

			User userr = userRepository.findByUsernameId("SUPERADMIN");
			User user2 = userRepository.findByUsernameId("USER");
		if (userr == null && user2 == null) {

			User user = new User();
			Karyawan karyawan = new Karyawan();

			user.setKodeuser("SUPERADMIN");
			user.setUsername("SUPERADMIN");
			String rawPassword = "SUPERADMIN";
			String encodedPassword = passwordEncoder.encode(rawPassword);
			user.setPassword(encodedPassword);
			user.setRole("ADMIN");
			user.setStatus(true);
			user.setCreated(Instant.now());
			karyawan.setNik("SUPERADMIN");
			karyawan.setNama("SUPERADMIN");
			karyawan.setDivisi("SUPERADMIN");
			karyawan.setJabatan("SUPERADMIN");
			karyawan.setEmail("SUPERADMIN@hum.com");
			LocalDate tanggal = LocalDate.of(1, 1, 1);
			karyawan.setTanggalmasuk(tanggal);
			karyawan.setTingkatan("HighestAdministrator");
			user.setKaryawan(karyawan);
			userRepository.save(user);

			User regulerUser = new User();
			Karyawan regulerKaryawan = new Karyawan();

			regulerUser.setKodeuser("USER");
			regulerUser.setUsername("TESTUSER");
			String rawPassword1 = "TESTUSER";
			String encodedPassword1 = passwordEncoder.encode(rawPassword1);
			regulerUser.setPassword(encodedPassword1);
			regulerUser.setRole("USER");
			regulerUser.setStatus(true);
			regulerUser.setCreated(Instant.now());
			regulerKaryawan.setNik("USER");
			regulerKaryawan.setNama("USER");
			regulerKaryawan.setDivisi("USER");
			regulerKaryawan.setJabatan("USER");
			regulerKaryawan.setEmail("USER@hum.com");
			LocalDate tanggal1 = LocalDate.of(1, 1, 1);
			regulerKaryawan.setTanggalmasuk(tanggal1);
			regulerKaryawan.setTingkatan("USER");
			regulerUser.setKaryawan(regulerKaryawan);
			userRepository.save(regulerUser);
		}
	}
}
