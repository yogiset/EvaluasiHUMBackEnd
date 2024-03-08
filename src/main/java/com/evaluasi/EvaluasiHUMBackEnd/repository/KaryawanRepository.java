package com.evaluasi.EvaluasiHUMBackEnd.repository;

import com.evaluasi.EvaluasiHUMBackEnd.entity.Karyawan;
import com.evaluasi.EvaluasiHUMBackEnd.entity.Rule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KaryawanRepository extends JpaRepository<Karyawan,Long> {

    Karyawan findByNik(String nik);
    Karyawan findByIdkar(Long idkar);
    Karyawan findByJabatan(String jabatan);

    @Query("SELECT p FROM Karyawan p WHERE p.jabatan = :jabatan")
    Page<Karyawan> findByJabatan(String jabatan, Pageable pageable);
    Optional<Karyawan> findByNama(String nama);
    Optional<Karyawan>findByEmail(String email);

}
